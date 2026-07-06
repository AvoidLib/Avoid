package pl.olafcio.avoid.mixin;

import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.keyboard.event.ClientKeyPressEvent;
import pl.olafcio.avoid.net.keyboard.event.ClientKeyEventNative;
import pl.olafcio.avoid.net.keyboard.event.ClientKeyReleaseEvent;

@Mixin(KeyboardHandler.class)
public class KeyboardMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Unique
    private static final ClientKeyPressEvent KEYPRESS
                       = ClientKeyEventNative.createPress();

    @Unique
    private static final ClientKeyReleaseEvent KEYRELEASE
                       = ClientKeyEventNative.createRelease();

    @Inject(at = @At("HEAD"), method = "keyPress")
    public void keyPress(long window, int action, KeyEvent event, CallbackInfo ci) {
        var gameWindow = this.minecraft.getWindow();
        if (window == gameWindow.handle()) {
            if (action == GLFW.GLFW_PRESS) {
                ClientKeyEventNative.change(KEYPRESS, event);
                EventManager.fire(KEYPRESS);
            } else if (action == GLFW.GLFW_RELEASE) {
                ClientKeyEventNative.change(KEYRELEASE, event);
                EventManager.fire(KEYRELEASE);
            }
        }
    }
}
