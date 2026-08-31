package pl.olafcio.avoid.net.screen.widget_impl;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import pl.olafcio.avoid.net.chat.converter.COToNative;
import pl.olafcio.avoid.net.screen.DrawerNative;
import pl.olafcio.avoid.net.screen.EventsInternal;
import pl.olafcio.avoid.net.screen.KeyboardEvent;
import pl.olafcio.avoid.net.screen.MouseEvent;
import pl.olafcio.avoid.net.screen.widget.able.*;

public record AWFull<T extends Renderable & Focusable & Hoverable & Narratable>(T element)
       implements net.minecraft.client.gui.components.Renderable,
                  GuiEventListener, NarratableEntry,
                  IAvoidWidget
{
    @Override
    public Renderable getAvoid() {
        return element;
    }

    //==================//
    //=== RENDERABLE ===//
    //==================//

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        element.render(DrawerNative.create(guiGraphics), i, j, f);
    }

    //==================//
    //=== NARRATABLE ===//
    //==================//

    @Override
    public void setFocused(boolean bl) {
        element.setFocused(bl);
    }

    @Override
    public boolean isActive() {
        return element.isActive();
    }

    @Override
    public boolean isFocused() {
        return element.isFocused();
    }

    @Override
    public NarrationPriority narrationPriority() {
        return element.isFocused() ? NarrationPriority.FOCUSED :
               element.isHovered() ? NarrationPriority.HOVERED :
                                     NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.USAGE, COToNative.from(element.getNarrationMessage()));
    }

    //=====================//
    //=== EVENTLISTENER ===//
    //=====================//

    //at:stfai
    @Override
    public boolean shouldTakeFocusAfterInteraction() {
        return element.shouldFocusAfterInteraction();
    }
    //atend:stfai

    @Override
    public boolean isMouseOver(double d, double e) {
        return element.isHovered(d, e);
    }

    private static final KeyboardEvent keyboard
                       = EventsInternal.makeKeyboard();

    @Override
    public boolean keyReleased(KeyEvent keyEvent) {
        if (element instanceof Attachable) {
            EventsInternal.set(keyboard, keyEvent);
            return ((Attachable) element).onKeyReleased(keyboard);
        }

        return GuiEventListener.super.keyReleased(keyEvent);
    }

    @Override
    public boolean keyPressed(KeyEvent keyEvent) {
        if (element instanceof Attachable) {
            EventsInternal.set(keyboard, keyEvent);
            return ((Attachable) element).onKeyPressed(keyboard);
        }

        return GuiEventListener.super.keyPressed(keyEvent);
    }

    @Override
    public boolean mouseScrolled(double d, double e, double f, double g) {
        if (element instanceof Attachable) {
            return ((Attachable) element).onMouseScrolled(d, e, f, g);
        }

        return GuiEventListener.super.mouseScrolled(d, e, f, g);
    }

    private static final MouseEvent mouse
                       = EventsInternal.makeMouse();

    @Override
    public boolean mouseDragged(MouseButtonEvent mouseButtonEvent, double d, double e) {
        if (element instanceof Attachable) {
            EventsInternal.set(mouse, mouseButtonEvent);
            return ((Attachable) element).onMouseDragged(mouse, d, e);
        }

        return GuiEventListener.super.mouseDragged(mouseButtonEvent, d, e);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent mouseButtonEvent) {
        if (element instanceof Attachable) {
            EventsInternal.set(mouse, mouseButtonEvent);
            return ((Attachable) element).onMouseReleased(mouse);
        }

        return GuiEventListener.super.mouseReleased(mouseButtonEvent);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean bl) {
        if (element instanceof Attachable) {
            EventsInternal.set(mouse, mouseButtonEvent);
            return ((Attachable) element).onMousePressed(mouse, bl);
        }

        return GuiEventListener.super.mouseClicked(mouseButtonEvent, bl);
    }

    @Override
    public void mouseMoved(double d, double e) {
        if (element instanceof Attachable) {
            ((Attachable) element).onMouseMoved(d, e);
        }
    }
}
