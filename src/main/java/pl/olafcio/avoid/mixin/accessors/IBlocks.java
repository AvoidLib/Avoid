package pl.olafcio.avoid.mixin.accessors;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Blocks.class)
public interface IBlocks {
    @SuppressWarnings("DataFlowIssue")
    @Invoker("netherStemProperties")
    @NotNull
    static BlockBehaviour.Properties netherStemProperties(MapColor mapColor) {
        return null;
    }

    @SuppressWarnings("DataFlowIssue")
    @Invoker("shulkerBoxProperties")
    @NotNull
    static BlockBehaviour.Properties shulkerBoxProperties(MapColor mapColor) {
        return null;
    }

    @SuppressWarnings("DataFlowIssue")
    @Invoker("candleProperties")
    @NotNull
    static BlockBehaviour.Properties candleProperties(MapColor mapColor) {
        return null;
    }

    @SuppressWarnings("DataFlowIssue")
    @Invoker("pistonProperties")
    @NotNull
    static BlockBehaviour.Properties pistonProperties() {
        return null;
    }

    @SuppressWarnings("DataFlowIssue")
    @Invoker("buttonProperties")
    @NotNull
    static BlockBehaviour.Properties buttonProperties() {
        return null;
    }

    @SuppressWarnings("DataFlowIssue")
    @Invoker("flowerPotProperties")
    @NotNull
    static BlockBehaviour.Properties flowerPotProperties() {
        return null;
    }
}
