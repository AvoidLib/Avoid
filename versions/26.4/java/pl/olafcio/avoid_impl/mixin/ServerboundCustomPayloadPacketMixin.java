package pl.olafcio.avoid_impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pl.olafcio.avoid_impl.net.payload.Networking;

import java.util.ArrayList;

@Mixin(ServerboundCustomPayloadPacket.class)
public class ServerboundCustomPayloadPacketMixin {
    @WrapOperation(at = @At(value = "NEW", target = "()Ljava/util/ArrayList;", ordinal = 0), method = "<clinit>")
    private static ArrayList clinit__newArrayList(Operation<ArrayList> original) {
        var list = original.call();

        list.addAll(Networking.c2s);
        Networking.c2s = null;

        return list;
    }
}
