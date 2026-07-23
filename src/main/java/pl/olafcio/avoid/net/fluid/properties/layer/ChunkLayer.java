package pl.olafcio.avoid.net.fluid.properties.layer;

import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

public enum ChunkLayer {
    SOLID(ChunkSectionLayer.SOLID),
    CUTOUT(ChunkSectionLayer.CUTOUT),
    TRANSLUCENT(ChunkSectionLayer.TRANSLUCENT),
    TRIPWIRE(ChunkSectionLayer.TRIPWIRE);

    final ChunkSectionLayer object;

    ChunkLayer(ChunkSectionLayer object) {
        this.object = object;
    }
}
