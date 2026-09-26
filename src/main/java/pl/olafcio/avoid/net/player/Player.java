package pl.olafcio.avoid.net.player;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.UnknownNullability;
import pl.olafcio.avoid_impl.AvoidInternal;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.ImproperEnvironment;
import pl.olafcio.avoid.RunningEnv;
import pl.olafcio.avoid.annotations.env.ClientOnly;
import pl.olafcio.avoid.annotations.env.ClientUnsafe;
import pl.olafcio.avoid.annotations.env.ServerOnly;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid_impl.client.AvoidLibClient;
import pl.olafcio.avoid_impl.mixininterface.IServerPlayer;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid_impl.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid_impl.net.chat.converter.COToNative;
import pl.olafcio.avoid.net.command.executor.Executor;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid_impl.net.entity.EntityNative;
import pl.olafcio.avoid.net.entity_type.EntityType;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;
import pl.olafcio.avoid.net.player.exception.UncontrollablePlayerException;
import pl.olafcio.avoid.net.player.gamemode.GameMode;
import pl.olafcio.avoid_impl.net.player.gamemode.GameModeNative;
import pl.olafcio.avoid.net.player.values.RespawnPoint;
import pl.olafcio.avoid.net.player_server.ChatVisibility;
import pl.olafcio.avoid_impl.net.world.location.RespawnDataNative;
import pl.olafcio.avoid.net.world.vect3.IVect3;
import pl.olafcio.avoid_impl.net.world.vect3.Vect3Native;

import java.util.UUID;

/**
 * A client or server player.
 * <br/><br/>
 * This is mixed to ease massive refactorings in Minecraft code for AvoidLib.
 */
@NeverRemoval
public class Player extends Entity implements Executor {
    private final PlayerProfile profile;
    private final Object connection;

    public Player(
            int id, EntityType type, IVect3 pos, IVect3 velocity, UUID uuid, String uuidString, BaseComponent<?> name,
            PlayerProfile profile, @Nullable Object connection,
            net.minecraft.world.entity.player.Player player
    ) {
        super(id, type, pos, velocity, uuid, name, player);

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
        //#region sendMsg
        if (underlyingEntity instanceof ServerPlayer)
            ((ServerGamePacketListenerImpl) connection).send(new ClientboundSystemChatPacket(COToNative.from(component), false));

        else if (
                AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.CLIENT &&
                underlyingEntity instanceof LocalPlayer client
        )
            client.displayClientMessage(COToNative.from(component), false);

        else
            throw new UncontrollablePlayerException("[Player#sendMessage] Remote players can't be controlled from the client");
        //#regionend velocity:sendMsg
    }

    /**
     * Displays an actionbar for the player.<br/><br/>
     * This only works from the server and on the local player.
     * If you try using it on remote players from the client,
     * it will throw an exception.
     */
    @NeverRemoval
    public void sendActionbar(BaseComponent<?> component) {
        //#region sendActionbar
        if (underlyingEntity instanceof ServerPlayer)
            ((ServerGamePacketListenerImpl) connection).send(new ClientboundSystemChatPacket(COToNative.from(component), true));

        else if (
                AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.CLIENT &&
                underlyingEntity instanceof LocalPlayer client
        )
            client.displayClientMessage(COToNative.from(component), true);

        else
            throw new UncontrollablePlayerException("[Player#sendActionbar] Remote players can't be controlled from the client");
        //#regionend velocity:sendActionbar
    }

    /**
     * Displays a title for the player.<br/><br/>
     * This only works from the server and on the local player.
     * If you try using it on remote players from the client,
     * it will throw an exception.
     */
    @NeverRemoval
    public void sendTitle(BaseComponent<?> component) {
        //#region sendTitle
        if (underlyingEntity instanceof ServerPlayer)
            ((ServerGamePacketListenerImpl) connection).send(new ClientboundSetTitleTextPacket(COToNative.from(component)));

        else if (
                AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.CLIENT &&
                underlyingEntity instanceof LocalPlayer
        )
            AvoidLibClient.mc.gui.setTitle(COToNative.from(component));

        else
            throw new UncontrollablePlayerException("[Player#sendTitle] Remote players can't be controlled from the client");
        //#regionend velocity:sendActionbar
    }

