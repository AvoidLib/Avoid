package pl.olafcio.avoid_impl.mixin;

import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import pl.olafcio.avoid_impl.net.payload.Networking;

import java.util.ArrayList;
import java.util.List;

@Mixin(ClientboundCustomPayloadPacket.class)
public class ClientboundCustomPayloadPacketMixin {
    @ModifyArgs(at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Lists;newArrayList([Ljava/lang/Object;)Ljava/util/ArrayList;"), method = "<clinit>")
    private static void clinit__newArrayList(Args args) {
        var array = (Object[]) args.get(0);
        var list = new ArrayList<>(List.of(array));

        list.addAll(Networking.s2c);
        Networking.s2c = null;

        args.set(0, list.toArray(Object[]::new));
    }
}
