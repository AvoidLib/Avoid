package pl.olafcio.avoid_impl.net.permission;

import net.minecraft.resources.Identifier;
import net.minecraft.server.permissions.LevelBasedPermissionSet;
import net.minecraft.server.players.NameAndId;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.Util;
import pl.olafcio.avoid.api.Avoid;
import pl.olafcio.avoid.net.command.annotation.PermissionLevel;
import pl.olafcio.avoid.net.permission.PermissionHandler;
import pl.olafcio.avoid.net.permission.PermissionHolder;
import pl.olafcio.avoid.net.permission.PermissionStatus;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid_impl.A4Paper;
import pl.olafcio.avoid_impl.AvoidInternal;

import java.util.*;

public final class PermissionHandlers {
    @ApiStatus.Internal
    private PermissionHandlers() {}

    public static final ArrayList<PermissionHandler> PERMISSION_HANDLERS
                  = new ArrayList<>();

    public abstract static class ForwardingMap<K, V> implements Map<K, V> {
        public final Map<K, V> delegate;

        protected ForwardingMap(Map<K, V> delegate) {
            this.delegate = delegate;
        }

        @Override
        public V get(Object key) {
            return delegate.get(key);
        }

        @Override
        public int size() {
            return delegate.size();
        }

        @Override
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            return delegate.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return delegate.containsValue(value);
        }

        @Override
        public @Nullable V put(K key, V value) {
            return delegate.put(key, value);
        }

        @Override
        public V remove(Object key) {
            return delegate.remove(key);
        }

        @Override
        public void putAll(@NotNull Map<? extends K, ? extends V> m) {
            delegate.putAll(m);
        }

        @Override
        public void clear() {
            delegate.clear();
        }

        @Override
        public @NotNull Set<K> keySet() {
            return delegate.keySet();
        }

        @Override
        public @NotNull Collection<V> values() {
            return delegate.values();
        }

