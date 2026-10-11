package pl.olafcio.avoid_impl.mixin;

import net.minecraft.network.syncher.SynchedEntityData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid_impl.mixininterface.IDataItem;

@Mixin(SynchedEntityData.DataItem.class)
public class SynchedEntityDataItemMixin implements IDataItem {
    @Unique
    private boolean neverDefault = false;

    @Override
    public void avoidlib$setNeverDefault() {
        neverDefault = true;
    }

    @Inject(at = @At("HEAD"), method = "isSetToDefault", cancellable = true)
    public void isSetToDefault(CallbackInfoReturnable<Boolean> cir) {
        if (neverDefault)
            cir.setReturnValue(false);
    }
}
