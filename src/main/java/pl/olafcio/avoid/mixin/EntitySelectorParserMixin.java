package pl.olafcio.avoid.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.olafcio.avoid.net.chat.converter.COToNative;
import pl.olafcio.avoid.net.entity_selector.EntitySelectorMeta;
import pl.olafcio.avoid.net.entity_selector.EntitySelectors;
import pl.olafcio.avoid.net.entity_selector.properties.SelectorOrder;
import pl.olafcio.avoid.net.entity_selector.properties.SelectorTarget;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Mixin(EntitySelectorParser.class)
public abstract class EntitySelectorParserMixin {
    @Shadow @Final private List<Predicate<Entity>> predicates;

    @Shadow private BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> suggestions;
    @Shadow protected abstract CompletableFuture<Suggestions> suggestOpenOptions(SuggestionsBuilder suggestionsBuilder, Consumer<SuggestionsBuilder> consumer);

    @Shadow @Final private StringReader reader;

    @Shadow protected abstract void parseOptions() throws CommandSyntaxException;
    @Shadow protected abstract CompletableFuture<Suggestions> suggestOptionsKeyOrClose(SuggestionsBuilder suggestionsBuilder, Consumer<SuggestionsBuilder> consumer);

    @Shadow private int maxResults;
    @Shadow private boolean includesEntities;
    @Shadow private BiConsumer<Vec3, List<? extends Entity>> order;

    @Shadow @Final public static BiConsumer<Vec3, List<? extends Entity>> ORDER_NEAREST;
    @Shadow @Final public static BiConsumer<Vec3, List<? extends Entity>> ORDER_FURTHEST;
    @Shadow @Final public static BiConsumer<Vec3, List<? extends Entity>> ORDER_RANDOM;

    @Shadow
    public abstract void limitToType(EntityType<?> entityType);

    @WrapOperation(
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/brigadier/StringReader;read()C",
                    ordinal = 0
            ),
            method = "parseSelector",
            order = 998
    )
    protected char parseSelector(StringReader instance, Operation<Character> original)
              throws CommandSyntaxException
    {
        this.skip = false;

        var ch = original.call(instance);
        var map = EntitySelectors.getAll();

        for (Map.Entry<Character, EntitySelectorMeta> sel : map.entrySet()) {
            if (ch == sel.getKey()) {
                use(sel.getValue());
                post(sel.getValue());

                this.skip = true;
            }
        }

        return ch;
    }

    @Unique
    private boolean skip;

    @Inject(
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/brigadier/StringReader;read()C",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            ),
            method = "parseSelector",
            order = 999,
            cancellable = true
    )
    protected void parseSelector(CallbackInfo ci) {
        if (skip)
            ci.cancel();
    }

    @Unique
    private void use(EntitySelectorMeta meta) {
        this.maxResults = meta.maxResults();
        this.includesEntities = meta.target() != SelectorTarget.Enum.PLAYERS;
        this.order = meta.order() == SelectorOrder.Enum.NEAREST  ? ORDER_NEAREST  :
                     meta.order() == SelectorOrder.Enum.FURTHEST ? ORDER_FURTHEST :
                                                                   ORDER_RANDOM   ;

        if (meta.target() == SelectorTarget.Enum.PLAYERS)
            this.limitToType(EntityType.PLAYER);
    }

    @Unique
    private void post(EntitySelectorMeta meta)
            throws CommandSyntaxException
    {
        if (!meta.self() && meta.target() != SelectorTarget.Enum.PLAYERS) {
            this.predicates.add(Entity::isAlive);
        }

        this.suggestions = this::suggestOpenOptions;

        if (this.reader.canRead() && this.reader.peek() == '[') {
            this.reader.skip();
            this.suggestions = this::suggestOptionsKeyOrClose;
            this.parseOptions();
        }
    }

    @Inject(at = @At("TAIL"), method = "fillSelectorSuggestions")
    private static void fillSelectorSuggestions(SuggestionsBuilder suggestionsBuilder, CallbackInfo ci) {
        var map = EntitySelectors.getAll();

        for (Map.Entry<Character, EntitySelectorMeta> meta : map.entrySet())
            suggestionsBuilder.suggest("@" + meta.getKey(), COToNative.from(meta.getValue().selectorClass().getTranslation()));
    }
}
