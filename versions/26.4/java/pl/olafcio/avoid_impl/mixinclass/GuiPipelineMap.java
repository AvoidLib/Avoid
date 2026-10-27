package pl.olafcio.avoid_impl.mixinclass;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiPipeline;

import java.util.HashMap;

public final class GuiPipelineMap {
    private GuiPipelineMap() {}

    public static final HashMap<RenderPipeline, GuiPipeline> MAP
                  = new HashMap<>();
}
