package pl.olafcio.avoid.net.chat.component.type;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.chat.component.BaseComponent;

import java.util.ArrayList;
import java.util.List;

public class ParentComponent extends BaseComponent<ParentComponent> {
    final List<BaseComponent<?>> children;

    protected ParentComponent(List<BaseComponent<?>> children) {
        this.children = children;
    }

    public List<BaseComponent<?>> getChildren() {
        return children;
    }

    @ApiStatus.Internal
    public static ParentComponent of(BaseComponent<?> child) {
        return new ParentComponent(new ArrayList<>() {{
            add(child);
        }});
    }

    public ParentComponent append(BaseComponent<?> comp) {
        children.addLast(comp);
        return this;
    }

    public ParentComponent prepend(BaseComponent<?> comp) {
        children.addFirst(comp);
        return this;
    }

    public ParentComponent insert(int index, BaseComponent<?> comp) {
        children.add(index, comp);
        return this;
    }
}
