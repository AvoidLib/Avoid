package pl.olafcio.avoid.mixin.accessors;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MinecraftServer.class)
public interface IMinecraftServer {
    @Invoker("stopServer")
    void avoid$stopServer();
}