    /**
     * Displays a subtitle for the player.<br/><br/>
     * This only works from the server and on the local player.
     * If you try using it on remote players from the client,
     * it will throw an exception.
     */
    @NeverRemoval
    public void sendSubtitle(BaseComponent<?> component) {
        //#region sendSubtitle
        if (underlyingEntity instanceof ServerPlayer)
            ((ServerGamePacketListenerImpl) connection).send(new ClientboundSetSubtitleTextPacket(COToNative.from(component)));

        else if (
                AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.CLIENT &&
                underlyingEntity instanceof LocalPlayer
        )
            AvoidLibClient.mc.gui.setSubtitle(COToNative.from(component));

        else
            throw new UncontrollablePlayerException("[Player#sendSubtitle] Remote players can't be controlled from the client");
        //#regionend velocity:sendSubtitle
    }

    /**
     * Sets the title animation timings for the player.<br/><br/>
     * This only works from the server and on the local player.
     * If you try using it on remote players from the client,
     * it will throw an exception.
     */
    @NeverRemoval
    public void setTitleAnimations(int fadeIn, int stay, int fadeOut) {
        //#region setTitleAnimations
        if (underlyingEntity instanceof ServerPlayer)
            ((ServerGamePacketListenerImpl) connection).send(new ClientboundSetTitlesAnimationPacket(fadeIn, stay, fadeOut));

        else if (
                AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.CLIENT &&
                underlyingEntity instanceof LocalPlayer
        )
            AvoidLibClient.mc.gui.setTimes(fadeIn, stay, fadeOut);

        else
            throw new UncontrollablePlayerException("[Player#setTitleAnimations] Remote players can't be controlled from the client");
        //#regionend velocity:setTitleAnimations
    }

    /**
     * Clears titles (and optionally title animation timings) for the player.<br/><br/>
     * This only works from the server and on the local player.
     * If you try using it on remote players from the client,
     * it will throw an exception.
     */
    @NeverRemoval
    public void clearTitles(boolean resetAnimations) {
        //#region clearTitles
        if (underlyingEntity instanceof ServerPlayer)
            ((ServerGamePacketListenerImpl) connection).send(new ClientboundClearTitlesPacket(resetAnimations));

        else if (
                AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.CLIENT &&
                underlyingEntity instanceof LocalPlayer
        )
        {
            AvoidLibClient.mc.gui.clearTitles();

            if (resetAnimations)
                AvoidLibClient.mc.gui.resetTitleTimes();
        }

        else
            throw new UncontrollablePlayerException("[Player#clearTitles] Remote players can't be controlled from the client");
        //#regionend velocity:clearTitles
    }

    /**
     * Sets the player's health and sends a SetHealthC2SPacket.<br/>
     * This looks more smooth than just a {@link #setHealth} call on the client.
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
        //#region no-velocity
        var cast = __castEnv(ServerPlayer.class, "[Player#updateHealthAndFood] This method can only be ran on server players!");
        var food = cast.getFoodData();

        ((ServerGamePacketListenerImpl) connection).send(new ClientboundSetHealthPacket(getHealth(), food.getFoodLevel(), food.getSaturationLevel()));
        //#regionend velocity:-
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
        //#region no-velocity
        __cast(net.minecraft.world.entity.player.Player.class).getFoodData().setFoodLevel(food);
        //#regionend velocity:-
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
        //#region no-velocity
        __cast(net.minecraft.world.entity.player.Player.class).getFoodData().setSaturation(saturation);
        //#regionend velocity:-
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
        //#region no-velocity
        __castEnv(ServerPlayer.class, "[Player#updateFoodLevel] This method can only be ran on server players!");

        setFoodLevel(food);
        updateHealthAndFood();
        //#regionend velocity:-
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
        //#region no-velocity
        __castEnv(ServerPlayer.class, "[Player#updateFoodSaturation] This method can only be ran on server players!");

        setFoodSaturation(saturation);
        updateHealthAndFood();
        //#regionend velocity:-
    }

    /**
     * Returns the player's food level.
     * <br/><br/>
     * This value is normally in range 0-20.<br/><br/>
     * 0 means the player is starving, while <br/>20 means the player is full.
     */
    @Range(from = 0, to = 20)
    public int getFoodLevel() {
        //#region no-velocity
        return __cast(net.minecraft.world.entity.player.Player.class).getFoodData().getFoodLevel();
        //#regionend velocity:-
    }

