package pl.olafcio.avoid_impl.net.screen;

import net.minecraft.client.input.KeyEvent;

public class KeyboardEvent extends pl.olafcio.avoid.net.screen.KeyboardEvent {
    KeyEvent event;

    KeyboardEvent() {}

    @Override
    public int getKey() {
        return event.key();
    }

    @Override
    public int getScancode() {
        return event.scancode();
    }

    @Override
    public int getModifiers() {
        return event.modifiers();
    }
}
