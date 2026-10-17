package pl.olafcio.avoid_impl.mixin;

import com.mojang.datafixers.DataFixer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.level.progress.LevelLoadListener;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.players.NameAndId;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid.net.permission.PermissionStatus;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid_impl.AvoidInternal;
import pl.olafcio.avoid.mods.loader.AvoidModLoader;
import pl.olafcio.avoid.platform.AvoidWrappedLoader;
import pl.olafcio.avoid.platform.RunningEnv;
import pl.olafcio.avoid.mods.AvoidModMeta;
import pl.olafcio.avoid_impl.mixininterface.INameAndId;
import pl.olafcio.avoid_impl.net.permission.PermissionHandlers;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.Comparator;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(at = @At("CTOR_HEAD"), method = "<init>")
    public void ctor(Thread thread, LevelStorageSource.LevelStorageAccess levelStorageAccess, PackRepository packRepository, WorldStem worldStem, Proxy proxy, DataFixer dataFixer, Services services, LevelLoadListener levelLoadListener, CallbackInfo ci) {
        AvoidInternal.server = (MinecraftServer) (Object) this;
    }

    @Inject(at = @At("HEAD"), method = "stopServer")
    public void stopServer(CallbackInfo ci) {
        var addons = AvoidModLoader.getLoadedAddons();
        for (AvoidModMeta mod : addons) {
            var main = AvoidModLoader.getLoadedAddonClass(mod);

            if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.SERVER)
                main.onDisable();

            main.onServerDisable();
        }
    }

    @Inject(at = @At("RETURN"), method = "getProfilePermissions", cancellable = true)
    public void getProfilePermissions(NameAndId nameAndId, CallbackInfoReturnable<Integer> cir) {
        var player = PlayerNative.convertFrom(((INameAndId) (Object) nameAndId).avoid$player());
        var handlers = PermissionHandlers.PERMISSION_HANDLERS;

        for (int i = 0; i < 4; i++) {
            var votes = new ArrayList<PermissionStatus>();

            for (var handler : handlers) {
                var vote = handler.hasPermissionLevel(
                        player,
                        pl.olafcio.avoid.net.command.annotation.PermissionLevel.Enum.values()[i+1]
                );

                if (vote.has() != null)
                    votes.add(vote);
            }

            votes.sort(Comparator.comparingInt(PermissionStatus::priority));

            if (!votes.isEmpty())
                if (Boolean.FALSE.equals(votes.getFirst().has()))
                    cir.setReturnValue(i);
        }
    }
}
