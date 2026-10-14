package pl.olafcio.avoid.mods.events_stage;

import org.jetbrains.annotations.ApiStatus;

/**
 * Fired when it's the correct time to add content to registries.<br/>
 * It's {@linkplain net.minecraft.core.registries.BuiltInRegistries#bootStrap right before} the {@link net.minecraft.core.registries.BuiltInRegistries#freeze freeze()}.
 * <br/><br/>
 * <b>Note:</b> You should only use this if you operate on net.minecraft or any dependencies that require it!
 */
@ApiStatus.Experimental
public record RegistryContentEvent() {}
