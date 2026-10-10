package pl.olafcio.avoid_impl.mixin;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid_impl.net.advancement.IAvoidAdvancement;

@Mixin(PlayerAdvancements.class)
public class PlayerAdvancementsMixin {
    @Shadow
    private ServerPlayer player;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerAdvancements;markForVisibilityUpdate(Lnet/minecraft/advancements/AdvancementHolder;)V"), method = "award")
    public void award__markForVisibilityUpdate(AdvancementHolder advancementHolder, String string, CallbackInfoReturnable<Boolean> cir) {
        if (advancementHolder.value() instanceof IAvoidAdvancement avoid)
            avoid.avoid$advancement().get().onGrantServer(PlayerNative.convertFrom(player));
    }
}
