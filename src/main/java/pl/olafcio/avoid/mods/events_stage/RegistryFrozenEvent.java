package pl.olafcio.avoid.mods.events_stage;

import org.jetbrains.annotations.ApiStatus;

/**
 * Fired when all registries have been frozen.<br/>
 * It's {@linkplain net.minecraft.core.registries.BuiltInRegistries#bootStrap in} the last iteration of {@link net.minecraft.core.registries.BuiltInRegistries#freeze freeze()}.
 * <br/><br/>
 * <b>Note:</b> You should only use this if you operate on net.minecraft or any dependencies that require it!
 */
@ApiStatus.Experimental
public record RegistryFrozenEvent() {}
