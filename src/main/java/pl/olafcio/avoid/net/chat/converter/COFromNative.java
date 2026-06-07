package pl.olafcio.avoid.net.chat.converter;

import net.minecraft.core.Holder;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.KeybindContents;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.dialog.Dialog;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.component.event.Click;
import pl.olafcio.avoid.net.chat.component.event.Hover;
import pl.olafcio.avoid.net.chat.component.type.KeymapComponent;
import pl.olafcio.avoid.net.chat.component.type.TextComponent;
import pl.olafcio.avoid.net.chat.component.type.TranslateComponent;
import pl.olafcio.avoid.net.entity_type.EntityTypeNative;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.stack.ItemStackNative;
import pl.olafcio.avoid.net.nbt.NbtNative;

import java.awt.*;
import java.net.URI;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public final class COFromNative {
    @ApiStatus.Internal
    private COFromNative() {}

    public static BaseComponent<?> from(Component input) {
        BaseComponent<?> comp;

        if (input.getContents() instanceof PlainTextContents content)
            comp = TextComponent.of(content.text());
        else if (input.getContents() instanceof TranslatableContents content)
            comp = TranslateComponent.of(content.getKey(), content.getFallback());
        else if (input.getContents() instanceof KeybindContents content)
            comp = KeymapComponent.of(content.getName());
        else throw new RuntimeException("Unknown minecraft component type '" + input + "'");

        if (input.getStyle().getColor() != null) comp.color(new Color(input.getStyle().getColor().getValue()));
        if (input.getStyle().getShadowColor() != null) comp.shadow(new Color(input.getStyle().getShadowColor()));

        if (input.getStyle().isBold()) comp.bold(input.getStyle().isBold());
        if (input.getStyle().isItalic()) comp.italic(input.getStyle().isItalic());
        if (input.getStyle().isUnderlined()) comp.underlined(input.getStyle().isUnderlined());
        if (input.getStyle().isStrikethrough()) comp.strikethrough(input.getStyle().isStrikethrough());
        if (input.getStyle().isObfuscated()) comp.obfuscated(input.getStyle().isObfuscated());

        if (input.getStyle().getFont() instanceof FontDescription.Resource(Identifier id)) comp.font(IdentificationNative.convertFrom(id));

        if (input.getStyle().getClickEvent() != null) comp.click(input.getStyle().getClickEvent() instanceof ClickEvent.OpenFile(String path) ? new Click.StartFile(path) :
                                                                 input.getStyle().getClickEvent() instanceof ClickEvent.OpenUrl(URI url) ? new Click.StartURL(url) :
                                                                 input.getStyle().getClickEvent() instanceof ClickEvent.CopyToClipboard(String text) ? new Click.Copy(text) :
                                                                 input.getStyle().getClickEvent() instanceof ClickEvent.ChangePage(int page) ? new Click.SwitchPage(page) :
                                                                 input.getStyle().getClickEvent() instanceof ClickEvent.RunCommand(String cmd) ? new Click.ChatCommand(cmd) :
                                                                 input.getStyle().getClickEvent() instanceof ClickEvent.SuggestCommand(String msg) ? new Click.ChatSuggest(msg) :
                                                                 input.getStyle().getClickEvent() instanceof ClickEvent.ShowDialog(Holder<Dialog> dialog) ? new Click.OpenDialog(dialog) :
                                                                 input.getStyle().getClickEvent() instanceof ClickEvent.Custom(Identifier id, Optional<Tag> payload) ? new Click.Misc(IdentificationNative.convertFrom(id), payload.map(NbtNative::convertFrom).orElse(null)) :
                                                                 null);

        if (input.getStyle().getHoverEvent() != null) comp.hover(input.getStyle().getHoverEvent() instanceof HoverEvent.ShowText(Component text) ? new Hover.ViewText(from(text)) :
                                                                 input.getStyle().getHoverEvent() instanceof HoverEvent.ShowEntity(HoverEvent.EntityTooltipInfo info) ? new Hover.ViewEntity(new Hover.EntityTooltip(EntityTypeNative.convertFrom(info.type), info.uuid, info.name.map(COFromNative::from).orElse(null))) :
                                                                 input.getStyle().getHoverEvent() instanceof HoverEvent.ShowItem(ItemStack stack) ? new Hover.ViewItem(ItemStackNative.convertFrom(stack)) :
                                                                 null);

        for (var ch : input.getSiblings())
            comp.append(from(ch));

        return comp;
    }
}
