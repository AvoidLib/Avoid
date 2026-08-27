package pl.olafcio.avoid.net.chat.converter_kyori;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.dialog.Dialog;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.KeybindComponent;
import net.kyori.adventure.text.ObjectComponent;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.object.PlayerHeadObjectContents;
import net.kyori.adventure.text.object.SpriteObjectContents;
import net.kyori.adventure.text.serializer.gson.GsonDataComponentValue;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.component.event.Click;
import pl.olafcio.avoid.net.chat.component.event.Hover;
import pl.olafcio.avoid.net.chat.component.type.*;
import pl.olafcio.avoid.net.chat.component.type.TextComponent;
import pl.olafcio.avoid.net.entity_type.EntityType;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.item.Item;
import pl.olafcio.avoid.net.item.component.map.ItemComponentMap;
import pl.olafcio.avoid.net.item.stack.ItemStack;
import pl.olafcio.avoid.net.nbt.NbtNative;

import java.awt.Color;
import java.net.URI;

@NullMarked
@ApiStatus.Internal
public final class COFromNative {
    @ApiStatus.Internal
    private COFromNative() {}

    public static BaseComponent<?> from(Component input) {
        BaseComponent<?> comp;

        if (input instanceof net.kyori.adventure.text.TextComponent content)
            comp = TextComponent.of(content.content());
        else if (input instanceof TranslatableComponent content)
            comp = TranslateComponent.of(content.key(), content.fallback());
        else if (input instanceof KeybindComponent content)
            comp = KeymapComponent.of(content.keybind());
        else if (input instanceof ObjectComponent content)
            if (content.contents() instanceof PlayerHeadObjectContents sprite)
                comp = HeadComponent.of(sprite.id(), sprite.hat());
            else if (content.contents() instanceof SpriteObjectContents sprite)
                comp = AtlasComponent.of(
                        convert(sprite.atlas()),
                        convert(sprite.sprite())
                );
            else
                throw new RuntimeException("Unknown minecraft object component type '" + content.contents() + "'");
        else throw new RuntimeException("Unknown minecraft component type '" + input + "'");

        if (input.style().color()       != null) comp.color (new Color(input.style().color().value()));
        if (input.style().shadowColor() != null) comp.shadow(new Color(input.style().shadowColor().value()));

        if (input.style().decoration(TextDecoration.BOLD)          != TextDecoration.State.NOT_SET) comp.bold(input.style().hasDecoration(TextDecoration.BOLD));
        if (input.style().decoration(TextDecoration.ITALIC)        != TextDecoration.State.NOT_SET) comp.bold(input.style().hasDecoration(TextDecoration.ITALIC));
        if (input.style().decoration(TextDecoration.UNDERLINED)    != TextDecoration.State.NOT_SET) comp.bold(input.style().hasDecoration(TextDecoration.UNDERLINED));
        if (input.style().decoration(TextDecoration.STRIKETHROUGH) != TextDecoration.State.NOT_SET) comp.bold(input.style().hasDecoration(TextDecoration.STRIKETHROUGH));
        if (input.style().decoration(TextDecoration.OBFUSCATED)    != TextDecoration.State.NOT_SET) comp.bold(input.style().hasDecoration(TextDecoration.OBFUSCATED));

        if (input.style().font() != null) comp.font(new Identification(input.style().font().namespace(), input.style().font().value()));

        try {
            if (input.style().clickEvent() != null) comp.click(input.style().clickEvent().action() == ClickEvent.Action.OPEN_FILE ? new Click.StartFile(((ClickEvent.Payload.Text) input.style().clickEvent().payload()).value()) :
                                                               input.style().clickEvent().action() == ClickEvent.Action.OPEN_URL  ? new Click.StartURL(URI.create(((ClickEvent.Payload.Text) input.style().clickEvent().payload()).value())) :
                                                               input.style().clickEvent().action() == ClickEvent.Action.COPY_TO_CLIPBOARD ? new Click.Copy(((ClickEvent.Payload.Text) input.style().clickEvent().payload()).value()) :
                                                               input.style().clickEvent().action() == ClickEvent.Action.CHANGE_PAGE ? new Click.SwitchPage(((ClickEvent.Payload.Int) input.style().clickEvent().payload()).integer()) :
                                                               input.style().clickEvent().action() == ClickEvent.Action.RUN_COMMAND ? new Click.ChatCommand(((ClickEvent.Payload.Text) input.style().clickEvent().payload()).value()) :
                                                               input.style().clickEvent().action() == ClickEvent.Action.SUGGEST_COMMAND ? new Click.ChatSuggest(((ClickEvent.Payload.Text) input.style().clickEvent().payload()).value()) :
                                                               input.style().clickEvent().action() == ClickEvent.Action.SHOW_DIALOG ? new Click.OpenDialog(convert(((Dialog) (((ClickEvent.Payload.Dialog) input.style().clickEvent().payload()).dialog())).key())) :
                                                               input.style().clickEvent().action() == ClickEvent.Action.CUSTOM ? new Click.Misc(convert(((ClickEvent.Payload.Custom) input.style().clickEvent().payload()).key()), NbtNative.convertFrom(NbtUtils.snbtToStructure(((ClickEvent.Payload.Custom) input.style().clickEvent().payload()).nbt().string()))) :
                                                               null);
        } catch (CommandSyntaxException e) {
            throw new RuntimeException("AvoidLib failed to unserialize chat component", e);
        }

        if (input.style().hoverEvent() != null) comp.hover(input.style().hoverEvent().action() == HoverEvent.Action.SHOW_TEXT ? new Hover.ViewText(from((Component) input.style().hoverEvent().value())) :
                                                           input.style().hoverEvent().value() instanceof HoverEvent.ShowEntity info ? new Hover.ViewEntity(new Hover.EntityTooltip(EntityType.of(convert(info.type())), info.id(), info.name() == null ? null : from(info.name()))) :
                                                           input.style().hoverEvent().value() instanceof HoverEvent.ShowItem info ? new Hover.ViewItem(convert(info)) :
                                                           null);

        for (var ch : input.children())
            comp.append(from(ch));

        return comp;
    }

    private static ItemStack convert(HoverEvent.ShowItem info) {
        var map = DataComponentMap.builder();

        for (var entry : info.dataComponents().entrySet()) {
            try {
                var key = BuiltInRegistries.DATA_COMPONENT_TYPE.get(convert2(entry.getKey())).orElseThrow().value();

                if (entry.getValue() instanceof BinaryTagHolder tag)
                    map.set((DataComponentType<? super Tag>) key, NbtUtils.snbtToStructure(tag.string()));
                else if (entry.getValue() instanceof GsonDataComponentValue obj)
                    map.set((DataComponentType<? super Tag>) key, NbtNative.convertJSON(obj.element()));
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        return new ItemStack(Item.of(convert(info.item())), info.count(), new ItemComponentMap(map.build()));
    }

    private static Identification convert(Key key) {
        return new Identification(key.namespace(), key.value());
    }

    private static Identifier convert2(Key key) {
        return Identifier.fromNamespaceAndPath(key.namespace(), key.value());
    }
}
