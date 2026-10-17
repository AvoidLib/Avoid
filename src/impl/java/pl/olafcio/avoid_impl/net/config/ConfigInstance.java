package pl.olafcio.avoid_impl.net.config;

import org.yaml.snakeyaml.Yaml;
import pl.olafcio.avoid_common.Either;
import pl.olafcio.avoid_impl.net.config.controller.ConfigController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

public record ConfigInstance<T>(
        Path path,
        T instance,
        ArrayList<Either<ConfigController<?, ?, ?>, ConfigNest>> controllers
) {
    public boolean load() {
        Map<String, ?> content;

        try (var stream = new FileInputStream(path.toFile())) {
            content = new Yaml().load(stream);
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration '%s'".formatted(path.toAbsolutePath()), e);
        }

        loadControllers(content, controllers, instance);

        return true;
    }

    @SuppressWarnings("unchecked")
    private void loadControllers(Map<String, ?> content, ArrayList<Either<ConfigController<?, ?, ?>, ConfigNest>> controllers, Object instance) {
        for (var controller : controllers) {
            if (controller.left() instanceof ConfigController<?,?,?> sc) {
                unsafeRead(sc, content, instance);
            } else {
                var map = content.get(controller.right().key());
                if (map == null)
                    continue;

                loadControllers((Map<String, ?>) map, controller.right().controllers(), controller.right().varhandle().get(instance));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <A, N> void unsafeRead(ConfigController<A, N, ?> controller, Map<String, ?> content, Object instance) {
        try {
            controller.set(instance, controller.converterFrom.apply((N) content.get(controller.name)));
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to read configuration key '%s' from '%s'".formatted(controller.name, path.toAbsolutePath()), e);
        }
    }

    public void save() {
        var output = new StringBuilder();

        new Unparser(output).saveControllers(controllers);

        try {
            Files.writeString(path, output.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save configuration '%s'".formatted(path.toAbsolutePath()), e);
        }
    }

    private class Unparser {
        private final StringBuilder output;
        private Object instance;

        public Unparser(StringBuilder output) {
            this.output = output;
            this.instance = ConfigInstance.this.instance;
        }

        @SuppressWarnings("unchecked")
        private <A, N> void unsafeWrite(ConfigController<A, N, ?> controller) {
            if (!controller.fieldYAML.comment().isEmpty()) {
                output.append("\n");

                tab();

                output.append("# ");
                output.append(controller.fieldYAML.comment());
                output.append("\n");
            }

            tab();

            output.append(controller.name);
            output.append(": ");

            var value = controller.converterTo.apply((A) controller.varHandle.get(instance));
            if (value instanceof String str && (str.contains("\n") || controller.fieldYAML.multiline())) {
                output.append("|-");
                output.append("\n");

                tab++;

                var lines = str.split("\n");

                for (var line : lines) {
                    tab();

                    output.append(line.replace("\r", "\\r"));
                    output.append("\n");
                }

                tab--;
            } else if (value instanceof String str) {
                output.append('"');
                output.append(str.replace("\"", "\\\"")
                                 .replace("\n", "\\n")
                                 .replace("\r", "\\r")
                                 .replace("\b", "\\b")
                                 .replace("\f", "\\f"));
                output.append('"');
            } else {
                throw new RuntimeException("Invalid exported value of key '%s' in config '%s': '%s'".formatted(controller.name, path.toAbsolutePath(), value));
            }
        }

        private int tab = 0;
        private Object mark(ConfigNest nest) {
            tab++;

            var prev = instance;
            instance = nest.varhandle().get(this.instance);
            return prev;
        }
        private void unmark(Object previousInstance) {
            tab--;
            instance = previousInstance;
        }

        private void saveControllers(ArrayList<Either<ConfigController<?, ?, ?>, ConfigNest>> list) {
            for (var either : list) {
                if (either.left() instanceof ConfigController<?,?,?> controller) {
                    unsafeWrite(controller);
                } else {
                    tab();

                    output.append(either.right().key());
                    output.append(":\n");

                    var prev = mark(either.right());
                    saveControllers(either.right().controllers());
                    unmark(prev);
                }

                output.append("\n");
            }
        }

        private void tab() {
            output.append("  ".repeat(tab));
        }
    }
}