    /**
     * Returns the player's food saturation level.
     * <br/><br/>
     * I have honestly no clue what this value is,<br/>
     * but I think it's also 0-20, like the food level.
     */
    public float getFoodSaturation() {
        //#region no-velocity
        return __cast(net.minecraft.world.entity.player.Player.class).getFoodData().getSaturationLevel();
        //#regionend velocity:-
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
        //#region no-velocity
        __cast(net.minecraft.world.entity.player.Player.class).getFoodData().tick(__cast(ServerPlayer.class));
        //#regionend velocity:-
    }

    /**
     * Returns the player's gamemode.
     * <br/><br/>
     * This value may be null if the player's connection has not been fully initialized yet -
     * for example, when the player hasn't been spawned onto the world.
     */
    @UnknownNullability
    public GameMode getGameMode() {
        //#region no-velocity
        var gm = __cast(net.minecraft.world.entity.player.Player.class).gameMode();
        if (gm == null)
            return null;

        return GameModeNative.convertFrom(gm);
        //#regionend velocity:-
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
        //#region no-velocity
        __castEnv(ServerPlayer.class, "[Player#setGameMode] This method can only be ran on server players!")
                .setGameMode(GameModeNative.convert(gamemode));
        //#regionend velocity:-
    }

    /**
     * Gets the player's IP address.
     * <br/><br/>
     * This method only works on the server.
     */
    @ServerOnly
    public String getIP() {
        //#region velocity:ip
        return __castEnv(ServerPlayer.class, "[Player#getIP] This method can only be ran on server players!")
                       .getIpAddress();
        //#regionend velocity:ip
    }

    /**
     * Returns {@code true} if the player allows to be listed in the server's MOTD hover list.
     * <br/><br/>
     * This method only works on the server.
     */
    @ServerOnly
    public boolean allowsListing() {
        //#region velocity:al
        return __castEnv(ServerPlayer.class, "[Player#allowsListing] This method can only be ran on server players!")
                       .allowsListing();
        //#regionend velocity:al
    }

    /**
     * Returns the player's configured view distance.
     * <br/><br/>
     * This method only works on the server.
     */
    @ServerOnly
    public int requestedViewDistance() {
        //#region velocity:rvd
        return __castEnv(ServerPlayer.class, "[Player#requestedViewDistance] This method can only be ran on server players!")
                       .requestedViewDistance();
        //#regionend velocity:rvd
    }

    /**
     * Returns the player's configured chat visibility.
     * <br/><br/>
     * This method only works on the server.
     */
    @ServerOnly
    public ChatVisibility chatVisibility() {
        //#region velocity:cv
        return ChatVisibility.from(
                __castEnv(ServerPlayer.class, "[Player#chatVisibility] This method can only be ran on server players!")
                        .getChatVisibility()
        );
        //#regionend velocity:cv
    }

    /**
     * Returns the player's abilities.<br/>
     * The changes done in the returned object reflect to the player state.
     * <br/><br/>
     * <b>NOTE:</b> Keep in mind that updating these abilities client-side:
     * <ol>
     *     <li>may notify the server (and trigger the anticheat),</li>
     *     <li>may not affect the server state.</li>
     * </ol>
     */
    public Abilities getAbilities() {
        //#region no-velocity:abil
        return new Abilities(__cast(net.minecraft.world.entity.player.Player.class).getAbilities());
        //#regionend velocity:abil
    }