        @Override
        public @NotNull Set<Entry<K, V>> entrySet() {
            return delegate.entrySet();
        }
    }

    public static void register(PermissionHandler permissionHandler) {
        PERMISSION_HANDLERS.add(permissionHandler);
    }

    private static void recalculate(Permissible p) {
        if (p instanceof Player) {
            try {
                var field = Class.forName("org.bukkit.craftbukkit.entity.CraftHumanEntity").getDeclaredField("perm");
                field.setAccessible(true);

                p = (Permissible) field.get(p);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        if (p instanceof PermissibleBase base) {
            try {
                var field = PermissibleBase.class.getDeclaredField("permissions");

                field.setAccessible(true);

                var basePerms = (Map<String, PermissionAttachmentInfo>) field.get(base);
                final var finalP = p;

                field.set(base, new ForwardingMap<>(basePerms) {
                    @Override
                    public PermissionAttachmentInfo get(Object key) {
                        var value = basePerms.get(key);
                        if (!(key instanceof String str))
                            return value;

                        ServerOperator user;

                        try {
                            var userField = PermissibleBase.class.getDeclaredField("opable");

                            userField.setAccessible(true);
                            user = (ServerOperator) userField.get(base);
                        } catch (NoSuchFieldException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }

                        var holder = user instanceof Player player               ? PlayerNative.convertFrom(Util.convert(player)) :
                                     user instanceof OfflinePlayer offlinePlayer ? new pl.olafcio.avoid.net.player.OfflinePlayer() {
                                         @Override
                                         public boolean hasPermission(String text) {
                                             var vote = castVote(this, text);
                                             if (vote != null)
                                                 return vote;

                                             return AvoidInternal.getServer().getProfilePermissions(new NameAndId(uuid(), getNick())).hasPermission(new net.minecraft.server.permissions.Permission.Atom(Identifier.withDefaultNamespace(text)));
                                         }

                                         @Override
                                         public boolean hasPermission(String text, PermissionLevel.Enum level) {
                                             var vote = castVote(this, text);
                                             if (vote != null)
                                                 return vote;

                                             var vote2 = castVote(this, level);
                                             if (vote2 != null)
                                                 return vote2;

                                             var set = AvoidInternal.getServer().getProfilePermissions(new NameAndId(uuid(), getNick()));

                                             return set.hasPermission(new net.minecraft.server.permissions.Permission.Atom(Identifier.withDefaultNamespace(text))) ||
                                                    set.hasPermission(new net.minecraft.server.permissions.Permission.HasCommandLevel(level.__get()));
                                         }

                                         @Override
                                         public String getNick() {
                                             return offlinePlayer.getPlayerProfile().getName();
                                         }

                                         @Override
                                         public UUID uuid() {
                                             return offlinePlayer.getUniqueId();
                                         }
                                     } : null;

                        if (holder == null)
                            return value;

                        var vote = castVote(holder, str);

                        if (value == null) {
                            if (vote != null) {
                                return new PermissionAttachmentInfo(finalP, str, new PermissionAttachment(A4Paper.INSTANCE, finalP), vote);
                            }

                            return null;
                        } else {
                            if (vote != null) {
                                return new PermissionAttachmentInfo(value.getPermissible(), value.getPermission(), value.getAttachment(), vote);
                            }

                            return new PermissionAttachmentInfo(value.getPermissible(), value.getPermission(), value.getAttachment(), value.getValue());
                        }
                    }
                });
            } catch (NoSuchFieldException e) {
                Avoid.LOGGER.warn("AvoidLib failed to inject permissions into PermissibleBase  /couldn't find field \"permissions\"");
            } catch (IllegalAccessException e) {
                Avoid.LOGGER.warn("AvoidLib failed to inject permissions into PermissibleBase  /couldn't access field \"permissions\"");
            }
        }
    }

    public static void init() {
        var pluginManager = Bukkit.getServer().getPluginManager();
        if (!(pluginManager instanceof SimplePluginManager)) {
            Avoid.LOGGER.error("------\nAvoidLib failed to inject its permission handler!\n" +
                               "Plugin Manager is not a SimplePluginManager\n" +
                               "\n" +
                               "PluginManager: " + pluginManager + "\n" +
                               "---");
            return;
        }

        try {
            injectDefaultPermissions(pluginManager);
            injectDefaultPermissionSubs(pluginManager);
            injectPermissions(pluginManager);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();

            Avoid.LOGGER.error("------\n" +
                               "AvoidLib failed to inject a permission handler!\n" +
                               "'NoSuchFieldException' exception has been thrown\n" +
                               "\n" +
                               "PluginManager: " + pluginManager + "\n" +
                               "---");
        } catch (IllegalAccessException e) {
            e.printStackTrace();

            Avoid.LOGGER.error("------\n" +
                               "AvoidLib failed to inject a permission handler!\n" +
                               "'IllegalAccessException' exception has been thrown\n" +
                               "\n" +
                               "PluginManager: " + pluginManager + "\n" +
                               "---");
        }
    }

    @SuppressWarnings("unchecked")
    private static void injectDefaultPermissions(PluginManager pluginManager) throws NoSuchFieldException, IllegalAccessException {
        var defaultpermissionsField = SimplePluginManager.class.getDeclaredField("defaultPerms");

        defaultpermissionsField.setAccessible(true);

        var defaultPermissions = (Map<Boolean, Set<Permission>>) defaultpermissionsField.get(pluginManager);

        defaultpermissionsField.set(pluginManager, new ForwardingMap<>(defaultPermissions) {
            @Override
            public Set<Permission> get(Object key) {
                return new HashSet<>();
            }

            @Override
            public @Nullable Set<Permission> put(Boolean key, Set<Permission> value) {
                return null;
            }

            @Override
            public void putAll(@NotNull Map<? extends Boolean, ? extends Set<Permission>> m) {}
        });
    }

    @SuppressWarnings("unchecked")
    private static void injectDefaultPermissionSubs(PluginManager pluginManager) throws NoSuchFieldException, IllegalAccessException {
        var defaultsubsField = SimplePluginManager.class.getDeclaredField("defSubs");

        defaultsubsField.setAccessible(true);

        var defaultSubs = (Map<Boolean, Map<Permissible, Boolean>>) defaultsubsField.get(pluginManager);

        defaultsubsField.set(pluginManager, new ForwardingMap<>(defaultSubs) {
            @Override
            public Map<Permissible, Boolean> get(Object key) {
                return new HashMap<>() {
                    @Override
                    public Boolean put(Permissible key, Boolean value) {
                        recalculate(key);
                        return null;
                    }
                };
            }

            @Override
            public @Nullable Map<Permissible, Boolean> put(Boolean key, Map<Permissible, Boolean> value) {
                return null;
            }

            @Override
            public void putAll(@NotNull Map<? extends Boolean, ? extends Map<Permissible, Boolean>> m) {}
        });
    }

    @SuppressWarnings("unchecked")
    private static void injectPermissions(PluginManager pluginManager) throws NoSuchFieldException, IllegalAccessException {
        var permissionsField = SimplePluginManager.class.getDeclaredField("permissions");

        permissionsField.setAccessible(true);

        var permissions = (Map<String, Permission>) permissionsField.get(pluginManager);

        permissionsField.set(pluginManager, new ForwardingMap<>(permissions) {
            @Override
            public Permission get(Object key) {
                var delegate = permissions.get(key);
                if (delegate == null) {
                    if (!(key instanceof String str))
                        return null;

                    var perm = new Permission(str);

                    put(str, perm);

                    return perm;
                }

                return delegate;
            }
        });
    }

    public static Boolean castVote(PermissionHolder holder, String permission) {
        var votes = new ArrayList<PermissionStatus>();

        for (var handler : PERMISSION_HANDLERS) {
            var vote = handler.hasPermission(holder, permission);

            if (vote.has() != null)
                votes.add(vote);
        }

        votes.sort(Comparator.comparingInt(PermissionStatus::priority));

        if (!votes.isEmpty())
            return votes.getFirst().has();

        return null;
    }

    public static Boolean castVote(PermissionHolder holder, PermissionLevel.Enum permissionLevel) {
        var votes = new ArrayList<PermissionStatus>();

        for (var handler : PERMISSION_HANDLERS) {
            var vote = handler.hasPermissionLevel(holder, permissionLevel);

            if (vote.has() != null)
                votes.add(vote);
        }

        votes.sort(Comparator.comparingInt(PermissionStatus::priority));

        if (!votes.isEmpty())
            return votes.getFirst().has();

        return null;
    }
}
