package pl.olafcio.avoid.net.command.annotation;

import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;

/**
 * Specifies a syntax method's permission atom and command level.<br/>
 * An executor must match either to be able to proceed.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionLevel {
    String value();
    Enum level();

    enum Enum {
        ALL("all", 0),
        MODERATORS("moderators", 1),
        GAMEMASTERS("gamemasters", 2),
        ADMINS("admins", 3),
        OWNERS("owners", 4);

        private static final HashMap<Enum, net.minecraft.server.permissions.PermissionLevel> map
                       = new HashMap<>();

        static {
            var avoids = values();
            var crafts = net.minecraft.server.permissions.PermissionLevel.values();

            for (var val : avoids) {
                for (var from : crafts) {
                    if (
                            from.getSerializedName().equals(val.id) ||
                            from.name().equals(val.name()) ||
                            from.id() == val.index
                    )
                        map.put(val, from);
                }
            }
        }

        private final String id;
        private final int index;

        Enum(String id, int index) {
            this.id = id;
            this.index = index;
        }

        @ApiStatus.Internal
        public final net.minecraft.server.permissions.PermissionLevel __get() {
            return map.get(this);
        }
    }
}
