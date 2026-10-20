package pl.olafcio.avoid_impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pl.olafcio.avoid_impl.net.payload.Networking;

import java.util.ArrayList;

@Mixin(ClientboundCustomPayloadPacket.class)
public class ClientboundCustomPayloadPacketMixin {
    @WrapOperation(at = @At(value = "NEW", target = "()Ljava/util/ArrayList;", ordinal = 0), method = "<clinit>")
    private static ArrayList clinit__newArrayList(Operation<ArrayList> original) {
        var list = original.call();

        list.addAll(Networking.s2c);
        Networking.s2c = null;

        return list;
    }
}