    /**
     * Grants an advancement to the player.
     * <br/><br/>
     * This method only works on the server.
     */
    @ServerOnly
    public void grantAdvancement(Identification id) {
        //#region no-velocity:advancement
        __castEnv(ServerPlayer.class, "[Player#grantAdvancement] This method can only be ran on server players!")
                    .getAdvancements().award(AvoidInternal.getServer().getAdvancements().get(IdentificationNative.convert(id)), "");
        //#regionend velocity:advancement
    }

    /**
     * Revokes an advancement from the player.
     * <br/><br/>
     * This method only works on the server.
     */
    @ServerOnly
    public void revokeAdvancement(Identification id) {
        //#region no-velocity:rvkadvancement
        __castEnv(ServerPlayer.class, "[Player#revokeAdvancement] This method can only be ran on server players!")
                .getAdvancements().revoke(AvoidInternal.getServer().getAdvancements().get(IdentificationNative.convert(id)), "");
        //#regionend velocity:rvkadvancement
    }

    /**
     * Returns the player's tablist order.
     * <br/><br/>
     * This method only works on the server.
     */
    @ServerOnly
    public int getTablistOrder() {
        //#region velocity:tblord
        return __castEnv(ServerPlayer.class, "[Player#getTablistOrder] This method can only be ran on server players!")
                .getTabListOrder();
        //#regionend velocity:tblord
    }

    /**
     * Sets the player's tablist order.
     * <br/><br/>
     * This method only works on the server.
     */
    @ServerOnly
    public void setTablistOrder(int value) {
        //#region velocity:settblord
        __castEnv(IServerPlayer.class, "[Player#setTablistOrder] This method can only be ran on server players!")
                .avoid$setTablistOrder(value);
        //#regionend velocity:settblord
    }

    /**
     * Resets the player's tablist order.
     * <br/><br/>
     * This method only works on the server.
     * <br/><br/>
     * <b>NOTE:</b> Not implemented on Paper (currently).
     */
    @ServerOnly
    public void resetTablistOrder() {
        //#region velocity:rstblord
        __castEnv(IServerPlayer.class, "[Player#resetTablistOrder] This method can only be ran on server players!")
                .avoid$setTablistOrder(null);
        //#regionend velocity:rstblord
    }

    /**
     * Returns the player's respawn point.<br/>
     * May be {@code null}.
     */
    @ServerOnly
    @Nullable
    public RespawnPoint getRespawnPoint() {
        //#region no-velocity:grp
        var config = __castEnv(ServerPlayer.class, "[Player#getRespawnPoint] This method can only be ran on server players!")
                          .getRespawnConfig();

        if (config == null)
            return null;

        return new RespawnPoint(
                RespawnDataNative.convertFrom(config.respawnData()),
                config.forced()
        );
        //#regionend velocity:grp
    }

    /**
     * Sets the player's respawn point.<br/>
     * May be {@code null}.
     */
    @ServerOnly
    public void setRespawnPoint(@Nullable RespawnPoint value) {
        //#region no-velocity:srp
        __castEnv(ServerPlayer.class, "[Player#setRespawnPoint] This method can only be ran on server players!")
                    .setRespawnPosition(value == null ? null : new ServerPlayer.RespawnConfig(
                            RespawnDataNative.convert(value.location()),
                            value.force()
                    ), false);
        //#regionend velocity:srp
    }

