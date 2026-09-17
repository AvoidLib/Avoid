package pl.olafcio.avoid.net.chat.component;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.chat.component.type.*;
import pl.olafcio.avoid.net.id.Identification;

import java.util.UUID;

/**
 * A namespace letting you create chat components.
 * <br/><br/>
 * There's a few types of them:
 * <ul>
 *     <li>{@linkplain Components#literal text literals} (plain),</li>
 *     <li>{@linkplain Components#keymap key bindings} (displays the key bound to a keybind),</li>
 *     <li>{@linkplain Components#translation translations} (translates the content using pack language files),</li>
 *     <li>{@linkplain Components#translationFallback translations with fallback} (translates the content, or if no translation is available, displays the fallback),</li>
 *     <li>{@linkplain Components#head player head} (displays the head object of the specified player),</li>
 *     <li>{@linkplain Components#atlas atlases} (displays a texture).</li>
 * </ul>
 */
@NeverRemoval
@ApiStatus.NonExtendable
public interface Components {
    ;

    /**
     * Returns a component displaying the provided text.
     */
    @NeverRemoval
    static TextComponent literal(String value) {
        return TextComponent.of(value);
    }

    /**
     * Returns a component displaying the value of the keybind represented by the provided ID.
     */
    @ApiStatus.Experimental
    static KeymapComponent keymap(String value) {
        return KeymapComponent.of(value);
    }

    /**
     * Returns a component displaying the value of the translation represented by the provided ID.
     */
    @NeverRemoval
    static TranslateComponent translation(String value) {
        return TranslateComponent.of(value);
    }

    /**
     * Returns a component displaying the value of the translation represented by the provided ID.
     */
    @NeverRemoval
    static TranslateFormattedComponent translation(String value, Object... format) {
        return TranslateFormattedComponent.of(value, format);
    }

    /**
     * Returns a component displaying the value of the translation represented by the provided ID, or if that translation
     *         does not exist, the fallback.
     */
    @NeverRemoval
    static TranslateFormattedComponent translationFallback(String value, String fallback) {
        return TranslateFormattedComponent.of(value, fallback);
    }

    /**
     * Returns a component displaying the value of the translation represented by the provided ID, or if that translation
     *         does not exist, the fallback.
     */
    @NeverRemoval
    static TranslateFormattedComponent translationFallback(String value, String fallback, Object... format) {
        return TranslateFormattedComponent.of(value, fallback, format);
    }

    /**
     * Returns a component displaying the head object of the player with the provided UUID.
     */
    @NeverRemoval
    static HeadComponent head(UUID uuid, boolean withHat) {
        return HeadComponent.of(uuid, withHat);
    }

    /**
     * Returns a component displaying the texture object from the provided atlas of the provided sprite.
     */
    @NeverRemoval
    static AtlasComponent atlas(Identification atlas, Identification sprite) {
        return AtlasComponent.of(atlas, sprite);
    }
}
