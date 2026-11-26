package pl.olafcio.avoid_loader;

import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.*;

/**
 * Tells the {@link pl.olafcio.avoid_platform.loader_impl.MCMetaAppenderService MCMetaAppenderService} to append a pack.mcmeta file onto all mods of this ModFile when possible.
 */
@ApiStatus.Experimental
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface PleaseAppendMCMeta {}
