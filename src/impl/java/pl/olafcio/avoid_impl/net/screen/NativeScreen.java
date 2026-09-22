package pl.olafcio.avoid_impl.net.screen;

import com.google.common.base.CaseFormat;
import net.minecraft.client.gui.components.events.GuiEventListener;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.net.screen.*;
import pl.olafcio.avoid.net.screen.KeyboardEvent;
import pl.olafcio.avoid.net.screen.MouseEvent;
import pl.olafcio.avoid_impl.A4;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid_impl.mixininterface.IScreen;
import pl.olafcio.avoid_impl.net.chat.converter.COFromNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.screen.widget.able.*;
import pl.olafcio.avoid.net.screen.widget.container.ParentElement;
import pl.olafcio.avoid_impl.net.screen.widget_impl.AWFull;
import pl.olafcio.avoid_impl.net.screen.widget_impl.AWRenderOnly;
import pl.olafcio.avoid_impl.net.screen.widget_impl.IAvoidWidget;

import java.net.MalformedURLException;

@ApiStatus.Internal
public final class NativeScreen extends Screen implements ParentElement {
    public final net.minecraft.client.gui.screens.Screen realScreen;

    public NativeScreen(IScreen castScreen) {
        super(COFromNative.from(((net.minecraft.client.gui.screens.Screen) castScreen).getTitle()));

        this.realScreen = (net.minecraft.client.gui.screens.Screen) castScreen;
        this.realScreen.added();
    }

    @Override
    public void init() {
        realScreen.width = width;
        realScreen.height = height;

        A4.init(realScreen);
    }

    @Override
    protected void resize(int width, int height) {
        realScreen.resize(width, height);
    }

    @Override
    public void render(pl.olafcio.avoid.net.screen.Drawer gui, int mouseX, int mouseY, float tickDelta) {
        realScreen.render(DrawerNative.graphicsOf((pl.olafcio.avoid_impl.net.screen.Drawer) gui), mouseX, mouseY, tickDelta);
    }

    @Override
    public void mouseMoved(double x, double y) {
        realScreen.mouseMoved(x, y);
    }

    @Override
    public boolean mouseClicked(pl.olafcio.avoid.net.screen.MouseEvent event, boolean doubleClick) {
        return realScreen.mouseClicked(((pl.olafcio.avoid_impl.net.screen.MouseEvent) event).event, doubleClick);
    }

    @Override
    public boolean mouseReleased(pl.olafcio.avoid.net.screen.MouseEvent event) {
        return realScreen.mouseReleased(((pl.olafcio.avoid_impl.net.screen.MouseEvent) event).event);
    }

    @Override
    public boolean mouseScrolled(double x, double y, double scrolledX, double scrolledY) {
        return realScreen.mouseScrolled(x, y, scrolledX, scrolledY);
    }

    @Override
    public boolean mouseDragged(MouseEvent event, double x, double y) {
        return realScreen.mouseDragged(((pl.olafcio.avoid_impl.net.screen.MouseEvent) event).event, x, y);
    }

    @Override
    public boolean keyPressed(pl.olafcio.avoid.net.screen.KeyboardEvent event) {
        return realScreen.keyPressed(((pl.olafcio.avoid_impl.net.screen.KeyboardEvent) event).event);
    }

    @Override
    public boolean keyReleased(KeyboardEvent event) {
        return realScreen.keyReleased(((pl.olafcio.avoid_impl.net.screen.KeyboardEvent) event).event);
    }

    @Override
    protected boolean isMouseWithin(double x1, double y1, double x2, double y2) {
        return false;
    }

    @Override
    protected boolean isMouseOver(double x, double y) {
        return realScreen.isMouseOver(x, y);
    }

    @Override
    public void append(Renderable widget) {
        if (widget instanceof Focusable && widget instanceof Hoverable && widget instanceof Narratable)
            realScreen.addRenderableWidget(new AWFull<>((Renderable & Focusable & Hoverable & Narratable) widget));
        else if (widget instanceof Focusable)
            throw new NativeWidgetAppendException("A minecraft screen cannot have widgets that are focusable without being hoverable and narratable");
        else if (widget instanceof Hoverable)
            throw new NativeWidgetAppendException("A minecraft screen cannot have widgets that are hoverable without being focusable and narratable");
        else if (widget instanceof Narratable)
            throw new NativeWidgetAppendException("A minecraft screen cannot have widgets that are narratable without being focusable and hoverable");
        else
            realScreen.addRenderableOnly(new AWRenderOnly(widget));
    }

