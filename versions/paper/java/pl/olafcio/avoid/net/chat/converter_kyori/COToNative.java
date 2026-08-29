package pl.olafcio.avoid.net.chat.converter_kyori;

import io.papermc.paper.dialog.Dialog;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.object.ObjectContents;
import net.kyori.adventure.util.ARGBLike;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
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

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public final class COToNative {
    @ApiStatus.Internal
    private COToNative() {}

    public static Component from(BaseComponent<?> input) {
        Component comp;

        switch (input) {
            case TextComponent cast -> comp = Component.text(cast.text());
            case KeymapComponent cast -> comp = Component.keybind(cast.keybind());
            case TranslateComponent cast -> {
                if (cast.fallback() == null)
                    comp = Component.translatable(cast.translate());
                else
                    comp = Component.translatable(cast.translate(), cast.fallback());
            }
            case TranslateFormattedComponent cast -> {
                Component[] array = Arrays.stream(cast.format())
                                          .map(COToNative::from)
                                          .toArray(Component[]::new);

                if (cast.fallback() == null)
                    comp = Component.translatable(cast.translate(), array);
                else
                    comp = Component.translatable(cast.translate(), cast.fallback(), array);
            }
            case ParentComponent cast -> {
                comp = Component.empty();

                for (var ch : cast.getChildren())
                    comp = comp.append(from(ch));
            }
            case HeadComponent cast -> {
                comp = Component.object(ObjectContents.playerHead().id(cast.uuid()).hat(cast.withHat()).build());
            }
            case AtlasComponent cast -> {
                comp = Component.object(ObjectContents.sprite(
                        Key.key(cast.atlas().namespace(), cast.atlas().path()),
                        Key.key(cast.sprite().namespace(), cast.sprite().path())
                ));
            }

            default -> throw new RuntimeException("Unknown Avoid component type '" + input + "'");
        }

        Style.Builder dest = Style.style();
        ChatStyle src = input.style();

        if (src.color != null) dest.color(TextColor.color(src.color.getRGB()));
        if (src.shadow != null) dest = dest.shadowColor(new ARGBLike() {
            @Override
            public @Range(from = 0L, to = 255L) int red() {
                return src.shadow.getRed();
            }

            @Override
            public @Range(from = 0L, to = 255L) int green() {
                return src.shadow.getGreen();
            }

            @Override
            public @Range(from = 0L, to = 255L) int blue() {
                return src.shadow.getBlue();
            }

            @Override
            public @Range(from = 0L, to = 255L) int alpha() {
                return src.shadow.getAlpha();
            }
        });

        if (src.bold != null) dest.decoration(TextDecoration.BOLD, src.bold);
        if (src.italic != null) dest.decoration(TextDecoration.ITALIC, src.italic);
        if (src.underlined != null) dest.decoration(TextDecoration.UNDERLINED, src.underlined);
        if (src.strikethrough != null) dest.decoration(TextDecoration.STRIKETHROUGH, src.strikethrough);
        if (src.obfuscated != null) dest.decoration(TextDecoration.OBFUSCATED, src.obfuscated);

        if (src.font != null) dest.font(Key.key(src.font.namespace(), src.font.path()));

        if (src.click != null) {
            try {
                dest.clickEvent(src.click instanceof Click.ChatCommand(String cmd) ? ClickEvent.runCommand(cmd) :
                                                       src.click instanceof Click.ChatSuggest(String cmd) ? ClickEvent.suggestCommand(cmd) :
                                                       src.click instanceof Click.Copy(String text)       ? ClickEvent.copyToClipboard(text) :
                                                       src.click instanceof Click.StartFile(String path)  ? ClickEvent.openFile(path) :
                                                       src.click instanceof Click.StartURL(URI url)       ? ClickEvent.openUrl(url.toURL()) :
                                                       src.click instanceof Click.SwitchPage(int page)    ? ClickEvent.changePage(page) :
                                                       src.click instanceof Click.OpenDialog(Identification dialog) ? ClickEvent.showDialog(Dialog.create(f -> {})) :
                                                       src.click instanceof Click.Misc(Identification id, @Nullable NbtElement payload) ? ClickEvent.custom(Key.key(id.namespace(), id.path()), payload == null ? "{}" : NbtNative.convert(payload).toString()) :
                                                       null);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Failed to create URL from Click.StartURL", e);
            }
        }

        if (src.hover != null) dest.hoverEvent(src.hover instanceof Hover.ViewEntity(Hover.EntityTooltip entity) ? HoverEvent.showEntity(Key.key(entity.type.getID().namespace(), entity.type.getID().path()), entity.uuid, entity.name == null ? null : COToNative.from(entity.name)) :
                                               src.hover instanceof Hover.ViewItem(ItemStack item) ? HoverEvent.showItem(Key.key(item.getItem().getID().namespace(), item.getItem().getID().path()), item.getAmount()) :
                                               src.hover instanceof Hover.ViewText(BaseComponent<?> hover) ? HoverEvent.showText(COToNative.from(hover)) :
                                               null);

        comp = comp.style(dest);

        return comp;
    }
}
