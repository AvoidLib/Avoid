package pl.olafcio.avoid_loader.subloaders;

import pl.olafcio.avoid_loader.AvoidModContainer;
import pl.olafcio.avoid_loader.interfaces.services.IModProcessor;

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
 *
 * @deprecated Implement a {@link IModProcessor IModProcessor} instead.
 */
@Deprecated(since = "v2.0", forRemoval = true)
public interface ILoader {
    void load();
    default void processAvoidMod(AvoidModContainer container) {}
}