    @Override
    public void prepend(Renderable widget) {
        if (widget instanceof Focusable && widget instanceof Hoverable && widget instanceof Narratable) {
            var impl = new AWFull<>((Renderable & Focusable & Hoverable & Narratable) widget);

            realScreen.renderables.addFirst(impl);
            realScreen.narratables.addFirst(impl);
            realScreen.children.addFirst(impl);
        } else if (widget instanceof Focusable) {
            throw new NativeWidgetAppendException("A minecraft screen cannot have widgets that are focusable without being hoverable and narratable");
        } else if (widget instanceof Hoverable) {
            throw new NativeWidgetAppendException("A minecraft screen cannot have widgets that are hoverable without being focusable and narratable");
        } else if (widget instanceof Narratable) {
            throw new NativeWidgetAppendException("A minecraft screen cannot have widgets that are narratable without being focusable and hoverable");
        } else {
            realScreen.renderables.addFirst(new AWRenderOnly(widget));
        }
    }

    @Override
    public void insert(Renderable widget, int index) {
        throw new UnsupportedOperationException("Minecraft screens don't support #insert(Renderable, int)");
    }

    @Override
    public void removeChild(Renderable widget) {
        net.minecraft.client.gui.components.Renderable wrapper = null;

        //at:removechild
        for (var el : realScreen.renderables) {
            if (el instanceof IAvoidWidget avoid && avoid.getAvoid() == widget) {
                wrapper = (net.minecraft.client.gui.components.Renderable) avoid;
                break;
            }
        }

        if (wrapper == null)
            return;

        if (wrapper instanceof GuiEventListener gel)
            realScreen.removeWidget(gel);
        else
            realScreen.renderables.remove(wrapper);
        //atend:removechild
    }

    @Override
    public Iterable<Renderable> children() {
        return realScreen.renderables.stream().map(NativeScreen::convertRenderable).toList();
    }

    private static Renderable convertRenderable(net.minecraft.client.gui.components.Renderable el) {
        if (el instanceof IAvoidWidget avoid)
            return avoid.getAvoid();

        return new Renderable() {
            @Override
            public void render(pl.olafcio.avoid.net.screen.Drawer ctx, int mouseX, int mouseY, float deltaTick) {
                el.render(DrawerNative.graphicsOf((pl.olafcio.avoid_impl.net.screen.Drawer) ctx), mouseX, mouseY, deltaTick);
            }
        };
    }

    @Override
    @Nullable
    public Renderable child(int index) {
        net.minecraft.client.gui.components.Renderable el;

        try {
            el = realScreen.renderables.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

        return convertRenderable(el);
    }

    @Override
    @Nullable
    public Renderable widget(WidgetMarker marker) {
        return convertRenderable(((IScreen) realScreen).avoid$widget(marker));
    }

    @Override
    public boolean is(Identification id) {
        final var klass = realScreen.getClass();
        final var klassName = CaseFormat.UPPER_CAMEL.to(
                                                               CaseFormat.LOWER_UNDERSCORE,
                                                               klass.getName()
                                                                    .replaceAll("(\\..*)(Screen|(\\$.*)Screen)", "$1$3")
                                                                    .replace(".", "/")
                                                       );

        if (id.namespace().equals("minecraft")) {
            return isMinecraft(id, klass, klassName);
        }

        return isModded(id, klass, klassName);
    }

    private boolean isModded(Identification id, Class<?> klass, String klassName) {
        for (var addon : AvoidWrappedLoader.getModsPaths()) {
            try {
                if (addon.toUri().toURL().equals(klass.getProtectionDomain().getCodeSource().getLocation())) {
                    //FIXME
                    if (!addon.getFileName().toString().toLowerCase().contains(id.namespace().toLowerCase()))
                        return false;

                    if (!klassName.toLowerCase().endsWith(id.path().toLowerCase()))
                        return false;

                    return true;
                }
            } catch (MalformedURLException ignored) {
            }
        }

        return false;
    }

    private boolean isMinecraft(Identification id, Class<?> klass, String klassName) {
        try {
            var marker = ScreenMarker.valueOf(id.path().toUpperCase());
            return marker.is((IScreen) realScreen);
        } catch (IllegalArgumentException ignored) {
        }

        if (!klass.getName().startsWith("net.minecraft."))
            return false;

        if (!klassName.toLowerCase().endsWith(id.path().toLowerCase()))
            return false;

        return true;
    }

    @Override
    public int width() {
        return realScreen.width;
    }

    @Override
    public int height() {
        return realScreen.height;
    }
}
