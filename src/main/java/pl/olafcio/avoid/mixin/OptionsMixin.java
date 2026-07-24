package pl.olafcio.avoid.mixin;

import com.google.common.collect.Lists;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.net.keyboard.bind.KeybindsNative;

@Mixin(Options.class)
public class OptionsMixin {
    @Shadow
    @Final
    @Mutable
    public KeyMapping[] keyMappings;

    @Inject(at = @At("TAIL"), method = "<init>")
    private void clinit(CallbackInfo ci) {
        var mappings = Lists.newArrayList(keyMappings);

        mappings.addAll(KeybindsNative.keymappings);

        keyMappings = mappings.toArray(KeyMapping[]::new);
    }
}
