package pl.olafcio.avoid.net.block;

import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid_impl.mods.loader.AvoidPackageOnly;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.world.block_data.BlockData;

import java.lang.reflect.AnnotatedElement;
import java.util.function.Supplier;

/**
 * A namespace used to manage blocks.
 * <br/><br/><hr/><br/>
 * To create an instance of a blockstate, see {@link #create(Identification) create([Identification] id)}.<br/>
 * To register a new block type, see: {@link #register(Identification, Supplier) register([Identification] id, [Supplier<? extends Block>] constructor)}
 */
@ApiStatus.Experimental
public final class Blocks {
    @ApiStatus.Internal
    private Blocks() {}

    /**
     * Creates a default instance of the given block ID.
     */
    public static BlockData create(Identification id) {
        return pl.olafcio.avoid_impl.net.block.Blocks.create(id);
    }

    /**
     * Registers a new block type from the specified parameters.
     * @param blockID The block ID of the new block type.
     * @param constructor Should return a new instance of the class of the block type.<br/>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp;&emsp;
     *                    <u><i>The annotations on it will be considered.</i></u>
     */
    public static void register(Identification blockID, Supplier<? extends pl.olafcio.avoid.net.block.Block> constructor) {
        pl.olafcio.avoid_impl.net.block.Blocks.register(blockID, constructor, constructor.get().getClass());
    }

    /**
     * Registers a new dynamic block type from the specified parameters.
     * @param blockID The block ID of the new block type.
     * @param constructor Should return a new instance of the class of the block type.<br/>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp;&emsp;
     *                    Annotations on it will not be considered.
     * @param config {@linkplain BlockBuilder Annotation configuration} for the block.<br/>&emsp;&emsp;&emsp;&emsp;&nbsp;&ensp;
     *               Useful when you don't have a class with all the annotations needed.
     */
    public static void register(Identification blockID, Supplier<? extends pl.olafcio.avoid.net.block.Block> constructor, AnnotatedElement config) {
        pl.olafcio.avoid_impl.net.block.Blocks.register(blockID, constructor, config);
    }

    @ApiStatus.Internal
    public static void register(Identification blockID, Supplier<? extends pl.olafcio.avoid.net.block.Block> constructor, AvoidPackageOnly<Block> interceptor) {
        pl.olafcio.avoid_impl.net.block.Blocks.register(blockID, constructor, interceptor);
    }
}