    /**
     * Sets the player's respawn point.<br/>
     * May be {@code null}.
     * @param notify If {@code true}, the player will be informed that his spawnpoint has changed.
     */
    @ServerOnly
    public void setRespawnPoint(@Nullable RespawnPoint value, boolean notify) {
        //#region no-velocity:srp2
        __castEnv(ServerPlayer.class, "[Player#setRespawnPoint] This method can only be ran on server players!")
                .setRespawnPosition(value == null ? null : new ServerPlayer.RespawnConfig(
                        RespawnDataNative.convert(value.location()),
                        value.force()
                ), notify);
        //#regionend velocity:srp2
    }

    @ServerOnly
    public void setSpawnExtraParticlesOnFall(boolean value) {
        //#region no-velocity:ssepof
        __castEnv(ServerPlayer.class, "[Player#setSpawnExtraParticlesOnFall] This method can only be ran on server players!")
                .setSpawnExtraParticlesOnFall(value);
        //#regionend velocity:ssepof
    }

    @ServerOnly
    public void setRaidOmenPosition(@Nullable BlockPos blockPos) {
        //#region no-velocity:srop
        __castEnv(ServerPlayer.class, "[Player#setRaidOmenPosition] This method can only be ran on server players!")
                .setRaidOmenPosition(blockPos == null ? null : BlockPosNative.convertFrom(blockPos));
        //#regionend velocity:srop
    }

    @ServerOnly
    public void clearRaidOmenPosition() {
        //#region no-velocity:crop
        __castEnv(ServerPlayer.class, "[Player#clearRaidOmenPosition] This method can only be ran on server players!")
                .clearRaidOmenPosition();
        //#regionend velocity:crop
    }

    @ServerOnly
    public @Nullable BlockPos getRaidOmenPosition() {
        //#region no-velocity:grop
        var pos = __castEnv(ServerPlayer.class, "[Player#getRaidOmenPosition] This method can only be ran on server players!")
                         .getRaidOmenPosition();

        return pos == null
                ? null
                : BlockPosNative.convert(pos);
        //#regionend velocity:grop
    }

    //#region del-velocity:inventory
    private final Inventory inventory
            = new Inventory(__cast(net.minecraft.world.entity.player.Player.class).getInventory());
    //#regionend velocity:inventory

    /**
     * Returns the player's inventory.
     * <br/><br/>
     * <b>NOTE:</b> The current client implementation is non-interactive.
     */
    @ClientUnsafe
    public Inventory getInventory() {
        return inventory;
    }

    //#region del-velocity:enderchest
    private final Container enderchest
            = new Container(__cast(net.minecraft.world.entity.player.Player.class).getEnderChestInventory());
    //#regionend velocity:enderchest

    /**
     * Returns the player's enderchest inventory.
     * <br/><br/>
     * <b>NOTE:</b> The current client implementation is non-interactive.
     */
    @ClientUnsafe
    public Container getEnderchest() {
        return enderchest;
    }

    /**
     * Returns whether the player can use operator blocks, such as command blocks.
     */
    public boolean canUseOPBlocks() {
        //#region no-velocity:cuob
        return __cast(net.minecraft.world.entity.player.Player.class).canUseGameMasterBlocks();
        //#regionend velocity:cuob
    }

    @ClientOnly
    public Raycast raycast(float distance) {
        //#region no-velocity:rc
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("[Player#raycast] This can be called only on local players");
        else if (!(underlyingEntity instanceof LocalPlayer))
            throw new UncontrollablePlayerException("[Player#raycast] This can be called only on local players");

        var result = __cast(LocalPlayer.class).raycastHitResult(distance, underlyingEntity);

        return new Raycast(
                result.getType() == HitResult.Type.ENTITY ? Raycast.Type.ENTITY :
                result.getType() == HitResult.Type.BLOCK  ? Raycast.Type.BLOCK  :
                                                            Raycast.Type.MISS,

                Vect3Native.convert(result.getLocation()),

                (
                             result instanceof EntityHitResult ehr &&
                    ehr.getEntity() instanceof net.minecraft.world.entity.Entity e
                )
                         ? EntityNative.convertFrom(e)
                         : null
        );  //at:raycast
        //#regionend velocity:rc
    }
}
