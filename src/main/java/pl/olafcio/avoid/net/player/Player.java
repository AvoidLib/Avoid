package pl.olafcio.avoid.net.player;

import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.server.level.ServerPlayer;
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
            int id, EntityType type, IVect3 pos, IVect3 velocity, UUID uuid, String uuidString, BaseComponent<?> name,
            PlayerProfile profile, ServerGamePacketListenerImpl connection,
            net.minecraft.world.entity.player.Player player
    ) {
        super(id, type, pos, velocity, uuid, uuidString, name, player);

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

    /**
     * Sets the player's health and sends a SetHealthC2SPacket.<br/>
     * This looks more smooth than just a {@code setHealth} call on the client.
     */
    public void updateHealth(float health) {
        super.setHealth(health);
        this.updateHealthAndFood();
    }

    /**
     * Sends a SetHealthC2SPacket.
     */
    public void updateHealthAndFood() {
        var cast = (net.minecraft.world.entity.player.Player) underlyingEntity;
        var food = cast.getFoodData();

        connection.send(new ClientboundSetHealthPacket(getHealth(), food.getFoodLevel(), food.getSaturationLevel()));
    }

    public void setFoodLevel(int food) {
        __cast(net.minecraft.world.entity.player.Player.class).getFoodData().setFoodLevel(food);
    }

    public void setFoodSaturation(float saturation) {
        __cast(net.minecraft.world.entity.player.Player.class).getFoodData().setSaturation(saturation);
    }

    public void updateFoodLevel(int food) {
        setFoodLevel(food);
        updateHealthAndFood();
    }

    public void updateFoodSaturation(float saturation) {
        setFoodSaturation(saturation);
        updateHealthAndFood();
    }

    public int getFoodLevel() {
        return __cast(net.minecraft.world.entity.player.Player.class).getFoodData().getFoodLevel();
    }

    public float getFoodSaturation() {
        return __cast(net.minecraft.world.entity.player.Player.class).getFoodData().getSaturationLevel();
    }

    public void tickHunger() {
        __cast(net.minecraft.world.entity.player.Player.class).getFoodData().tick(__cast(ServerPlayer.class));
    }
}
