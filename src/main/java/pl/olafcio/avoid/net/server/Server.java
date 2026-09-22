package pl.olafcio.avoid.net.server;

import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.players.NameAndId;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid_impl.AvoidInternal;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.ImproperEnvironment;
import pl.olafcio.avoid.RunningEnv;
import pl.olafcio.avoid.annotations.env.DedicatedServerOnly;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.world.World;
import pl.olafcio.avoid.net.world.WorldNative;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

@ApiStatus.Experimental
public final class Server {
    @ApiStatus.Internal
    private Server() {}

    public static String[] getPlayerNicks() {
        return AvoidInternal.getServer().getPlayerNames();
    }

    public static int getPlayerCount() {
        return AvoidInternal.getServer().getPlayerCount();
    }

    public static int getMaxPlayerCount() {
        return AvoidInternal.getServer().getMaxPlayers();
    }

    public static List<Player> getPlayers() {
        return AvoidInternal.getServer().getPlayerList().getPlayers().stream()
                                                                     .map(PlayerNative::convertFrom)
                                                                     .toList();
    }

    public static Player getPlayer(String nick) {
        var ret = AvoidInternal.getServer().getPlayerList().getPlayer(nick);
        if (ret == null)
            return null;

        return PlayerNative.convertFrom(ret);
    }

    public static Player getPlayer(UUID uuid) {
        var ret = AvoidInternal.getServer().getPlayerList().getPlayer(uuid);
        if (ret == null)
            return null;

        return PlayerNative.convertFrom(ret);
    }

    public static List<Operator> getOperators()  {
        return AvoidInternal.getServer().getPlayerList().getOps().getEntries().stream().map(op -> {
            var user = op.getUser();
            return new Operator(user.name(), user.id());
        }).toList();
    }

    /**
     * <b>NOTE:</b> This may perform a Mojang servers lookup.
     */
    public static void addOperator(String nick) {
        var profile = AvoidInternal.getServer().services().nameToIdCache().get(nick).orElseThrow();

        AvoidInternal.getServer().getPlayerList().op(profile);
    }

    public static void addOperator(String nick, UUID uuid) {
        AvoidInternal.getServer().getPlayerList().op(new NameAndId(uuid, nick));
    }

    /**
     * <b>NOTE:</b> This may perform a Mojang servers lookup.
     */
    public static void removeOperator(String nick) {
        var profile = AvoidInternal.getServer().services().nameToIdCache().get(nick).orElseThrow();

        AvoidInternal.getServer().getPlayerList().deop(profile);
    }

    public static void removeOperator(String nick, UUID uuid) {
        AvoidInternal.getServer().getPlayerList().deop(new NameAndId(uuid, nick));
    }

    public static void removeOperator(Operator operator) {
        AvoidInternal.getServer().getPlayerList().deop(new NameAndId(operator.uuid(), operator.nick()));
    }

    public static void clearOperators() {
        AvoidInternal.getServer().getPlayerList().getOps().clear();
    }

    /**
     * <b>NOTE:</b> This may perform a Mojang servers lookup.
     */
    public static boolean canBypassPlayerLimit(String nick) {
        var profile = AvoidInternal.getServer().services().nameToIdCache().get(nick).orElseThrow();

        return AvoidInternal.getServer().getPlayerList().getOps().canBypassPlayerLimit(profile);
    }

    public static boolean canBypassPlayerLimit(String nick, UUID uuid) {
        return AvoidInternal.getServer().getPlayerList().getOps().canBypassPlayerLimit(new NameAndId(uuid, nick));
    }

    public static boolean canBypassPlayerLimit(Operator operator) {
        return AvoidInternal.getServer().getPlayerList().getOps().canBypassPlayerLimit(new NameAndId(operator.uuid(), operator.nick()));
    }

    public static List<World> getWorlds() {
        var list = new ArrayList<World>();

        AvoidInternal.getServer().getAllLevels().forEach(level -> {
            list.add(WorldNative.make(level));
        });

        return list;
    }

    public static void eachEntity(Consumer<Entity> callback) {
        var worlds = getWorlds();

        for (var world : worlds)
            world.eachEntity(callback);
    }

    public static boolean findEntity(Predicate<Entity> callback) {
        var worlds = getWorlds();

        for (var world : worlds)
            if (world.findEntity(callback))
                return true;

        return false;
    }

    public static World getOverworld() {
        return WorldNative.make(AvoidInternal.getServer().getLevel(Level.OVERWORLD));
    }

    public static World getNether() {
        return WorldNative.make(AvoidInternal.getServer().getLevel(Level.NETHER));
    }

    public static World getEnd() {
        return WorldNative.make(AvoidInternal.getServer().getLevel(Level.END));
    }

    public static void stopServer() {
        AvoidInternal.getServer().stopServer();
    }

    @DedicatedServerOnly
    public static Properties getProperties() {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.SERVER)
            throw new ImproperEnvironment("[Server#getProperties] Can only be called from the server");

        return new Properties(((DedicatedServer) AvoidInternal.getServer()).getProperties());
    }
}
