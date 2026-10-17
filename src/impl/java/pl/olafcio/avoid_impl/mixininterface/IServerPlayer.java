package pl.olafcio.avoid_impl.mixininterface;

import net.minecraft.network.chat.Component;

public interface IServerPlayer {
    void avoid$setTablistOrder(Integer value);
    void avoid$setTablistDisplayName(Component component);
}
