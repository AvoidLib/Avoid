package pl.olafcio.avoid.mixin;

import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import pl.olafcio.avoid.mixininterface.IScreen;

@Mixin(Screen.class)
public class ScreenMixin
       implements IScreen
{}
