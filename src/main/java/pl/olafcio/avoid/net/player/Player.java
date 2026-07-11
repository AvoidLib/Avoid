package pl.olafcio.avoid.net.player;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.UnknownNullability;
import pl.olafcio.avoid.annotations.env.ClientUnsafe;
import pl.olafcio.avoid.annotations.env.ServerOnly;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COToNative;
import pl.olafcio.avoid.net.command.executor.Executor;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity_type.EntityType;
import pl.olafcio.avoid.net.player.exception.UncontrollablePlayerException;
import pl.olafcio.avoid.net.player.gamemode.GameMode;
import pl.olafcio.avoid.net.player.gamemode.GameModeNative;
import pl.olafcio.avoid.net.player_server.ChatVisibility;
import pl.olafcio.avoid.net.world.vect3.IVect3;

import java.util.UUID;

/**
 * A client or server player.
 * <br/><br/>
 * This is mixed to ease massive refactorings in Minecraft code for AvoidLib.
 */
@NeverRemoval
public class Player extends Entity implements Executor {
    private final PlayerProfile profile;
    private final ServerGamePacketListenerImpl connection;

    public Player(
            int id, EntityType type, IVect3 pos, IVect3 velocity, UUID uuid, String uuidString, BaseComponent<?> name,
            PlayerProfile profile, @Nullable ServerGamePacketListenerImpl connection,
            net.minecraft.world.entity.player.Player player
    ) {
        super(id, type, pos, velocity, uuid, uuidString, name, player);

        this.profile = profile;
        this.connection = connection;
    }

    @Override
    public @NotNull String getName() {
        return getNick().replace("§", "");
    }

    public String getNick() {
        return profile == null ? null : profile.name();
    }

    /**
     * Sends a message to the player.<br/><br/>
     * This only works from the server and on the local player.
     * If you try using it on remote players from the client,
     * it will throw an exception.
     */
    @NeverRemoval
    public void sendMessage(BaseComponent<?> component) {
        if (underlyingEntity instanceof ServerPlayer)
            connection.send(new ClientboundSystemChatPacket(COToNative.from(component), false));
        else if (underlyingEntity instanceof LocalPlayer client)
            client.displayClientMessage(COToNative.from(component), false);
        else
            throw new UncontrollablePlayerException("[Player#sendMessage] Remote players can't be controlled from the client");
    }

    /**
     * Sets the player's health and sends a SetHealthC2SPacket.<br/>
     * This looks more smooth than just a {@code setHealth} call on the client.
     */
    @ServerOnly
    public void updateHealth(float health) {
        __castEnv(ServerPlayer.class, "[Player#updateHealth] This method can only be ran on server players!");

        super.setHealth(health);
        this.updateHealthAndFood();
    }

    /**
     * Sends a SetHealthC2SPacket.
     */
    @ServerOnly
    public void updateHealthAndFood() {
        var cast = __castEnv(ServerPlayer.class, "[Player#updateHealthAndFood] This method can only be ran on server players!");
        var food = cast.getFoodData();

        connection.send(new ClientboundSetHealthPacket(getHealth(), food.getFoodLevel(), food.getSaturationLevel()));
    }

    /**
     * Sets the player's food level.
     * <br/><br/>
     * This value is normally in range 0-20.<br/><br/>
     * 0 means the player is starving, while <br/>20 means the player is full.
     * <br/><br/>
     * Using this method on the client may pose desync risks and/or other issues.
     */
    @ClientUnsafe
    public void setFoodLevel(@Range(from = 0, to = 20) int food) {
        __cast(net.minecraft.world.entity.player.Player.class).getFoodData().setFoodLevel(food);
    }

    /**
     * Sets the player's food saturation.
     * <br/><br/>
     * I have honestly no clue what this value is,<br/>
     * but I think it's also 0-20, like the food level.
     * <br/><br/>
     * Using this method on the client may pose desync risks and/or other issues.
     */
    @ClientUnsafe
    public void setFoodSaturation(float saturation) {
        __cast(net.minecraft.world.entity.player.Player.class).getFoodData().setSaturation(saturation);
    }

