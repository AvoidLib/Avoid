package pl.olafcio.avoid.net.player;

import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COToNative;
import pl.olafcio.avoid.net.command.executor.Executor;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity_type.EntityType;
import pl.olafcio.avoid.net.world.IVect3;

import java.util.UUID;

public class Player extends Entity implements Executor {
    private final PlayerProfile profile;
    private final ServerGamePacketListenerImpl connection;

    public Player(
            int id, EntityType type, IVect3 velocity, UUID uuid, String uuidString, BaseComponent<?> name,
            PlayerProfile profile, ServerGamePacketListenerImpl connection
    ) {
        super(id, type, velocity, uuid, uuidString, name);

        this.profile = profile;
        this.connection = connection;
    }

    @Override
    public @NotNull String getName() {
        return "";
    }

    public String getNick() {
        return profile.name();
    }

    public void sendMessage(BaseComponent<?> component) {
        connection.send(new ClientboundSystemChatPacket(COToNative.from(component), false));
    }
}
