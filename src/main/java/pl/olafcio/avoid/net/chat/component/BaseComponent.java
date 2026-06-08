package pl.olafcio.avoid.net.chat.component;

import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.chat.component.event.Click;
import pl.olafcio.avoid.net.chat.component.event.Hover;
import pl.olafcio.avoid.net.chat.component.type.ParentComponent;
import pl.olafcio.avoid.net.id.Identification;

import java.awt.*;
import java.util.function.Consumer;

@WillRefactor(aspect = "name")
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
    public void styleFrom(BaseComponent<?> component) {
        this.style = component.style;
    }

    @ApiStatus.Experimental
    public void styleAs(ChatStyle style) {
        this.style = style;
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
