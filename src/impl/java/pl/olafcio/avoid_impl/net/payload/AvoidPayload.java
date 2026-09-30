package pl.olafcio.avoid_impl.net.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import pl.olafcio.avoid.net.payload.CustomPayload;

import java.util.function.Supplier;

public class AvoidPayload {
    final Supplier<? extends CustomPayload> SUPPLIER;

    final StreamCodec<FriendlyByteBuf, Payload> STREAM_CODEC;
    final CustomPacketPayload.Type<Payload> TYPE;

    AvoidPayload(
            Supplier<? extends CustomPayload> supplier,
            CustomPacketPayload.Type<Payload> type
    ) {
        this.SUPPLIER = supplier;
        this.TYPE = type;
        this.STREAM_CODEC = CustomPacketPayload.codec(Payload::write, Payload::new);
    }

    public Payload make(CustomPayload payload) {
        return new Payload(payload);
    }

    public class Payload implements CustomPacketPayload {
        public final CustomPayload payload;

        private Payload(CustomPayload payload) {
            this.payload = payload;
        }

        private Payload(FriendlyByteBuf friendlyByteBuf) {
            payload = SUPPLIER.get();
            payload.read(friendlyByteBuf.readByteArray());
        }

        private void write(FriendlyByteBuf friendlyByteBuf) {
            friendlyByteBuf.writeByteArray(payload.write());
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
}
