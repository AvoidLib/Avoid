package pl.olafcio.avoid.net.entity_selector;

import pl.olafcio.avoid.net.entity_selector.properties.SelectorOrder;
import pl.olafcio.avoid.net.entity_selector.properties.SelectorTarget;

public record EntitySelectorMeta(
        boolean self,
        int maxResults,

        SelectorOrder.Enum order,
        SelectorTarget.Enum target,

        EntitySelector selectorClass
) {}
