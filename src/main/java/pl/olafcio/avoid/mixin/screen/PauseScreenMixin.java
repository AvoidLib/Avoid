package pl.olafcio.avoid.mixin.screen;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import pl.olafcio.avoid.mixininterface.IScreen;
import pl.olafcio.avoid.net.screen.WidgetMarker;

import java.util.function.Supplier;

@Mixin(PauseScreen.class)
public class PauseScreenMixin implements IScreen {
    @Shadow
    private @Nullable Button disconnectButton;

    @Unique
    private Renderable back_to_game,
                       advancements,
                       stats,
                       options,
                       open_to_lan,
                       player_reporting;

    @Override
    public Renderable avoid$widget(WidgetMarker marker) {
        if (marker == WidgetMarker.PauseScreen.DISCONNECT_BUTTON) {
            return disconnectButton;
        } else if (marker == WidgetMarker.PauseScreen.BACK_TO_GAME_BUTTON) {
            return back_to_game;
        } else if (marker == WidgetMarker.PauseScreen.ADVANCEMENTS_BUTTON) {
            return advancements;
        } else if (marker == WidgetMarker.PauseScreen.STATS_BUTTON) {
            return stats;
        } else if (marker == WidgetMarker.PauseScreen.OPTIONS_BUTTON) {
            return options;
        } else if (marker == WidgetMarker.PauseScreen.OPEN_TO_LAN_BUTTON) {
            return open_to_lan;
        } else if (marker == WidgetMarker.PauseScreen.PLAYER_REPORTING_BUTTON) {
            return player_reporting;
        }

        return null;
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/Button$Builder;build()Lnet/minecraft/client/gui/components/Button;", ordinal = 0), method = "createPauseMenu")
    public Button createPauseMenu__build__0(Button.Builder instance, Operation<Button> original) {
        return (Button)(back_to_game = original.call(instance));
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/PauseScreen;openScreenButton(Lnet/minecraft/network/chat/Component;Ljava/util/function/Supplier;)Lnet/minecraft/client/gui/components/Button;", ordinal = 0), method = "createPauseMenu")
    public Button createPauseMenu__openScreenButton__0(PauseScreen instance, Component component, Supplier<Screen> supplier, Operation<Button> original) {
        return (Button)(advancements = original.call(instance, component, supplier));
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/PauseScreen;openScreenButton(Lnet/minecraft/network/chat/Component;Ljava/util/function/Supplier;)Lnet/minecraft/client/gui/components/Button;", ordinal = 1), method = "createPauseMenu")
    public Button createPauseMenu__openScreenButton__1(PauseScreen instance, Component component, Supplier<Screen> supplier, Operation<Button> original) {
        return (Button)(stats = original.call(instance, component, supplier));
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/PauseScreen;openScreenButton(Lnet/minecraft/network/chat/Component;Ljava/util/function/Supplier;)Lnet/minecraft/client/gui/components/Button;", ordinal = 2), method = "createPauseMenu")
    public Button createPauseMenu__openScreenButton__2(PauseScreen instance, Component component, Supplier<Screen> supplier, Operation<Button> original) {
        return (Button)(options = original.call(instance, component, supplier));
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/PauseScreen;openScreenButton(Lnet/minecraft/network/chat/Component;Ljava/util/function/Supplier;)Lnet/minecraft/client/gui/components/Button;", ordinal = 3), method = "createPauseMenu")
    public Button createPauseMenu__openScreenButton__3(PauseScreen instance, Component component, Supplier<Screen> supplier, Operation<Button> original) {
        return (Button)(open_to_lan = original.call(instance, component, supplier));
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/PauseScreen;openScreenButton(Lnet/minecraft/network/chat/Component;Ljava/util/function/Supplier;)Lnet/minecraft/client/gui/components/Button;", ordinal = 4), method = "createPauseMenu")
    public Button createPauseMenu__openScreenButton__4(PauseScreen instance, Component component, Supplier<Screen> supplier, Operation<Button> original) {
        return (Button)(player_reporting = original.call(instance, component, supplier));
    }
}
