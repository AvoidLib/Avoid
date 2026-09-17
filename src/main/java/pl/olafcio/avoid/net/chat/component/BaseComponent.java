package pl.olafcio.avoid.net.chat.component;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.chat.component.event.Click;
import pl.olafcio.avoid.net.chat.component.event.Hover;
import pl.olafcio.avoid.net.chat.component.type.ParentComponent;
import pl.olafcio.avoid.net.id.Identification;

import java.awt.*;
import java.util.function.Consumer;

/**
 * A chat component.
 * <br/><br/>
 * This is an object representing a text segment displayed in the UI.<br/>
 * It can be the chat, a screen, an actionbar, a title, an entity display name, in the scoreboard, in the tablist, maybe somewhere even more.
 * <br/><br/>
 * It's very often called a chat component. That is because the name has normalized in server-only environments such as
 *      Paper, in which there's no screens and fewer places to send these components in.
 * <br><br/>
 * Also, these plugin-based server-only platforms are older than Avoid - in which the times of there might've been no
 *       other places than chat. Beta minecraft, titles, scoreboards, tablist didn't exist (":
 * <br/><br/>
 * To make components, look at the {@link Components} namespace.<br>
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
public abstract class BaseComponent<T extends BaseComponent<T>> {
    ChatStyle style
              = new ChatStyle();

    @ApiStatus.Experimental
    protected BaseComponent() {}

    // ///////////////// //
    //  STYLE MODIFIERS  //
    // ///////////////// //

    @NeverRemoval
    @SuppressWarnings("unchecked")
    public T color(Color color) {
        this.style.color = color;
        return (T) this;
    }

    @NeverRemoval
    @SuppressWarnings("unchecked")
    public T color(Colors color) {
        this.style.color = color.getColor();
        return (T) this;
    }

    @NeverRemoval
    @SuppressWarnings("unchecked")
    public T shadow(Color color) {
        this.style.shadow = color;
        return (T) this;
    }

    @NeverRemoval
    @SuppressWarnings("unchecked")
    public T shadow(Colors color) {
        this.style.shadow = color.getColor();
        return (T) this;
    }

    private static final Color INVISIBLE
                   = new Color(0, true);

    @NeverRemoval
    @SuppressWarnings("unchecked")
    public T noShadow() {
        this.style.shadow = INVISIBLE;
        return (T) this;
    }

    @NeverRemoval
    @SuppressWarnings("unchecked")
    public T bold(boolean state) {
        this.style.bold = state;
        return (T) this;
    }

    @NeverRemoval
    @SuppressWarnings("unchecked")
    public T italic(boolean state) {
        this.style.italic = state;
        return (T) this;
    }

    @NeverRemoval
    @SuppressWarnings("unchecked")
    public T underlined(boolean state) {
        this.style.underlined = state;
        return (T) this;
    }

    @NeverRemoval
    @SuppressWarnings("unchecked")
    public T strikethrough(boolean state) {
        this.style.strikethrough = state;
        return (T) this;
    }

    @NeverRemoval
    @SuppressWarnings("unchecked")
    public T obfuscated(boolean state) {
        this.style.obfuscated = state;
        return (T) this;
    }

    @NeverRemoval
    @SuppressWarnings("unchecked")
    public T font(Identification font) {
        this.style.font = font;
        return (T) this;
    }

    @NeverRemoval
    @SuppressWarnings("unchecked")
    public T click(Click click) {
        this.style.click = click;
        return (T) this;
    }

    @NeverRemoval
    @SuppressWarnings("unchecked")
    public T hover(Hover hover) {
        this.style.hover = hover;
        return (T) this;
    }

    // ///////////////// //
    //  STYLE ACCESSORS  //
    // ///////////////// //

    @ApiStatus.Experimental
    @SuppressWarnings("unchecked")
    public T styleFrom(BaseComponent<?> component) {
        this.style = component.style;
        return (T) this;
    }

    @ApiStatus.Experimental
    @SuppressWarnings("unchecked")
    public T styleAs(ChatStyle style) {
        this.style = style;
        return (T) this;
    }

    @ApiStatus.Experimental
    public ChatStyle style() {
        return this.style;
    }

    @ApiStatus.Experimental
    public BaseComponent<T> style(Consumer<ChatStyle> modifier) {
        modifier.accept(this.style);
        return this;
    }

    // ///////////////// //
    //  PARENT CREATORS  //
    // ///////////////// //

    @NeverRemoval
    public ParentComponent append(BaseComponent<?> comp) {
        return ParentComponent.of(this)
                              .append(comp);
    }

    @ApiStatus.Experimental
    public ParentComponent prepend(BaseComponent<?> comp) {
        return ParentComponent.of(this)
                              .prepend(comp);
    }

    @ApiStatus.Experimental
    public ParentComponent insert(int index, BaseComponent<?> comp) {
        return ParentComponent.of(this)
                              .insert(index, comp);
    }

    // ////// //
    //  MISC  //
    // ////// //

//    public static FormattedCharSequence getVisualSequence() {
//     TODO
//    }
}
