package pl.olafcio.avoid.mixin.screen;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import pl.olafcio.avoid.mixininterface.IScreen;
import pl.olafcio.avoid.net.screen.WidgetMarker;

@Mixin(SelectWorldScreen.class)
public class SelectWorldScreenMixin implements IScreen {
    @Shadow private Button selectButton;
    @Shadow private Button deleteButton;
    @Shadow @Nullable private Button renameButton;
    @Shadow @Nullable private Button copyButton;
    @Shadow @Nullable protected EditBox searchBox;

    @Override
    public Renderable avoid$widget(WidgetMarker marker) {
        if (marker == WidgetMarker.Singleplayer.SELECT_BUTTON) {
            return selectButton;
        } else if (marker == WidgetMarker.Singleplayer.DELETE_BUTTON) {
            return deleteButton;
        } else if (marker == WidgetMarker.Singleplayer.RENAME_BUTTON) {
            return renameButton;
        } else if (marker == WidgetMarker.Singleplayer.COPY_BUTTON) {
            return copyButton;
        } else if (marker == WidgetMarker.Singleplayer.SEARCH_BOX) {
            return searchBox;
        }

        return null;
    }
}
