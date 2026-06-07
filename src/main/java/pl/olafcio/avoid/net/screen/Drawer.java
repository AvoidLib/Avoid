package pl.olafcio.avoid.net.screen;

import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.render.state.BlitRenderState;
import net.minecraft.client.gui.render.state.ColoredRectangleRenderState;
import net.minecraft.client.gui.render.state.GuiTextRenderState;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.metadata.gui.GuiMetadataSection;
import net.minecraft.client.resources.metadata.gui.GuiSpriteScaling;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.ARGB;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Matrix3x2f;
import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.chat.converter.COToNative;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;
import pl.olafcio.avoid.net.screen.font.Font;
import pl.olafcio.avoid.net.screen.font.FontNative;
import pl.olafcio.avoid.net.screen.renderlayer.RenderLayer;
import pl.olafcio.avoid.net.screen.renderlayer.RenderLayerNative;
import pl.olafcio.avoid.net.screen.renderlayer.RenderLayers;
import pl.olafcio.avoid.net.util.Coloring;

import java.util.Objects;

@ApiStatus.Experimental
public final class Drawer {
    GuiGraphics graphics;

    @ApiStatus.Internal
    Drawer() {}

    public void horizontalLine(int x, int y, int x2, int color) {
        if (x2 < x) {
            int m = x;
            x = x2;
            x2 = m;
        }

        this.fill(x, y, x2 + 1, y + 1, color);
    }

    public void verticalLine(int x, int y, int y2, int color) {
        if (y2 < y) {
            int m = y;
            y = y2;
            y2 = m;
        }

        this.fill(x, y + 1, x + 1, y2, color);
    }

    public void enableCrop(int x, int y, int x2, int y2) {
        ScreenRectangle screenRectangle = (new ScreenRectangle(x, y, x2 - x, y2 - y)).transformAxisAligned(this.graphics.pose());
        this.graphics.scissorStack.push(screenRectangle);
    }

    public void disableCrop() {
        this.graphics.scissorStack.pop();
    }

    public boolean containsPointInCrop(int x, int y) {
        return this.graphics.scissorStack.containsPoint(x, y);
    }

    public void fill(int x, int y, int x2, int y2, int color) {
        this.fill(RenderLayers.GUI, x, y, x2, y2, color);
    }

    public void fill(RenderLayer renderLayer, int x, int y, int x2, int y2, int color) {
        if (x < x2) {
            int n = x;
            x = x2;
            x2 = n;
        }

        if (y < y2) {
            int n = y;
            y = y2;
            y2 = n;
        }

        this.submitColoredRectangle(renderLayer, TextureSetup.noTexture(), x, y, x2, y2, color, (Integer)null);
    }

    public void fillGradient(int x, int y, int x2, int y2, int colorTop, int colorBottom) {
        this.submitColoredRectangle(RenderLayers.GUI, TextureSetup.noTexture(), x, y, x2, y2, colorTop, colorBottom);
    }

    private void fill(RenderLayer renderLayer, TextureSetup textureSetup, int x, int y, int x2, int y2) {
        this.submitColoredRectangle(renderLayer, textureSetup, x, y, x2, y2, -1, (Integer)null);
    }

    private void submitColoredRectangle(RenderLayer renderLayer, TextureSetup textureSetup, int x, int y, int x2, int y2, int color, @Nullable Integer colorBottom) {
        // TODO: Should I copy-lock ColoredRectangleRenderState?
        // TODO: Support floating operations

        this.graphics.guiRenderState.submitGuiElement(new ColoredRectangleRenderState(
                RenderLayerNative.convert(renderLayer),
                textureSetup,
                new Matrix3x2f(this.graphics.pose()),
                x, y,
                x2, y2,
                color,
                colorBottom != null ? colorBottom : color,
                this.graphics.scissorStack.peek()
        ));
    }

    public void drawTextHighlight(int x, int y, int w, int h, boolean invert) {
        if (invert) {
            this.fill(RenderLayers.GUI_INVERT, x, y, w, h, -1);
        }

        this.fill(RenderLayers.GUI_TEXT_HIGHLIGHT, x, y, w, h, -16776961);
    }

    public void drawCenteredString(Font font, String string, int x, int y, int color) {
        this.drawString(font, string, x - font.width(string) / 2, y, color);
    }

    public void drawCenteredString(Font font, BaseComponent<?> component, int i, int j, int k) {
        FormattedCharSequence formattedCharSequence = COToNative.from(component).getVisualOrderText();
        this.drawString(font, formattedCharSequence, i - FontNative.convert(font).width(formattedCharSequence) / 2, j, k);
    }

    private void drawCenteredString(Font font, FormattedCharSequence formattedCharSequence, int i, int j, int k) {
        this.drawString(font, formattedCharSequence, i - FontNative.convert(font).width(formattedCharSequence) / 2, j, k);
    }

    public void drawString(Font font, @Nullable String string, int i, int j, int k) {
        this.drawString(font, string, i, j, k, true);
    }

    public void drawString(Font font, @Nullable String string, int i, int j, int k, boolean bl) {
        if (string != null) {
            this.drawString(font, Language.getInstance().getVisualOrder(FormattedText.of(string)), i, j, k, bl);
        }
    }

