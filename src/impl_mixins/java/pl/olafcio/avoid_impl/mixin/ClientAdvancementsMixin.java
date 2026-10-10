package pl.olafcio.avoid_impl.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.advancements.AdvancementNode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid_impl.net.advancement.IAvoidAdvancement;

@Mixin(ClientAdvancements.class)
public class ClientAdvancementsMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/game/ClientboundUpdateAdvancementsPacket;shouldReset()Z", ordinal = 1), method = "update")
    public void update__processedNewAdvancement(
            ClientboundUpdateAdvancementsPacket clientboundUpdateAdvancementsPacket,
            CallbackInfo ci,
            @Local AdvancementNode advancementNode
    ) {
        if (advancementNode.advancement() instanceof IAvoidAdvancement avoid)
            avoid.avoid$advancement().get().onGrantClient(PlayerNative.convertFrom(minecraft.player));
    }
}
