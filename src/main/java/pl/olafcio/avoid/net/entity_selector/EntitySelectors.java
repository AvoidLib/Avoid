package pl.olafcio.avoid.net.entity_selector;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.entity_selector.properties.SelectorOrder;
import pl.olafcio.avoid.net.entity_selector.properties.SelectorResults;
import pl.olafcio.avoid.net.entity_selector.properties.SelectorSelf;
import pl.olafcio.avoid.net.entity_selector.properties.SelectorTarget;

import java.util.HashMap;
import java.util.function.Supplier;

@ApiStatus.Experimental
public final class EntitySelectors {
    @ApiStatus.Internal
    private EntitySelectors() {}

    private static final HashMap<Character, EntitySelectorMeta> SELECTOR_TYPES
                   = new HashMap<>();

    public static void register(char ch, Supplier<? extends EntitySelector> constructor) {
        var instance = constructor.get();
        var type = instance.getClass();

        var self = type.isAnnotationPresent(SelectorSelf.class);
        var maxResults = type.getDeclaredAnnotation(SelectorResults.class)
                             .value();

        var order = type.getDeclaredAnnotation(SelectorOrder.class)
                        .value();
        var target = type.getDeclaredAnnotation(SelectorTarget.class)
                         .value();

        SELECTOR_TYPES.put(ch, new EntitySelectorMeta(self, maxResults,
                                                order, target,
                                                type));
    }

    public static HashMap<Character, EntitySelectorMeta> getAll() {
        return SELECTOR_TYPES;
    }
}
