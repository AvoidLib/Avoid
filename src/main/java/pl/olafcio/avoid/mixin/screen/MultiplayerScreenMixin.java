package pl.olafcio.avoid.mixin.screen;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import pl.olafcio.avoid.mixininterface.IScreen;
import pl.olafcio.avoid.net.screen.WidgetMarker;

@Mixin(JoinMultiplayerScreen.class)
public class MultiplayerScreenMixin implements IScreen {
    @Shadow private Button selectButton;
    @Shadow private Button deleteButton;
    @Shadow private Button editButton;

    @Override
    public Renderable avoid$widget(WidgetMarker marker) {
        if (marker == WidgetMarker.Multiplayer.SELECT_BUTTON) {
            return selectButton;
        } else if (marker == WidgetMarker.Multiplayer.DELETE_BUTTON) {
            return deleteButton;
        } else if (marker == WidgetMarker.Multiplayer.EDIT_BUTTON) {
            return editButton;
        }

        return null;
    }
}
