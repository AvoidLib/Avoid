package pl.olafcio.avoid.mixininterface;

import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.function.Supplier;

@ApiStatus.Internal
public interface IMinecraft {
    HashMap<Class<? extends Screen>, Supplier<Screen>> avoidlib$overwrites();
}
