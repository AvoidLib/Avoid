package pl.olafcio.avoid.net.server;

import net.minecraft.server.players.NameAndId;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.AvoidInternal;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.player.Player;
import pl.olafcio.avoid.net.player.PlayerNative;
import pl.olafcio.avoid.net.world.World;
import pl.olafcio.avoid.net.world.WorldNative;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

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

    public static List<Operator> getOperators()  {
        return AvoidInternal.getServer().getPlayerList().getOps().getEntries().stream().map(op -> {
            var user = op.getUser();
            return new Operator(user.name(), user.id());
        }).toList();
    }

    public static void addOperator(String nick, UUID uuid) {
        AvoidInternal.getServer().getPlayerList().op(new NameAndId(uuid, nick));
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

    public static World getOverworld() {
        return WorldNative.make(AvoidInternal.getServer().getLevel(Level.OVERWORLD));
    }

    public static World getNether() {
        return WorldNative.make(AvoidInternal.getServer().getLevel(Level.NETHER));
    }

    public static World getEnd() {
        return WorldNative.make(AvoidInternal.getServer().getLevel(Level.END));
    }
}
