package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import pl.olafcio.avoid.mixinclass.AvoidClientSuggestion;
import pl.olafcio.avoid.mixininterface.IClientSuggestion;
import pl.olafcio.avoid.mods.event.EventManager;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.chat.converter.COToNative;
import pl.olafcio.avoid.net.command_client.event.ClientCommandSuggestEvent;
import pl.olafcio.avoid.net.command_client.suggestion.ClientSuggestion;
import pl.olafcio.avoid_common.Either;

import java.util.Arrays;
import java.util.List;

@Mixin(CommandSuggestions.class)
public class CommandSuggestionsMixin {
    @Shadow
    @Final
    EditBox input;

    @WrapOperation(at = @At(value = "NEW", target = "(Lnet/minecraft/client/gui/components/CommandSuggestions;IIILjava/util/List;Z)Lnet/minecraft/client/gui/components/CommandSuggestions$SuggestionsList;"), method = "showSuggestions")
    public CommandSuggestions.SuggestionsList showSuggestions__new__SuggestionList(CommandSuggestions commandSuggestions, int i, int j, int k, List<Suggestion> list, boolean bl, Operation<CommandSuggestions.SuggestionsList> original) {
        var event = new ClientCommandSuggestEvent(input.getValue(), Arrays.asList(list.stream().map(sg -> new ClientSuggestion(
                sg.getRange().getStart(),
                sg.getRange().getEnd(),
                sg.getText(),
                sg.getTooltip() == null                  ? null :
                sg.getTooltip() instanceof Component cmp ? Either.left(COFromNative.from(cmp)) :
                                                           Either.right(sg.getTooltip().getString())
        )).toArray(ClientSuggestion[]::new)));

        EventManager.fire(event);

        return original.call(commandSuggestions, i, j, k, event.getSuggestions().stream().map(sg -> new AvoidClientSuggestion(
                new StringRange(sg.rangeStart(), sg.rangeEnd()),

                sg.text(),
                sg.tooltip() == null                                ? null                 :
                sg.tooltip().left() instanceof BaseComponent<?> cmp ? COToNative.from(cmp) :
                                                                      new LiteralMessage(sg.tooltip().right()),

                sg.textColor(),
                sg.textActiveColor(),
                sg.backgroundColor()
        )).toList(), bl);
    }

    @SuppressWarnings("unused")
    @Mixin(CommandSuggestions.SuggestionsList.class)
    public static class SuggestionsListMixin {
        @WrapOperation(at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/components/CommandSuggestions;fillColor:I", ordinal = 2, opcode = Opcodes.GETFIELD), method = "render")
        public int render__backgroundColor(CommandSuggestions instance, Operation<Integer> original, @Local Suggestion suggestion) {
            if (suggestion instanceof IClientSuggestion ics) {
                var val = ics.avoid$backgroundColor();
                if (val != null)
                    return val;
            }

            return original.call(instance);
        }

        @Unique private Suggestion suggestion;
        @Unique private int n;

        @WrapOperation(at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;", ordinal = 0), method = "render")
        public Object render__store__suggestion(List<Suggestion> instance, int i, Operation<Object> original) {
            this.n = i - this.offset;
            return suggestion = (Suggestion) original.call(instance, i);
        }

        @Shadow private int offset;
        @Shadow private int current;

        @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V", ordinal = 0), method = "render")
        public void render__drawString(GuiGraphics instance, Font font, String string, int i, int j, int k, Operation<Void> original) {
            if (suggestion instanceof IClientSuggestion ics) {
                Integer val;

                if (n + this.offset == this.current)
                    val = ics.avoid$textActiveColor();
                else
                    val = ics.avoid$textColor();

                if (val != null)
                    k = val;
            }

            original.call(instance, font, string, i, j, k);
        }
    }
}
