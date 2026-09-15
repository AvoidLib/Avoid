package pl.olafcio.avoid.net.world;

import org.jetbrains.annotations.ApiStatus;

/**
 * A block highlight (outline around a block).
 * @param color The main color to use.
 * @param secondaryColor The <i>secondary color</i>. <br/><br/>
 *                       It is used in the secondary_block_outline world render layer,
 *                          by-default used only for high contrast mode.
 *                       <br/>
 * @param color_highcontrast The main color to use while in high contrast mode.
 *                           <br/><br/>
 *                           <b>IMPORTANT:</b> If set to {@code null}, <u>there will be no primary outline in high contrast mode!</u>
 *                           <br/>
 * @param secondaryColor_highcontrast The main color to use while in high contrast mode. <br/><br/>
 *                                    It is used in the secondary_block_outline world render layer,
 *                                       by-default used only for high contrast mode.
 *                                    <br/><br/>
 *                                    <b>IMPORTANT:</b> If set to {@code null}, <u>there will be no secondary outline in high contrast mode!</u>
 *                                    <br/>
 * @param lineWidth The width of each line drawn in the primary highlight. <br/><br/>
 *                  If set to {@code null}, the <i>default appropriate value</i> is used.<br/>
 *                  This is the appropriate value formula:<br/>
 *                  <pre>
 *                  {@code
 *                         return Math.max(2.5f, (float)this¹.getWidth() / 1920.0f * 2.5f);
 *                  }
 *                  </pre>
 *                  <h6 style="font-size: .95em">[1] this - the minecraft window object</h6>
 *                  <h6 style="font-size: .95em">[2] the formula may be different depending on the minecraft version!<br/>&ensp;&ensp;&ensp;(this is from 1.21.11)</h6>
 * @param lineWidth_secondary The width of each line drawn in the secondary highlight.
 */
@ApiStatus.Experimental
public record Highlight(
        Integer color,              Integer secondaryColor,
        Integer color_highcontrast, Integer secondaryColor_highcontrast,
        Float lineWidth,            float lineWidth_secondary
) {
    /**
     * Creates a new block highlight, using the default line width for the main color, and <b>7</b> (the standard secondary line width) for the secondary color.
     * @param color The main color to use.
     * @param secondaryColor The <i>secondary color</i>. <br/><br/>
     *                       It is used in the secondary_block_outline world render layer,
     *                          by-default used only for high contrast mode.
     *                       <br/>
     * @param color_highcontrast The main color to use while in high contrast mode.
     *                           <br/><br/>
     *                           <b>IMPORTANT:</b> If set to {@code null}, <u>there will be no primary outline in high contrast mode!</u>
     *                           <br/>
     * @param secondaryColor_highcontrast The main color to use while in high contrast mode. <br/><br/>
     *                                    It is used in the secondary_block_outline world render layer,
     *                                       by-default used only for high contrast mode.
     *                                    <br/><br/>
     *                                    <b>IMPORTANT:</b> If set to {@code null}, <u>there will be no secondary outline in high contrast mode!</u>
     */
    public Highlight(
            Integer color,              Integer secondaryColor,
            Integer color_highcontrast, Integer secondaryColor_highcontrast
    ) {
        this(color, secondaryColor, color_highcontrast, secondaryColor_highcontrast, null, 7f);
    }

    /**
     * Creates a new block highlight.<br/>
     * Both {@code color} as well as {@code secondaryColor} may be null.
     * @param color The main color to use.
     * @param secondaryColor The <i>secondary color</i>. <br/><br/>
     *                       It is used in the secondary_block_outline world render layer,
     *                          by-default used only for high contrast mode.
     *                       <br/><br>
     *                       <b>IMPORTANT:</b> This will get overriden by the default secondary color value if in
     *                                              high-contrast mode! <i>(to control that, use the full constructor)</i>
     */
    public Highlight(Integer color, Integer secondaryColor) {
        this(color, secondaryColor, color, 0x1000000);
    }

    /**
     * Creates a new block highlight, without a secondary color in non-high-contrast mode.<br/><br/>
     * <i>(in high contrast there will still be the default secondary color)</i>
     */
    public Highlight(Integer color) {
        this(color, null);
    }

    /**
     * Creates a new block highlight, without a secondary color in non-high-contrast mode.<br/><br/>
     * <i>(in high contrast there will still be the default secondary color)</i>
     * @param color The main color to use.
     * @param color_highcontrast The main color to use while in high contrast mode. <br/><br/>
     *                           It is used in the secondary_block_outline world render layer,
     *                              by-default used only for high contrast mode.
     *                           <br/><br/>
     *                           <b>IMPORTANT:</b> If set to {@code null}, <u>there will be no primary outline in high contrast mode!</u>
     */
    public static Highlight primary(Integer color, Integer color_highcontrast) {
        return new Highlight(color, null, color_highcontrast, 0x1000000);
    }

    /**
     * Creates a new block highlight, without primary colors.
     * @param secondaryColor The <i>secondary color</i>. <br/><br/>
     *                       It is used in the secondary_block_outline world render layer,
     *                          by-default used only for high contrast mode.
     *                       <br/>
     * @param secondaryColor_highcontrast The main color to use while in high contrast mode. <br/><br/>
     *                                    It is used in the secondary_block_outline world render layer,
     *                                       by-default used only for high contrast mode.
     *                                    <br/><br/>
     *                                    <b>IMPORTANT:</b> If set to {@code null}, <u>there will be no secondary outline in high contrast mode!</u>
     */
    public static Highlight secondary(Integer secondaryColor, Integer secondaryColor_highcontrast) {
        return new Highlight(null, secondaryColor, null, secondaryColor_highcontrast);
    }
}
