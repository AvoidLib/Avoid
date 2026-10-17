package pl.olafcio.avoid_impl.net.config.controller;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.config.annotations.YAML;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.world.block_location.BlockLocation;
import pl.olafcio.avoid.net.world.location.Location;
import pl.olafcio.avoid_impl.net.config.controller.abst.list.CCBlockLocation;
import pl.olafcio.avoid_impl.net.config.controller.abst.list.CCBlockPos;
import pl.olafcio.avoid_impl.net.config.controller.abst.list.CCLocation;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.UUID;
import java.util.function.Function;

public final class ConfigControllers {
    @ApiStatus.Internal
    private ConfigControllers() {}

    public static ConfigController<?, ?, ?> convert(Field field) {
        var klass = field.getType();
        var ret = convert(klass);

        ret.field = field;
        ret.fieldYAML = field.getDeclaredAnnotation(YAML.class);

        try {
            ret.varHandle = MethodHandles.privateLookupIn(field.getDeclaringClass(), MethodHandles.lookup())
                                         .unreflectVarHandle(field);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to get varhandle of configuration field '%s' on config class '%s'".formatted(field.getName(), field.getDeclaringClass().getName()), e);
        }

        ret.name = field.getName();

        return ret;
    }

    public static ConfigController<?, ?, ?> convert(Class<?> klass) {
        Function<?, String> x;

             if (klass == String.class)                            x = Function.identity();
        else if (klass == int.class     || klass == Integer.class) return ConfigController.create((Integer i) -> i.toString());
        else if (klass == float.class   || klass == Float.class)   return ConfigController.create((Float f) -> f.toString());
        else if (klass == long.class    || klass == Long.class)    return ConfigController.create((Long l) -> l.toString());
        else if (klass == short.class   || klass == Short.class)   return ConfigController.create((Short s) -> s.toString());
        else if (klass == double.class  || klass == Double.class)  return ConfigController.create((Double d) -> d.toString());
        else if (klass == boolean.class || klass == Boolean.class) return ConfigController.create((Boolean bl) -> bl == true ? "true" : "false");
        else if (klass == UUID.class)                              return new ConfigController<>(UUID::fromString, Object::toString);
        else if (klass == BlockPos.class)                          return new CCBlockPos().controller;
        else if (klass == BlockLocation.class)                     return new CCBlockLocation().controller;
        else if (klass == Location.class)                          return new CCLocation().controller;
        else if (klass == Identification.class)                    return new ConfigController<>((Identification z) -> IdentificationNative.convert(z), Object::toString);
        else                                                       throw new RuntimeException("Unserializable type '%s'".formatted(klass.getName()));

        return ConfigController.create(x);
    }
}
