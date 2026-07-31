package pl.olafcio.avoid.net.gui.event;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.fog.delta.TickTracker;
import pl.olafcio.avoid.net.screen.Drawer;

@ApiStatus.Experimental
public record RenderHudEvent(Drawer drawer, TickTracker tickTracker) {}
