package pl.olafcio.avoid_platform;

import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.ModLoadingException;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid_loader.PreModLoader;
import pl.olafcio.avoid_loader.interfaces.services.IModLoader;
import pl.olafcio.avoid_loader.interfaces.IMod;
import pl.olafcio.avoid_loader.interfaces.IModFile;
import pl.olafcio.avoid_loader.interfaces.services.stage.fml.IFMLCowardlyEventStateListener;
import pl.olafcio.avoid_loader.interfaces.services.stage.fml.IFMLLoadingExceptionListener;
import pl.olafcio.reflectstorm.Abstraction;
import pl.olafcio.reflectstorm.ReflectStorm;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class PlatformFatalImpl implements IModLoader {
    ///Copypaste from JarManager to be in loader namespace
    @NotNull
    private String getJarURL() {
        var pkg = "pl/olafcio/avoid_platform/";

        return this.getClass().getClassLoader().getResource(pkg.substring(0, pkg.length() - 1))
                                               .getPath()
                                               .split("!")[0];
    }

    @Override
    public void load() {
        try {
            // OH MY GOD JPMS WHAT THE FUCK IM LOSING IT
            // ESCALATION ONTO PARENT CLASSLOADER?! LIKE WHAT THE FUCK

            var dir = Path.of(".cache/avoid_loader/extract/");
            Files.createDirectories(dir);

            var jar = dir.resolve("Avoid AppLevel.jar");

            var fos = new FileOutputStream(jar.toFile());
            var zos = new ZipOutputStream(fos);

            try {
                try (var stream = this.getClass().getResourceAsStream("/pl/olafcio/avoid_platform/applevel/PFIAppNat.class"))  {
                    zos.putNextEntry(new ZipEntry("pl/olafcio/avoid_platform/applevel/PFIAppNat.class"));
                    stream.transferTo(zos);
                }
            } finally {
                zos.close();
                fos.close();
            }

            ReflectStorm.classpath().klassLoader(ReflectStorm.Classpath.BuiltinCL.APP)
                        .addFile(jar.toAbsolutePath().toString());  // Safest method for builtin loaders \:

            // Right. I guess ReflectStorm helped... more than it should've ._.

            var b =ReflectStorm.builder();
            b.klassLoader(Abstraction.get().getClassLoader(ReflectStorm.Classpath.BuiltinCL.APP));
            b.klass("pl.olafcio.avoid_platform.applevel.PFIAppNat").method("thisIsInsane", Runnable.class, URL[].class).invoke(null, (Runnable) this::inject, new URL[]{
                    URI.create(getJarURL()).toURL(),
                    jar.toAbsolutePath().toUri().toURL()
            });
        } catch (Exception e) {
            e.printStackTrace();

            System.err.println("Failed to initialize Avoid appclassloader logger manipulation magic! Sorry for the throwup!");
            System.err.println("Crashing your game so that you don't think avoid works. Because this message means it broke.");
            System.err.println("---");
            System.err.println("--- SOLUTION: Try changing Avoid version");
            System.err.println("---");

            Runtime.getRuntime().halt(0);

            throw new RuntimeException(e);
        }
    }

    private static volatile boolean injected = false;

    @SuppressWarnings("unchecked")
    public void inject() {
        var statusconsumer = (Optional<Consumer<String>>)
                             Reflect.get(ModLoader.class, "statusConsumer", Optional.class, ModLoader.get());

        Reflect.set(ModLoader.class, "statusConsumer", ModLoader.get(),
                                                             this.make(statusconsumer.orElse(null)));

        var loadingexceptions = (List<ModLoadingException>)
                                Reflect.get(ModLoader.class, "loadingexceptions", List.class, ModLoader.get());

        Reflect.set(ModLoader.class, "loadingexceptions", ModLoader.get(),
                                                                this.make(loadingexceptions));

        injected = true;
    }

    private Optional<Consumer<String>> make(Consumer<String> delegate) {
        return Optional.of(t -> {
            if (t.equals("ERROR DURING MOD LOADING") && !Reflect.get(ModLoader.class, "loadingStateValid", boolean.class, ModLoader.get())) {
                var services = PreModLoader.getInstance().loadService(IFMLCowardlyEventStateListener.class);

                for (var service : services)
                    service.acknowledge();
            }

            if (delegate != null)
                delegate.accept(t);
        });
    }

    private List<ModLoadingException> make(List<ModLoadingException> delegate) {
        return new List<ModLoadingException>() {
            @Override
            public int size() {
                return delegate.size();
            }

            @Override
            public boolean isEmpty() {
                return delegate.isEmpty();
            }

            @Override
            public boolean contains(Object o) {
                return delegate.contains(o);
            }

            @Override
            public @NotNull Iterator<ModLoadingException> iterator() {
                return delegate.iterator();
            }

            @Override
            public @NotNull Object[] toArray() {
                return delegate.toArray();
            }

            @Override
            public @NotNull <T> T[] toArray(@NotNull T[] a) {
                return delegate.toArray(a);
            }

            @Override
            public boolean add(ModLoadingException e) {
                var services = PreModLoader.getInstance().loadService(IFMLLoadingExceptionListener.class);

                for (var service : services)
                    service.acknowledge(e);

                return delegate.add(e);
            }

            @Override
            public boolean remove(Object o) {
                return delegate.remove(o);
            }

            @Override
            public boolean containsAll(@NotNull Collection<?> c) {
                return delegate.containsAll(c);
            }

            @Override
            public boolean addAll(@NotNull Collection<? extends ModLoadingException> c) {
                for (var e : c) {
                    var services = PreModLoader.getInstance().loadService(IFMLLoadingExceptionListener.class);

                    for (var service : services)
                        service.acknowledge(e);
                }

                return delegate.addAll(c);
            }

            @Override
            public boolean addAll(int index, @NotNull Collection<? extends ModLoadingException> c) {
                for (var e : c) {
                    var services = PreModLoader.getInstance().loadService(IFMLLoadingExceptionListener.class);

                    for (var service : services)
                        service.acknowledge(e);
                }

                return delegate.addAll(index, c);
            }

            @Override
            public boolean removeAll(@NotNull Collection<?> c) {
                return delegate.removeAll(c);
            }

            @Override
            public boolean retainAll(@NotNull Collection<?> c) {
                return delegate.retainAll(c);
            }

            @Override
            public void clear() {
                delegate.clear();
            }

            @Override
            public ModLoadingException get(int index) {
                return delegate.get(index);
            }

            @Override
            public ModLoadingException set(int index, ModLoadingException element) {
                return delegate.set(index, element);
            }

            @Override
            public void add(int index, ModLoadingException element) {
                delegate.add(index, element);
            }

            @Override
            public ModLoadingException remove(int index) {
                return delegate.remove(index);
            }

            @Override
            public int indexOf(Object o) {
                return delegate.indexOf(o);
            }

            @Override
            public int lastIndexOf(Object o) {
                return delegate.lastIndexOf(o);
            }

            @Override
            public @NotNull ListIterator<ModLoadingException> listIterator() {
                return delegate.listIterator();
            }

            @Override
            public @NotNull ListIterator<ModLoadingException> listIterator(int index) {
                return delegate.listIterator(index);
            }

            @Override
            public @NotNull List<ModLoadingException> subList(int fromIndex, int toIndex) {
                return delegate.subList(fromIndex, toIndex);
            }
        };
    }

    @Override
    public Collection<IMod> process(IModFile modFile) {
        return List.of();
    }
}
