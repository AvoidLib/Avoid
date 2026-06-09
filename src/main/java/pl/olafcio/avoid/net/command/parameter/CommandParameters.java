package pl.olafcio.avoid.net.command.parameter;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.net.command.parameter.impl.IntegerParameter;
import pl.olafcio.avoid.net.command.parameter.impl.StringParameter;

import java.lang.reflect.Constructor;
import java.util.HashMap;

@ApiStatus.Experimental
public final class CommandParameters {
    @ApiStatus.Internal
    private CommandParameters() {}

    private static final HashMap<String, Class<? extends CommandParameter<?>>> map
                   = new HashMap<>();

    private static final HashMap<String, Constructor<? extends CommandParameter<?>>> map_constructors
                   = new HashMap<>();

    static {
        register("string", StringParameter.class);
        register("integer", IntegerParameter.class);
    }

    public static void register(String tagName, Class<? extends CommandParameter<?>> parameterType) {
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
