package pl.olafcio.avoid.mixin;

import net.minecraft.client.renderer.state.BlockOutlineRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import pl.olafcio.avoid.mixininterface.IBlockOutlineRenderState;

@Mixin(BlockOutlineRenderState.class)
public class BlockOutlineRenderStateMixin implements IBlockOutlineRenderState {
    @Unique
    private Integer color,              secondaryColor,
                    color_highcontrast, secondaryColor_highcontrast;

    @Unique private Float linewidth;
    @Unique private float linewidth_secondary;

    @Override
    public Integer color() {
        return color;
    }

    @Override
    public Integer secondaryColor() {
        return secondaryColor;
    }

    @Override
    public Integer color_highcontrast() {
        return color_highcontrast;
    }

    @Override
    public Integer secondaryColor_highcontrast() {
        return secondaryColor_highcontrast;
    }

    @Override
    public Float lineWidth() {
        return linewidth;
    }

    @Override
    public float lineWidth_secondary() {
        return linewidth_secondary;
    }

    @Override
    public void color(Integer value) {
        this.color = value;
    }

    @Override
    public void secondaryColor(Integer value) {
        this.secondaryColor = value;
    }

    @Override
    public void color_highcontrast(Integer value) {
        this.color_highcontrast = value;
    }

    @Override
    public void secondaryColor_highcontrast(Integer value) {
        this.secondaryColor_highcontrast = value;
    }

    @Override
    public void lineWidth(Float value) {
        this.linewidth = value;
    }

    @Override
    public void lineWidth_secondary(float value) {
        this.linewidth_secondary = value;
    }
}
