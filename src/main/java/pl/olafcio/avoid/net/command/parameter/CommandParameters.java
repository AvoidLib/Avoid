package pl.olafcio.avoid.net.command.parameter;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.mods.AvoidMod;
import pl.olafcio.avoid.net.command.parameter.impl.IntegerParameter;
import pl.olafcio.avoid.net.command.parameter.impl.StringParameter;
import pl.olafcio.avoid.net.command.parameter.pattern.PatternSyntaxError;
import pl.olafcio.avoid.net.command.parameter.pattern.WrappedString;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A main namespace where command parameters are defined and registered.<br/>
 * Of course, you may register your own (using the {@link #register(String, Class)} method) in your {@link AvoidMod#onEnable()} method.
 */
@ApiStatus.Experimental
public final class CommandParameters {
    @ApiStatus.Internal
    private CommandParameters() {}

    private static final HashMap<String, Class<? extends CommandParameter<?>>> map
                   = new HashMap<>();

    private static final HashMap<String, Constructor<? extends CommandParameter<?>>> map_constructors
                   = new HashMap<>();

    static {
        registerSingle("string", StringParameter.class);
        register("int[eger]", IntegerParameter.class);
    }

    /**
     * Registers a tag using the given pattern.
     * <br/><br/>
     * This method compiles the provided pattern into a list of strings,
     * and calls {@link #registerSingle} for each of them.
     * <br/><br/>
     * The pattern language is a custom-written one for these,
     * and only contains the <u>[ optional ]</u> expression.
     *
     * @param tagPattern The tag pattern itself, e.g. {@code [rgb][_]color}.
     * @param parameterType Class of the parameter type you want to register, e.g. {@code RGBColorParameter.class}.
     */
    public static void register(String tagPattern, Class<? extends CommandParameter<?>> parameterType) {
        if (!tagPattern.contains("[")) {
            Avoid.LOGGER.warn("[CommandParameters#register] Non-pattern tags should be registered with registerSingle(...) instead");
            registerSingle(tagPattern, parameterType);
            return;
        }

        var marked = new StringBuilder();

        var placeholders = new ArrayList<WrappedString>();
        var results = new ArrayList<String>();

        var chars = tagPattern.toCharArray();

        var value = new StringBuilder();
        var inTag = false;

        for (char ch : chars) {
            if (inTag) {
                if (ch == ']') {
                    if (value.isEmpty())
                        throw new PatternSyntaxError("Empty optional tag-element: '[]'");

                    inTag = false;
                    marked.append("?");

                    placeholders.add(new WrappedString(value.toString()));
                    value.setLength(0);
                } else {
                    value.append(ch);
                }
            } else if (ch == '[') {
                if (!value.isEmpty()) {
                    marked.append(value);
                    value.setLength(0);
                }

                inTag = true;
            } else {
                value.append(ch);
            }
        }

        if (inTag)
            throw new PatternSyntaxError("Non-terminated tag");

        if (!value.isEmpty())
            marked.append(value);

        Avoid.LOGGER.debug("Calculating pattern tags '{}'", tagPattern);

        int index = 2;
        for (var opt : placeholders) {
            var arr = marked.toString().split("\\?", index);
            var arr2 = marked.toString().split("\\?", index);

            arr[index - 1] = opt.string();
            arr2[index - 1] = "";

            var texts = new ArrayList<String>();
            var texts2 = new ArrayList<String>();

            texts.add(String.join("", arr));
            texts2.add(String.join("", arr2));

            for (var opt2 : placeholders) {
                int i = 0;
                for (var t : texts)
                    texts.set(i++, t.replaceFirst("\\?", opt2.string()));

                if (opt != opt2) {
                    i = 0;

                    for (var t : texts)
                        texts2.set(i++, t.replaceFirst("\\?", ""));
                }
            }

            index++;

            results.addAll(texts);
            results.addAll(texts2);
        }

        Avoid.LOGGER.debug("Registering pattern tags '{}'", tagPattern);

        for (var res : results)
            registerSingle(res, parameterType);
    }

    /**
     * Registers a tag with the specified name.
     *
     * @param tagName The tag name, e.g. {@code rgbcolor}.
     * @param parameterType Class of the parameter type you want to register, e.g. {@code RGBColorParameter.class}.
     */
    public static void registerSingle(String tagName, Class<? extends CommandParameter<?>> parameterType) {
        try {
            map.put(tagName, parameterType);
            map_constructors.put(tagName, parameterType.getConstructor(String.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Failed to register parameter type '%s (%s)'".formatted(
                                              tagName,
                                              parameterType.getSimpleName()
                                      ), e);
        }
    }

    @Nullable
    public static Class<? extends CommandParameter<?>> queryTag(String tagName) {
        return map.get(tagName);
    }

    @Nullable
    public static Constructor<? extends CommandParameter<?>> queryTagConstructor(String tagName) {
        return map_constructors.get(tagName);
    }
}
