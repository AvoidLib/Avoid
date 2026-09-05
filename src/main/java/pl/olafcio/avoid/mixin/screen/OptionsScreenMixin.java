package pl.olafcio.avoid.mixin.screen;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import pl.olafcio.avoid.mixininterface.IScreen;
import pl.olafcio.avoid.net.screen.WidgetMarker;

import java.util.function.Supplier;

@Mixin(OptionsScreen.class)
public class OptionsScreenMixin implements IScreen {
    @Unique
    private Renderable skinCustomization,
                       sounds,
                       video,
                       controls,
                       language,
                       chat,
                       resourcePack,
                       accessibility,
                       telemetry,
                       creditsAndAttribution;

    @Override
    public Renderable avoid$widget(WidgetMarker marker) {
        if (marker == WidgetMarker.Options.ACCESSIBILITY)
            return accessibility;
        else if (marker == WidgetMarker.Options.SOUNDS)
            return sounds;
        else if (marker == WidgetMarker.Options.SKIN_CUSTOMIZATION)
            return skinCustomization;
        else if (marker == WidgetMarker.Options.VIDEO)
            return video;
        else if (marker == WidgetMarker.Options.CONTROLS)
            return controls;
        else if (marker == WidgetMarker.Options.LANGUAGE)
            return language;
        else if (marker == WidgetMarker.Options.CHAT)
            return chat;
        else if (marker == WidgetMarker.Options.RESOURCE_PACK)
            return resourcePack;
        else if (marker == WidgetMarker.Options.TELEMETRY)
            return telemetry;
        else if (marker == WidgetMarker.Options.CREDITS_AND_ATTRIBUTION)
            return creditsAndAttribution;

        return null;
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/options/OptionsScreen;openScreenButton(Lnet/minecraft/network/chat/Component;Ljava/util/function/Supplier;)Lnet/minecraft/client/gui/components/Button;", ordinal = 0), method = "init")
    public Button init__openScreenButton__0(OptionsScreen instance, Component component, Supplier<Screen> supplier, Operation<Button> original) {
        return (Button)(skinCustomization = original.call(instance, component, supplier));
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/options/OptionsScreen;openScreenButton(Lnet/minecraft/network/chat/Component;Ljava/util/function/Supplier;)Lnet/minecraft/client/gui/components/Button;", ordinal = 1), method = "init")
    public Button init__openScreenButton__1(OptionsScreen instance, Component component, Supplier<Screen> supplier, Operation<Button> original) {
        return (Button)(sounds = original.call(instance, component, supplier));
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/options/OptionsScreen;openScreenButton(Lnet/minecraft/network/chat/Component;Ljava/util/function/Supplier;)Lnet/minecraft/client/gui/components/Button;", ordinal = 2), method = "init")
    public Button init__openScreenButton__2(OptionsScreen instance, Component component, Supplier<Screen> supplier, Operation<Button> original) {
        return (Button)(video = original.call(instance, component, supplier));
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/options/OptionsScreen;openScreenButton(Lnet/minecraft/network/chat/Component;Ljava/util/function/Supplier;)Lnet/minecraft/client/gui/components/Button;", ordinal = 3), method = "init")
    public Button init__openScreenButton__3(OptionsScreen instance, Component component, Supplier<Screen> supplier, Operation<Button> original) {
        return (Button)(controls = original.call(instance, component, supplier));
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/options/OptionsScreen;openScreenButton(Lnet/minecraft/network/chat/Component;Ljava/util/function/Supplier;)Lnet/minecraft/client/gui/components/Button;", ordinal = 4), method = "init")
    public Button init__openScreenButton__4(OptionsScreen instance, Component component, Supplier<Screen> supplier, Operation<Button> original) {
        return (Button)(language = original.call(instance, component, supplier));
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/options/OptionsScreen;openScreenButton(Lnet/minecraft/network/chat/Component;Ljava/util/function/Supplier;)Lnet/minecraft/client/gui/components/Button;", ordinal = 5), method = "init")
    public Button init__openScreenButton__5(OptionsScreen instance, Component component, Supplier<Screen> supplier, Operation<Button> original) {
        return (Button)(chat = original.call(instance, component, supplier));
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/options/OptionsScreen;openScreenButton(Lnet/minecraft/network/chat/Component;Ljava/util/function/Supplier;)Lnet/minecraft/client/gui/components/Button;", ordinal = 6), method = "init")
    public Button init__openScreenButton__6(OptionsScreen instance, Component component, Supplier<Screen> supplier, Operation<Button> original) {
        return (Button)(resourcePack = original.call(instance, component, supplier));
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/options/OptionsScreen;openScreenButton(Lnet/minecraft/network/chat/Component;Ljava/util/function/Supplier;)Lnet/minecraft/client/gui/components/Button;", ordinal = 7), method = "init")
    public Button init__openScreenButton__7(OptionsScreen instance, Component component, Supplier<Screen> supplier, Operation<Button> original) {
        return (Button)(accessibility = original.call(instance, component, supplier));
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/options/OptionsScreen;openScreenButton(Lnet/minecraft/network/chat/Component;Ljava/util/function/Supplier;)Lnet/minecraft/client/gui/components/Button;", ordinal = 8), method = "init")
    public Button init__openScreenButton__8(OptionsScreen instance, Component component, Supplier<Screen> supplier, Operation<Button> original) {
        return (Button)(telemetry = original.call(instance, component, supplier));
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/options/OptionsScreen;openScreenButton(Lnet/minecraft/network/chat/Component;Ljava/util/function/Supplier;)Lnet/minecraft/client/gui/components/Button;", ordinal = 9), method = "init")
    public Button init__openScreenButton__9(OptionsScreen instance, Component component, Supplier<Screen> supplier, Operation<Button> original) {
        return (Button)(creditsAndAttribution = original.call(instance, component, supplier));
    }
}
