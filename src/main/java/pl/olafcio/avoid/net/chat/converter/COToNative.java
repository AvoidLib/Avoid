package pl.olafcio.avoid.net.chat.converter;

import net.minecraft.core.Holder;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.dialog.Dialog;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.component.ChatStyle;
import pl.olafcio.avoid.net.chat.component.event.Click;
import pl.olafcio.avoid.net.chat.component.event.Hover;
import pl.olafcio.avoid.net.chat.component.type.*;
import pl.olafcio.avoid.net.chat.component.type.TextComponent;
import pl.olafcio.avoid.net.entity_type.EntityTypeNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.item.stack.ItemStack;
import pl.olafcio.avoid.net.item.stack.ItemStackNative;
import pl.olafcio.avoid.net.nbt.NbtElement;
import pl.olafcio.avoid.net.nbt.NbtNative;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public final class COToNative {
    @ApiStatus.Internal
    private COToNative() {}

    public static Component from(BaseComponent<?> input) {
        MutableComponent comp;

        switch (input) {
            case TextComponent cast -> comp = Component.literal(cast.text());
            case KeymapComponent cast -> comp = Component.keybind(cast.keybind());
            case TranslateComponent cast -> {
                if (cast.fallback() == null)
                    comp = Component.translatable(cast.translate());
                else
                    comp = Component.translatableWithFallback(cast.translate(), cast.fallback());
            }
            case TranslateFormattedComponent cast -> {
                Object[] array = Arrays.stream(cast.format())
                                       .map(COToNative::from)
                                       .toArray(Component[]::new);

                if (cast.fallback() == null)
                    comp = Component.translatable(cast.translate(), array);
                else
                    comp = Component.translatableWithFallback(cast.translate(), cast.fallback(), array);
            }
            case ParentComponent cast -> {
                comp = Component.empty();

                for (var ch : cast.getChildren())
                    comp.append(from(ch));
            }

            default -> throw new RuntimeException("Unknown Avoid component type '" + input + "'");
        }

        Style dest = Style.EMPTY;
        ChatStyle src = input.style();

        if (src.color != null) dest = dest.withColor(src.color.getRGB());
        if (src.shadow != null) dest = dest.withShadowColor(src.shadow.getRGB());

        if (src.bold != null) dest = dest.withBold(src.bold);
        if (src.italic != null) dest = dest.withBold(src.italic);
        if (src.underlined != null) dest = dest.withBold(src.underlined);
        if (src.strikethrough != null) dest = dest.withBold(src.strikethrough);
        if (src.obfuscated != null) dest = dest.withBold(src.obfuscated);

        if (src.font != null) dest = dest.withFont(new FontDescription.Resource(IdentificationNative.convert(src.font)));

        if (src.click != null) dest = dest.withClickEvent(src.click instanceof Click.ChatCommand(String cmd) ? new ClickEvent.RunCommand(cmd) :
                                                          src.click instanceof Click.ChatSuggest(String cmd) ? new ClickEvent.SuggestCommand(cmd) :
                                                          src.click instanceof Click.Copy(String text)       ? new ClickEvent.CopyToClipboard(text) :
                                                          src.click instanceof Click.StartFile(String path)  ? new ClickEvent.OpenFile(path) :
                                                          src.click instanceof Click.StartURL(URI url)       ? new ClickEvent.OpenUrl(url) :
                                                          src.click instanceof Click.SwitchPage(int page)    ? new ClickEvent.ChangePage(page) :
                                                          src.click instanceof Click.OpenDialog(Holder<Dialog> dialog) ? new ClickEvent.ShowDialog(dialog) :
                                                          src.click instanceof Click.Misc(Identification id, @Nullable NbtElement payload) ? new ClickEvent.Custom(IdentificationNative.convert(id), payload == null ? Optional.empty() : Optional.of(NbtNative.convert(payload))) :
                                                          null);

        if (src.hover != null) dest = dest.withHoverEvent(src.hover instanceof Hover.ViewEntity(Hover.EntityTooltip entity) ? new HoverEvent.ShowEntity(new HoverEvent.EntityTooltipInfo(EntityTypeNative.convert(entity.type), entity.uuid, entity.name == null ? null : COToNative.from(entity.name))) :
                                                          src.hover instanceof Hover.ViewItem(ItemStack item) ? new HoverEvent.ShowItem(ItemStackNative.convert(item)) :
                                                          src.hover instanceof Hover.ViewText(BaseComponent<?> hover) ? new HoverEvent.ShowText(COToNative.from(hover)) :
                                                          null);

        comp.setStyle(dest);

        return comp;
    }
}
