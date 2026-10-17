package pl.olafcio.avoid_impl.mixininterface;

import net.minecraft.world.entity.player.Player;

public interface INameAndId {
    Player avoid$player();
    void avoid$player(Player value);
}
