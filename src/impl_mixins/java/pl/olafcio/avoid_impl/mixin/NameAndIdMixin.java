package pl.olafcio.avoid_impl.mixin;

import net.minecraft.server.players.NameAndId;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import pl.olafcio.avoid_impl.mixininterface.INameAndId;

@Mixin(NameAndId.class)
public class NameAndIdMixin implements INameAndId {
    @Unique
    private Player player;

    @Override
    public Player avoid$player() {
        return player;
    }

    @Override
    public void avoid$player(Player value) {
        player = value;
    }
}