    private void drawString(Font font, FormattedCharSequence formattedCharSequence, int i, int j, int k) {
        this.drawString(font, formattedCharSequence, i, j, k, true);
    }

    private void drawString(Font font, FormattedCharSequence formattedCharSequence, int i, int j, int k, boolean shadow) {
        if (Coloring.getAlpha(k) != 0) {
            // TODO: Should I copy-lock GuiTextRenderState?
            //       Probably not, because the array of those is fucked up
            //       However it's probably doable with access-wideners

            this.graphics.guiRenderState.submitText(new GuiTextRenderState(
                    FontNative.convert(font),
                    formattedCharSequence,
                    new Matrix3x2f(this.graphics.pose()),
                    i, j,
                    k, 0,
                    shadow, false,
                    this.graphics.scissorStack.peek()
            ));
        }
    }

    public void drawString(Font font, BaseComponent<?> component, int i, int j, int k) {
        this.drawString(font, component, i, j, k, true);
    }

    public void drawString(Font font, BaseComponent<?> component, int i, int j, int k, boolean bl) {
        this.drawString(font, COToNative.from(component).getVisualOrderText(), i, j, k, bl);
    }

    public void drawWordWrap(Font font, BaseComponent<?> component, int i, int j, int k, int l) {
        this.drawWordWrap(font, component, i, j, k, l, true);
    }

    public void drawWordWrap(Font font, BaseComponent<?> component, int x, int y, int k, int l, boolean bl) {
        var formattedText = COToNative.from(component);
        for(FormattedCharSequence formattedCharSequence : FontNative.convert(font).split(formattedText, k)) {
            this.drawString(font, formattedCharSequence, x, y, l, bl);
            Objects.requireNonNull(font);
            y += 9;
        }

    }

    public void drawStringWithBackdrop(Font font, BaseComponent<?> component, int i, int j, int k, int l) {
        int m = Avoid.mc.options.getBackgroundColor(0.0F);
        if (m != 0) {
            int n = 2;
            int var10001 = i - 2;
            int var10002 = j - 2;
            int var10003 = i + k + 2;
            Objects.requireNonNull(font);
            this.fill(var10001, var10002, var10003, j + 9 + 2, ARGB.multiply(m, l));
        }

        this.drawString(font, component, i, j, l, true);
    }

    public void renderOutline(int x, int y, int w, int h, int color) {
        this.fill(x, y, x + w, y + 1, color);
        this.fill(x, y + h - 1, x + w, y + h, color);
        this.fill(x, y + 1, x + 1, y + h - 1, color);
        this.fill(x + w - 1, y + 1, x + w, y + h - 1, color);
    }

    private static GuiSpriteScaling getSpriteScaling(TextureAtlasSprite textureAtlasSprite) {
        return textureAtlasSprite.contents().getAdditionalMetadata(GuiMetadataSection.TYPE).orElse(GuiMetadataSection.DEFAULT).scaling();
    }

    public void blit(RenderLayer renderLayer, Identification id, int i, int j, float f, float g, int k, int l, int m, int n, int o) {
        this.blit(renderLayer, id, i, j, f, g, k, l, k, l, m, n, o);
    }

    public void blit(RenderLayer renderLayer, Identification id, int i, int j, float f, float g, int k, int l, int m, int n) {
        this.blit(renderLayer, id, i, j, f, g, k, l, k, l, m, n);
    }

    public void blit(RenderLayer renderLayer, Identification id, int i, int j, float f, float g, int k, int l, int m, int n, int o, int p) {
        this.blit(renderLayer, id, i, j, f, g, k, l, m, n, o, p, -1);
    }

    public void blit(RenderLayer renderLayer, Identification id, int i, int j, float f, float g, int k, int l, int m, int n, int o, int p, int q) {
        this.innerBlit(renderLayer, id, i, i + k, j, j + l, (f + 0.0F) / (float)o, (f + (float)m) / (float)o, (g + 0.0F) / (float)p, (g + (float)n) / (float)p, q);
    }

    public void blit(Identification id, int i, int j, int k, int l, float f, float g, float h, float m) {
        this.innerBlit(RenderLayers.GUI_TEXTURED, id, i, k, j, l, f, g, h, m, -1);
    }

    private void innerBlit(RenderLayer renderLayer, Identification identifier, int i, int j, int k, int l, float f, float g, float h, float m, int n) {
        AbstractTexture abstractTexture = Avoid.mc.getTextureManager().getTexture(IdentificationNative.convert(identifier));
        this.submitBlit(renderLayer, abstractTexture.getTextureView(), abstractTexture.getSampler(), i, k, j, l, f, g, h, m, n);
    }

    private void submitBlit(RenderLayer renderLayer, GpuTextureView gpuTextureView, GpuSampler gpuSampler, int i, int j, int k, int l, float f, float g, float h, float m, int n) {
        this.graphics.guiRenderState.submitGuiElement(new BlitRenderState(
                RenderLayerNative.convert(renderLayer),
                TextureSetup.singleTexture(gpuTextureView, gpuSampler),
                new Matrix3x2f(this.graphics.pose()),
                i, j,
                k, l,
                f, g,
                h, m,
                n,
                this.graphics.scissorStack.peek()
        ));
    }
}
