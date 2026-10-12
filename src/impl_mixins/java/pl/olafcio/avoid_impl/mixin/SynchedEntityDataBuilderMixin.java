package pl.olafcio.avoid_impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.network.syncher.SyncedDataHolder;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ClassTreeIdRegistry;
import org.jspecify.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.olafcio.avoid_impl.mixininterface.ISynchedEntityData;
import pl.olafcio.avoid_impl.net.entity.custom_internal.IAvoidEntity;

@Mixin(SynchedEntityData.Builder.class)
public class SynchedEntityDataBuilderMixin {
    @Shadow
    @Final
    private SyncedDataHolder entity;

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ClassTreeIdRegistry;getCount(Ljava/lang/Class;)I"), method = "<init>")
    public int init__getCount(ClassTreeIdRegistry instance, Class<?> class_, Operation<Integer> original, SyncedDataHolder syncedDataHolder) {
        if (syncedDataHolder instanceof IAvoidEntity avoid)
            return avoid.getAvoidType().lastID + 1;

        return original.call(instance, class_);
    }

    @Inject(at = @At("RETURN"), method = "build")
    public void build(CallbackInfoReturnable<SynchedEntityData> cir) {
        ((ISynchedEntityData) cir.getReturnValue()).avoidlib$builder((SynchedEntityData.Builder) (Object) this);
    }

    @WrapOperation(at = @At(value = "FIELD", target = "Lnet/minecraft/network/syncher/SynchedEntityData$Builder;itemsById:[Lnet/minecraft/network/syncher/SynchedEntityData$DataItem;", ordinal = 0, opcode = Opcodes.GETFIELD), method = "build")
    public SynchedEntityData.@Nullable DataItem<?>[] build__itemsById__toCheckErrors(SynchedEntityData.Builder instance, Operation<SynchedEntityData.DataItem<?>[]> original) {
        if (entity instanceof IAvoidEntity)
            //TODO This hack is insane please change this if there's a better way.. maybe defining new classes at runtime was a better idea than all this
            return new SynchedEntityData.DataItem[0];

        return original.call(instance);
    }
}
