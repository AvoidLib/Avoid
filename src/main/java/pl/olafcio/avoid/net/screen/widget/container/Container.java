package pl.olafcio.avoid.net.screen.widget.container;

import pl.olafcio.avoid.net.screen.Drawer;
import pl.olafcio.avoid.net.screen.widget.able.Renderable;

import java.util.ArrayList;

public abstract class Container implements Renderable, ParentElement {
    @Override
    public void render(Drawer ctx, int mouseX, int mouseY, float tickDelta) {
        var kids = children();

        for (var el : kids)
            el.render(ctx, mouseX, mouseY, tickDelta);
    }

    private final ArrayList<Renderable> children
            = new ArrayList<>();

    @Override
    public Iterable<Renderable> children() {
        return children;
    }

    @Override
    public Renderable child(int index) {
        return children.get(index);
    }

    @Override
    public void append(Renderable widget) {
        children.add(widget);
    }

    @Override
    public void prepend(Renderable widget) {
        children.addFirst(widget);
    }

    public void insert(Renderable widget, int index) {
        children.add(index, widget);
    }

    @Override
    public void removeChild(Renderable widget) {
        children.remove(widget);
    }
}
