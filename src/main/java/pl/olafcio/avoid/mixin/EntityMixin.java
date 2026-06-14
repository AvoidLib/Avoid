package pl.olafcio.avoid.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity_server.event.ServerEntityInteractEvent;
import pl.olafcio.avoid.net.player.PlayerNative;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract Level level();

    @SuppressWarnings("resource")
    @Inject(at = @At("HEAD"), method = "interact", cancellable = true)
    public void interact(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        if (!level().isClientSide() && player instanceof ServerPlayer) {
            var event = new ServerEntityInteractEvent(
                    EntityNative.convertFrom((Entity) (Object) this),
                    PlayerNative.convertFrom((ServerPlayer) player)
            );

            EventManager.fire(event);

            if (event.isCancelled())
                cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
