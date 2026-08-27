package pl.olafcio.avoid;

import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.InvocationTargetException;

@ApiStatus.Internal
public final class A4 {
    public static void init(Screen screen) {
        try {
            var init = screen.getClass().getDeclaredMethod("init");
            init.setAccessible(true);
            init.invoke(screen);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException("Couldn't Screen#init()", e);
        }
    }
}
