package pl.olafcio.avoid.net.screen.renderlayer;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiPipeline;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class RenderLayerNative {
    @ApiStatus.Internal
    private RenderLayerNative() {}

    public static GuiPipeline convert(RenderLayer layer) {
        return (GuiPipeline) layer.getGui();
    }
}
