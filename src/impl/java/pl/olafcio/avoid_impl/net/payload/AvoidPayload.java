package pl.olafcio.avoid_impl.net.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import pl.olafcio.avoid.net.payload.CustomPayload;
import pl.olafcio.avoid.net.payload.annotations.NoManual;
import pl.olafcio.avoid.net.payload.field.Order;
import pl.olafcio.avoid.net.payload.field.PacketField;
import pl.olafcio.avoid_impl.net.payload.field_controller.NetworkControllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Supplier;

public class AvoidPayload {
    final Supplier<? extends CustomPayload> SUPPLIER;

    final StreamCodec<FriendlyByteBuf, Payload> STREAM_CODEC;
    final CustomPacketPayload.Type<Payload> TYPE;

    private final boolean hasByteArray;
    private final ArrayList<FieldInstance<?, ?>> packetFields;

    AvoidPayload(
            Supplier<? extends CustomPayload> supplier,
            CustomPacketPayload.Type<Payload> type
    ) {
        this.SUPPLIER = supplier;
        this.TYPE = type;
        this.STREAM_CODEC = CustomPacketPayload.codec(Payload::write, Payload::new);

        Class<?> klass = supplier.get().getClass();

        this.hasByteArray = !klass.isAnnotationPresent(NoManual.class);
        this.packetFields = new ArrayList<>();

        while (klass != CustomPayload.class) {
            for (var field : klass.getDeclaredFields()) {
                if (field.isAnnotationPresent(PacketField.class)) {
                    int order = field.getDeclaredAnnotation(Order.class)
                                     .value();

                    var controller = NetworkControllers.convert(field);

                    field.setAccessible(true);

                    packetFields.add(new FieldInstance<>(order, controller));
                }
            }

            klass = klass.getSuperclass();
        }

        packetFields.sort(Comparator.comparingInt(FieldInstance::order));
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

            for (var field : packetFields)
                read(friendlyByteBuf, field);

            if (hasByteArray)
                payload.read(friendlyByteBuf.readByteArray());

            payload.onRead();
        }

        private <A, N> void read(FriendlyByteBuf friendlyByteBuf, FieldInstance<A, N> field) {
            try {
                field.controller().set(payload, field.controller().converterFrom.apply(field.controller().reader.apply(friendlyByteBuf)));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to set payload @PacketField %s  /IllegalAccessException".formatted(field.controller().field.getName()), e);
            }
        }

        private void write(FriendlyByteBuf friendlyByteBuf) {
            payload.onWrite();

            for (var field : packetFields)
                store(friendlyByteBuf, field);

            if (hasByteArray)
                friendlyByteBuf.writeByteArray(payload.write());
        }

        @SuppressWarnings("unchecked")
        private <A, N> void store(FriendlyByteBuf friendlyByteBuf, FieldInstance<A, N> field) {
            try {
                field.controller().writer.accept(friendlyByteBuf, field.controller().converterTo.apply((A) field.controller().field.get(payload)));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to get payload @PacketField %s  /IllegalAccessException".formatted(field.controller().field.getName()), e);
            }
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
}
