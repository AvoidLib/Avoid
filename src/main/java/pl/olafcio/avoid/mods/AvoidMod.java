package pl.olafcio.avoid.mods;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;

@NeverRemoval
public abstract class AvoidMod {
    @ApiStatus.Experimental
    public void onLoad() {}

    @ApiStatus.Experimental
    public void onEnable() {}

    @ApiStatus.Experimental
    public void onDisable() {}
}
