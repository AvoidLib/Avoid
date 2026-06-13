package pl.olafcio.avoid.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity_server.event.ServerEntityCreateEvent;
import pl.olafcio.avoid.net.entity_type.EntityTypeNative;
import pl.olafcio.avoid.net.item.stack.ItemStackNative;
import pl.olafcio.avoid.net.entity_server.event.ServerEntityDropEvent;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(at = @At("CTOR_HEAD"), method = "<init>")
    public void init(EntityType<?> entityType, Level level, CallbackInfo ci) {
        EventManager.fire(new ServerEntityCreateEvent(
                EntityTypeNative.convertFrom(entityType),
                EntityNative.convertFrom((LivingEntity) (Object) this)
        ));
    }

    @Inject(at = @At("HEAD"), method = "drop", cancellable = true)
    public void drop(ItemStack itemStack, boolean bl, boolean bl2, CallbackInfoReturnable<ItemEntity> cir) {
        var event = new ServerEntityDropEvent(
                EntityNative.convertFrom((LivingEntity) (Object) this),
                ItemStackNative.convertFrom(itemStack)
        );

        EventManager.fire(event);

        if (event.isCancelled())
            cir.setReturnValue(null);
    }
}
