package pl.olafcio.avoid.net.entity_selector;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.chat.component.BaseComponent;

@ApiStatus.Experimental
public abstract class EntitySelector {
    public abstract BaseComponent<?> getTranslation();
}
