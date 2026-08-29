package pl.olafcio.avoid;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class AvoidInternal {
    @ApiStatus.Internal
    private AvoidInternal() {}

    @ApiStatus.Internal
    public static MinecraftServer server;

    @ApiStatus.Internal
    public static MinecraftServer getServer() {
        try {
            var craftServer = Class.forName("org.bukkit.craftbukkit.CraftServer").cast(Bukkit.getServer());
            var consoleField = craftServer.getClass().getDeclaredField("console");

            consoleField.setAccessible(true);

            return (MinecraftServer) consoleField.get(craftServer);
        } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException e) {
            throw new RuntimeException("AvoidLib failed to retrieve MinecraftServer object", e);
        }
    }

    public static HolderLookup.Provider registry
                = VanillaRegistries.createLookup();
}
