package pl.olafcio.avoid_impl.net.entity;

import com.google.common.base.CaseFormat;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity.EntityAccessor;
import pl.olafcio.avoid_impl.mixin.accessors.IEntityType;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid_impl.net.chat.converter.COFromNative;
import pl.olafcio.avoid_impl.net.entity.custom_internal.IAvoidEntity;
import pl.olafcio.avoid.net.entity.type.base.NativeEntityConstructor;
import pl.olafcio.avoid.net.entity.type.base.annotations.AutoEntityAttach;
import pl.olafcio.avoid.net.entity_type.EntityType;
import pl.olafcio.avoid.net.entity_type.EntityTypeNative;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.world.vect3.IVect3;
import pl.olafcio.avoid_impl.net.world.vect3.Vect3Native;
import pl.olafcio.avoid_common.JarManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Function;

@Native
@ApiStatus.Internal
public final class EntityNative {
    @ApiStatus.Internal
    private EntityNative() {}

    private static final ArrayList<Function<Class<? extends net.minecraft.world.entity.Entity>, @Nullable NativeEntityConstructor<?>>> types
                   = new ArrayList<>();

    public static <Avoid extends Entity> void register(Class<? extends net.minecraft.world.entity.Entity> entity, NativeEntityConstructor<Avoid> transformer) {
        types.add(mc -> {
            if (entity.isAssignableFrom(mc))
                return transformer;

            return null;
        });
    }

    private static void scan(String pkg) {
        try {
            var resources = new JarManager(EntityNative.class).listFilenames(pkg);

            for (var url : resources) {
                var path = pkg + "/" + url;
                if (path.contains("mixin"))
                    continue;

                if (path.endsWith(".class") || path.endsWith(".java")) {
                    path = path.substring(0, path.lastIndexOf("."));

                    try {
                        var klass = Class.forName(path.replace("/", "."));
                        if (klass.isAnnotationPresent(AutoEntityAttach.class)) {
                            Class<? extends net.minecraft.world.entity.Entity> mcEntity;

                            if (klass.isAnnotationPresent(pl.olafcio.avoid.net.entity.type.base.annotations.klass.Class.class))
                                mcEntity = klass.getAnnotation(pl.olafcio.avoid.net.entity.type.base.annotations.klass.Class.class)
                                                .value();
                            else if (klass.isAnnotationPresent(pl.olafcio.avoid.net.entity.type.base.annotations.id.Namespace.class)) {
                                var type = BuiltInRegistries.ENTITY_TYPE.getValue(
                                        Identifier.fromNamespaceAndPath(
                                            klass.getAnnotation(pl.olafcio.avoid.net.entity.type.base.annotations.id.Namespace.class)
                                                 .value(),
                                            klass.getAnnotation(pl.olafcio.avoid.net.entity.type.base.annotations.id.Value.class)
                                                 .value()
                                        )
                                );

                                //noinspection unchecked
                                mcEntity = (Class<? extends net.minecraft.world.entity.Entity>)
                                           ((IEntityType<?>) type).avoid$factory().getClass().getNestHost();

                                // Magic. Ikr?
                            } else
                                throw new RuntimeException("Illegal use of @AutoEntityAttach in '%s' [Avoid internals]".formatted(id(path)));

                            var constructor = klass.getDeclaredConstructor(int.class, EntityType.class,
                                                                                          IVect3.class, IVect3.class,
                                                                                          UUID.class, BaseComponent.class,
                                                                                          net.minecraft.world.entity.Entity.class);

                            final var ident = id(path);

                            Avoid.LOGGER.debug("Registering " + klass.getSimpleName());
                            register(mcEntity, (int id, EntityType type, IVect3 position, IVect3 velocity, UUID uuid, BaseComponent<?> name, net.minecraft.world.entity.Entity underlyingEntity) -> {
                                try {
                                    return (Entity) constructor.newInstance(id, type, position, velocity, uuid, name, underlyingEntity);
                                } catch (InstantiationException e) {
                                    throw new RuntimeException("Failed to register Minecraft entity type: '%s' /reflection exception".formatted(ident), e);
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException("Failed to register Minecraft entity type: '%s' /access exception".formatted(ident), e);
                                } catch (InvocationTargetException e) {
                                    throw new RuntimeException("Failed to register Minecraft entity type: '%s' /invocation exception".formatted(ident), e);
                                }
                            });
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException("Failed to process internal class: '%s' /classnotfound exception".formatted(path), e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException("Failed to process internal class: '%s' /nosuchmethod exception".formatted(path), e);
                    }
                } else if (!path.contains(".") && path.toLowerCase().equals(path)) {
                    scan(path);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to scan internal Avoid entity-type wrappers", e);
        }
    }

    static {
        scan("pl/olafcio/avoid");
    }

    private static @NotNull String id(String path) {
        return CaseFormat.UPPER_CAMEL.to(
                CaseFormat.LOWER_UNDERSCORE,
                path.substring(path.lastIndexOf("/") + 1)
        );
    }

    public static net.minecraft.world.entity.Entity convert(Entity entity) {
        return EntityAccessor.convert(entity);
    }

    // replaced by MC 26.2
    public static Entity convertFromTry(net.minecraft.world.entity.Entity entity) {return convertFrom(entity);}

    public static Entity convertFrom(net.minecraft.world.entity.Entity entity) {
        if (entity instanceof IAvoidEntity wrapper)
            return wrapper.getAvoidEntity();
        else if (entity instanceof Player player)
            return PlayerNative.convertFrom(player);

        BaseComponent<?> name;

        try                 { name = COFromNative.from(entity.getName()); }
        catch (Exception e) { name = null;                                }

        var kls = entity.getClass();

        try {
            for (var type : types) {
                var nec = type.apply(kls);
                if (nec != null)
                    return nec.construct(
                            entity.getId(),
                            EntityTypeNative.convertFrom(entity.getType()),
                            Vect3Native.convert(entity.position()),
                            Vect3Native.convert(entity.getDeltaMovement()),
                            entity.getUUID(),
                            name,
                            entity
                    );
            }
        } catch (Exception e) {
            Avoid.LOGGER.error("===========================================");
            Avoid.LOGGER.error("An error has been detected in creating a entity wrapper for %s.".formatted(entity.getClass().getName()));
            Avoid.LOGGER.error("If your world is currently loading, SHUT DOWN YOUR GAME AS SOON AS POSSIBLE!");
            Avoid.LOGGER.error("This may break your world if you're in that stage.");
            Avoid.LOGGER.error("===========================================");
        }

        return new Entity(
                entity.getId(),
                EntityTypeNative.convertFrom(entity.getType()),
                Vect3Native.convert(entity.position()),
                Vect3Native.convert(entity.getDeltaMovement()),
                entity.getUUID(),
                name,
                entity
        ) {};
    }
}
