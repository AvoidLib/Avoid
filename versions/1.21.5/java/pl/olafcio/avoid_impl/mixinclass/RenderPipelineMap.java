package pl.olafcio.avoid_impl.mixinclass;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.renderer.RenderType;

import java.util.HashMap;

public final class RenderPipelineMap {
    private RenderPipelineMap() {}

    public static final HashMap<RenderPipeline, RenderType> MAP
                  = new HashMap<>();
}