    /**
     * Updates (sets & syncs) the player's food level.
     * <br/><br/>
     * This value is normally in range 0-20.<br/><br/>
     * 0 means the player is starving, while <br/>20 means the player is full.
     * <br/><br/>
     * This method only works on the server.
     */
    @ServerOnly
    public void updateFoodLevel(@Range(from = 0, to = 20) int food) {
        __castEnv(ServerPlayer.class, "[Player#updateFoodLevel] This method can only be ran on server players!");

        setFoodLevel(food);
        updateHealthAndFood();
    }

    /**
     * Updates (sets & syncs) the player's food saturation.
     * <br/><br/>
     * I have honestly no clue what this value is,<br/>
     * but I think it's also 0-20, like the food level.
     * <br/><br/>
     * This method only works on the server.
     */
    @ServerOnly
    public void updateFoodSaturation(float saturation) {
        __castEnv(ServerPlayer.class, "[Player#updateFoodSaturation] This method can only be ran on server players!");

        setFoodSaturation(saturation);
        updateHealthAndFood();
    }

    /**
     * Returns the player's food level.
     * <br/><br/>
     * This value is normally in range 0-20.<br/><br/>
     * 0 means the player is starving, while <br/>20 means the player is full.
     */
    @Range(from = 0, to = 20)
    public int getFoodLevel() {
        return __cast(net.minecraft.world.entity.player.Player.class).getFoodData().getFoodLevel();
    }

    /**
     * Returns the player's food saturation level.
     * <br/><br/>
     * I have honestly no clue what this value is,<br/>
     * but I think it's also 0-20, like the food level.
     */
    public float getFoodSaturation() {
        return __cast(net.minecraft.world.entity.player.Player.class).getFoodData().getSaturationLevel();
    }

    /**
     * Ticks the player's food counters.
     * <br/><br/>
     * This is kind-of like {@code /tick step}, but for hunger.
     * <br/><br/>
     * This method only works on the server.
     */
    @ServerOnly
    public void tickHunger() {
        __cast(net.minecraft.world.entity.player.Player.class).getFoodData().tick(__cast(ServerPlayer.class));
    }

    /**
     * Returns the player's gamemode.
     * <br/><br/>
     * This value may be null if the player's connection has not been fully initialized yet -
     * for example, when the player hasn't been spawned onto the world.
     */
    @UnknownNullability
    public GameMode getGameMode() {
        var gm = __cast(net.minecraft.world.entity.player.Player.class).gameMode();
        if (gm == null)
            return null;

        return GameModeNative.convertFrom(gm);
    }

    /**
     * Sets the player's gamemode.
     * <br/><br/>
     * This method only works on the server.<br/>
     * Spoofing a player's gamemode on the client hasn't been implemented.<br/><br/>
     * If you need it, feel free to <a href="https://github.com/AvoidLib/Avoid/issues">make a GitHub feature request</a>.
     */
    @ServerOnly
    public void setGameMode(@NotNull GameMode gamemode) {
        __castEnv(ServerPlayer.class, "[Player#setGameMode] This method can only be ran on server players!")
                .setGameMode(GameModeNative.convert(gamemode));
    }

    /**
     * Gets the player's IP address.
     * <br/><br/>
     * This method only works on the server.
     */
    @ServerOnly
    public String getIP() {
        return __castEnv(ServerPlayer.class, "[Player#getIP] This method can only be ran on server players!")
                       .getIpAddress();
    }

    /**
     * Returns {@code true} if the player allows to be listed in the server's MOTD hover list.
     * <br/><br/>
     * This method only works on the server.
     */
    @ServerOnly
    public boolean allowsListing() {
        return __castEnv(ServerPlayer.class, "[Player#allowsListing] This method can only be ran on server players!")
                       .allowsListing();
    }

    /**
     * Returns the player's configured view distance.
     * <br/><br/>
     * This method only works on the server.
     */
    @ServerOnly
    public int requestedViewDistance() {
        return __castEnv(ServerPlayer.class, "[Player#requestedViewDistance] This method can only be ran on server players!")
                       .requestedViewDistance();
    }

    /**
     * Returns the player's configured chat visibility.
     * <br/><br/>
     * This method only works on the server.
     */
    @ServerOnly
    public ChatVisibility chatVisibility() {
        return ChatVisibility.from(
                __castEnv(ServerPlayer.class, "[Player#chatVisibility] This method can only be ran on server players!")
                        .getChatVisibility()
        );
    }
}
