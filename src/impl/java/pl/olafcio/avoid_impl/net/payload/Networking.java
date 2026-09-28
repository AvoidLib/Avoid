package pl.olafcio.avoid_impl.net.payload;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.packet.PacketSide;
import pl.olafcio.avoid.net.payload.CustomPayload;
import pl.olafcio.avoid.net.payload.annotations.Side;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

@ApiStatus.Internal
public final class Networking {
    @ApiStatus.Internal
    private Networking() {}

    public static ArrayList<CustomPacketPayload.TypeAndCodec<?, ?>> c2s
            = new ArrayList<>();

    public static ArrayList<CustomPacketPayload.TypeAndCodec<?, ?>> s2c
            = new ArrayList<>();

    public static <T extends CustomPayload> void register(Identification id, Supplier<T> payload, Class<T> clazz) {
        if (
                c2s == null ||
                s2c == null
        )
            throw new RuntimeException(("""
                    [Networking#register] Registry already frozen! (You've tried to register your packet too late.)
                    > packet: %s
                    > solution:\s
                        1. try using Networking::register in your mod onLoad method instead
                        2. remove any Networking::register references and use @AutoCustomPayload instead""").formatted(clazz.getName()));

        var avoidpayload = new AvoidPayload(
                payload,
                new CustomPacketPayload.Type<>(IdentificationNative.convert(id))
        );

        var registration = new CustomPacketPayload.TypeAndCodec<>(avoidpayload.TYPE, avoidpayload.STREAM_CODEC);
        var side = clazz.getAnnotation(Side.class).value();

        if (side == PacketSide.BOTH) {
            c2s.add(registration);
            s2c.add(registration);
        } else if (side == PacketSide.C2S) {
            c2s.add(registration);
        } else /*if (side == PacketSide.S2C)*/ {
            s2c.add(registration);
        }
    }
}
