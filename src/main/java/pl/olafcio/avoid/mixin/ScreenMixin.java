package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import pl.olafcio.avoid.mixininterface.IScreen;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.screen.Drawer;
import pl.olafcio.avoid.net.screen.DrawerNative;
import pl.olafcio.avoid.net.screen.NativeScreenNative;
import pl.olafcio.avoid.net.screen.WidgetMarker;
import pl.olafcio.avoid.net.screen.event.ScreenInitEvent;
import pl.olafcio.avoid.net.screen_modifier.*;

import java.util.function.Predicate;

@Mixin(Screen.class)
public class ScreenMixin
       implements IScreen
{
    @Unique
    private pl.olafcio.avoid.net.screen.Screen screen;

    @Unique
    private ScreenModifier[] modifiers;

    @Unique
    private static final ScreenModifier[] EMPTY
                   = new ScreenModifier[0];

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;init()V"), method = "init(II)V")
    public void init(Screen instance, Operation<Void> original, int i, int j) {
        this.screen = NativeScreenNative.create(this);

        EventManager.fire(new ScreenInitEvent(screen, i, j));

        var typeOnly = ScreenModifiersNative.modifiers.get(((Screen) (Object) this).getClass());
        if (typeOnly == null) {
            modifiers = EMPTY;
            original.call(instance);
            return;
        }

        // TODO: Get this value from cache
        modifiers = new ScreenModifier[typeOnly.size()];

        int index = 0;

        for (var mod : typeOnly) {
            var screenModifier = mod.get();

            ScreenModifierNative.setScreen(screenModifier, screen);
            ScreenModifierNative.setCancelled(screenModifier, true);
            ScreenModifierNative.setSkip(screenModifier, index > 0);
            ScreenModifierNative.setFunction(screenModifier, "init", args -> {
                if (args.length != 0)
                    throw new IncorrectArgumentsException("'init' doesn't accept any arguments, got %d".formatted(args.length));

                return original.call(instance);
            });

            //shorten?
            this.modifiers[index++] = screenModifier;

            screenModifier.init();

            ScreenModifierNative.unsetFunction(screenModifier);

            if (ScreenModifierNative.isCancelled(screenModifier)) {
                return;
            }
        }
    }

    @Unique
    private void inject(Predicate<ScreenModifier> callback, Function original, int argCount, String operation) {
        int index = 0;

        for (var screenModifier : this.modifiers) {
            ScreenModifierNative.setScreen(screenModifier, screen);
            ScreenModifierNative.setCancelled(screenModifier, true);
            ScreenModifierNative.setSkip(screenModifier, index > 0);
            ScreenModifierNative.setFunction(screenModifier, operation, args -> {
                if (args.length != argCount)
                    if (argCount == 0)
                        throw new IncorrectArgumentsException("'%s' doesn't accept any arguments, got %d".formatted(operation, args.length));
                    else
                        throw new IncorrectArgumentsException("'%s' accepts only %d argument, got %d".formatted(operation, argCount, args.length));

                return original.invoke(args);
            });

            if (callback.test(screenModifier))
                // return true;
                return;

            ScreenModifierNative.unsetFunction(screenModifier);

            if (ScreenModifierNative.isCancelled(screenModifier))
                // super.mouseClicked(...) not called
                return;

            index++;
        }
    }

    @Unique
    private <T> void inject(Predicate<ScreenModifier> callback, Operation<T> original, Object instance, int argCount, String operation) {
        inject(callback, args -> {
            var total = new Object[args.length + 1];

            total[0] = instance;
            System.arraycopy(args, 0, total, 1, args.length);

            return original.call(total);
        }, argCount, operation);
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V"), method = "renderWithTooltipAndSubtitles")
    public void render(Screen instance, GuiGraphics guiGraphics, int i, int j, float f, Operation<Void> original) {
        if (this.modifiers.length > 0)
            inject(mod -> {
                mod.render(DrawerNative.create(guiGraphics), i, j, f);
                return false;
            }, args -> {
                return original.call(instance, DrawerNative.graphicsOf((Drawer) args[0]), args[1], args[2], args[3]);
            }, 4, "render");
        else
            original.call(instance, guiGraphics, i, j, f);
    }

    @Override
    public Renderable avoid$widget(WidgetMarker marker) {
        return null;
    }
}
