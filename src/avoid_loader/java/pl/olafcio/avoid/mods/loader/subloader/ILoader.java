package pl.olafcio.avoid.mods.loader.subloader;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid_loader.PreModContainer;

/**
 * A subloader.
 * <br/><br/>
 * Subloaders are extensions to the Avoid mod loader.<br/>
 * They make the following possible:
 * <ul>
 *     <li>{@linkplain org.spongepowered.asm.mixin.Mixins#addConfiguration(String) loading/creating mixins dynamically,}</li>
 *     <li>{@linkplain pl.olafcio.avoid_loader.PreModLoader#addTransformer transforming classes dynamically,}</li>
 *     <li>{@linkplain pl.olafcio.avoid_loader.PreModLoader#addToClasspath modifying the game classpath.}</li>
 * </ul>
 * <br/>
 * You probably heard of them before; they're basically <i>coremods</i>.
 */
@ApiStatus.Experimental
public interface ILoader {
    void load();
    default void processAvoidMod(PreModContainer container) {}
}
