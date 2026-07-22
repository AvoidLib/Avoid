package pl.olafcio.avoid.net.block.properties.preset;

import org.intellij.lang.annotations.MagicConstant;

import java.lang.annotation.*;

/**
 * Applies the Nether Stem vanilla property preset.<br/>
 * Any direct property setting has priority over it.
 * <br/><br/>
 * <b>📐 What it sets (in MC 1.21.11):</b><br/>
 * <ul>
 *     <li>{@code .mapColor(mapColor)}</li>
 *     <li>{@code .instrument(NoteBlockInstrument.BASS)}</li>
 *     <li>{@code .strength(2.0F)}</li>
 *     <li>{@code .sound(SoundType.STEM)} <i><u>(internal, currently unachievable otherwise)</u></i></li>
 *     <li>{@code .noOcclusion()}</li>
 * </ul>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _presetNetherStem {
    /**
     * The name of an Avoid map color.<br/>
     * Must be {@code ""} if the {@code mapColorRGB} property is set.
     */
    @MagicConstant(stringValues = {
            "NONE",
            "GRASS",
            "SAND",
            "WOOL",
            "FIRE",
            "ICE",
            "METAL",
            "PLANT",
            "SNOW",
            "CLAY",
            "DIRT",
            "STONE",
            "WATER",
            "WOOD",
            "QUARTZ",
            "COLOR_ORANGE",
            "COLOR_MAGENTA",
            "COLOR_LIGHT_BLUE",
            "COLOR_YELLOW",
            "COLOR_LIGHT_GREEN",
            "COLOR_PINK",
            "COLOR_GRAY",
            "COLOR_LIGHT_GRAY",
            "COLOR_CYAN",
            "COLOR_PURPLE",
            "COLOR_BLUE",
            "COLOR_BROWN",
            "COLOR_GREEN",
            "COLOR_RED",
            "COLOR_BLACK",
            "GOLD",
            "DIAMOND",
            "LAPIS",
            "EMERALD",
            "PODZOL",
            "NETHER",
            "TERRACOTTA_WHITE",
            "TERRACOTTA_ORANGE",
            "TERRACOTTA_MAGENTA",
            "TERRACOTTA_LIGHT_BLUE",
            "TERRACOTTA_YELLOW",
            "TERRACOTTA_LIGHT_GREEN",
            "TERRACOTTA_PINK",
            "TERRACOTTA_GRAY",
            "TERRACOTTA_LIGHT_GRAY",
            "TERRACOTTA_CYAN",
            "TERRACOTTA_PURPLE",
            "TERRACOTTA_BLUE",
            "TERRACOTTA_BROWN",
            "TERRACOTTA_GREEN",
            "TERRACOTTA_RED",
            "TERRACOTTA_BLACK",
            "CRIMSON_NYLIUM",
            "CRIMSON_STEM",
            "CRIMSON_HYPHAE",
            "WARPED_NYLIUM",
            "WARPED_STEM",
            "WARPED_HYPHAE",
            "WARPED_WART_BLOCK",
            "DEEPSLATE",
            "RAW_IRON",
            "GLOW_LICHEN"
    }) String mapColor() default "";

    /**
     * The RGB of the map color.<br/>
     * Must be {@code 0} if the {@code mapColor} property is set.
     */
    int mapColorRGB() default 0;
}
