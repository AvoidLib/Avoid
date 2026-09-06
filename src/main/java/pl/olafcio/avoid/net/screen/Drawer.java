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
import org.joml.Matrix3x2fStack;
import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.client.AvoidLibClient;
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

/**
 * A class used to draw on a screen.
 * <br/><br/>
 * To apply matrix transformations, such as scaling, rotating or offset,
 * use {@link Drawer#matrixStack()}.
 */
@NeverRemoval
public final class Drawer {
    GuiGraphics graphics;

    @ApiStatus.Internal
    Drawer() {}

    /**
     * Draws a horizontal line, from X {@code x} to {@code x2},
     * on Y {@code y}, using the ARGB color {@code color}.
     */
    public void horizontalLine(int x, int y, int x2, int color) {
        if (x2 < x) {
            int m = x;
            x = x2;
            x2 = m;
        }

        this.fill(x, y, x2 + 1, y + 1, color);
    }

    /**
     * Draws a vertical line, from Y {@code y} to {@code y2},
     * on X {@code x}, using the ARGB color {@code color}.
     */
    public void verticalLine(int x, int y, int y2, int color) {
        if (y2 < y) {
            int m = y;
            y = y2;
            y2 = m;
        }

        this.fill(x, y + 1, x + 1, y2, color);
    }

    /**
     * Enables cropping.
     * <br/><br/>
     * This limits the further drawing region to a rectangle with the specified coordinates.<br/>
     * To disable this effect, use {@link Drawer#disableCrop()}.
     */
    public void enableCrop(int x, int y, int x2, int y2) {
        ScreenRectangle screenRectangle = (new ScreenRectangle(x, y, x2 - x, y2 - y)).transformAxisAligned(this.graphics.pose());
        this.graphics.scissorStack.push(screenRectangle);
    }

    /**
     * Disables cropping.
     * <br/><br/>
     * For more info, see: {@link Drawer#enableCrop}
     */
    public void disableCrop() {
        this.graphics.scissorStack.pop();
    }

    public boolean containsPointInCrop(int x, int y) {
        return this.graphics.scissorStack.containsPoint(x, y);
    }

    /**
     * Fills a region on the screen with the specified RGB color.
     */
    public void fill(int x, int y, int x2, int y2, int color) {
        this.fill(RenderLayers.GUI, x, y, x2, y2, color);
    }

    /**
     * Fills a region on the screen, at the given layer, with the specified RGB color.
     */
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

    /**
     * Draws a vertical gradient on the screen, in the specified region, with the specified RGB colors.
     */
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

    /**
     * Draws a text highlight region on the screen.<br/>
     * <b>NOTE:</b> This uses width and height, instead of x2 and y2!
     */
    public void drawTextHighlight(int x, int y, int w, int h, boolean invert) {
        if (invert) {
            this.fill(RenderLayers.GUI_INVERT, x, y, w, h, -1);
        }

        this.fill(RenderLayers.GUI_TEXT_HIGHLIGHT, x, y, w, h, -16776961);
    }

    public void drawCenteredString(Font font, String string, int x, int y, int color) {
        this.drawString(font, string, x - font.width(string) / 2, y, color);
    }

    public void drawCenteredString(Font font, BaseComponent<?> component, int x, int y, int color) {
        FormattedCharSequence formattedCharSequence = COToNative.from(component).getVisualOrderText();
        this.drawString(font, formattedCharSequence, x - FontNative.convert(font).width(formattedCharSequence) / 2, y, color);
    }

    public void drawCenteredString(Font font, String string, int x, int y, int color, boolean shadow) {
        this.drawString(font, string, x - font.width(string) / 2, y, color, shadow);
    }

    public void drawCenteredString(Font font, BaseComponent<?> component, int x, int y, int color, boolean shadow) {
        FormattedCharSequence formattedCharSequence = COToNative.from(component).getVisualOrderText();
        this.drawString(font, formattedCharSequence, x - FontNative.convert(font).width(formattedCharSequence) / 2, y, color, shadow);
    }

    private void drawCenteredString(Font font, FormattedCharSequence formattedCharSequence, int x, int y, int color) {
        this.drawString(font, formattedCharSequence, x - FontNative.convert(font).width(formattedCharSequence) / 2, y, color);
    }

    public void drawString(Font font, @Nullable String string, int x, int y, int color) {
        this.drawString(font, string, x, y, color, true);
    }

    public void drawString(Font font, @Nullable String string, int x, int y, int color, boolean shadow) {
        if (string != null) {
            this.drawString(font, Language.getInstance().getVisualOrder(FormattedText.of(string)), x, y, color, shadow);
        }
    }

    private void drawString(Font font, FormattedCharSequence formattedCharSequence, int x, int y, int color) {
        this.drawString(font, formattedCharSequence, x, y, color, true);
    }

    private void drawString(Font font, FormattedCharSequence formattedCharSequence, int x, int y, int color, boolean shadow) {
        if (Coloring.getAlpha(color) != 0) {
            // TODO: Should I copy-lock GuiTextRenderState?
            //       Probably not, because the array of those is fucked up
            //       However it's probably doable with access-wideners

            this.graphics.guiRenderState.submitText(new GuiTextRenderState(
                    FontNative.convert(font),
                    formattedCharSequence,
                    new Matrix3x2f(this.graphics.pose()),
                    x, y,
                    color, 0,
                    shadow, false,
                    this.graphics.scissorStack.peek()
            ));
        }
    }

    /**
     * Renders a chat component <i>(basically a more advanced form of text)</i> with shadow.
     * @param font The font to render with.
     * @param component The chat component to render.
     * @param x The screen X to render at. <i>(affected by pose)</i>
     * @param y The screen Y to render at. <i>(affected by pose)</i>
     * @param color The text color.
     */
    public void drawString(Font font, BaseComponent<?> component, int x, int y, int color) {
        this.drawString(font, component, x, y, color, true);
    }

    /**
     * Renders a chat component <i>(basically a more advanced form of text)</i>.
     * @param font The font to render with.
     * @param component The chat component to render.
     * @param x The screen X to render at. <i>(affected by pose)</i>
     * @param y The screen Y to render at. <i>(affected by pose)</i>
     * @param color The text color.
     * @param shadow Whether to use native text shadow.
     */
    public void drawString(Font font, BaseComponent<?> component, int x, int y, int color, boolean shadow) {
        this.drawString(font, COToNative.from(component).getVisualOrderText(), x, y, color, shadow);
    }

    public void drawWordWrap(Font font, BaseComponent<?> component, int x, int y, int maxWidth, int color) {
        this.drawWordWrap(font, component, x, y, maxWidth, color, true);
    }

    public void drawWordWrap(Font font, BaseComponent<?> component, int x, int y, int maxWidth, int color, boolean shadow) {
        var formattedText = COToNative.from(component);
        for(FormattedCharSequence formattedCharSequence : FontNative.convert(font).split(formattedText, maxWidth)) {
            this.drawString(font, formattedCharSequence, x, y, color, shadow);
            Objects.requireNonNull(font);
            y += 9;
        }

    }

    public void drawStringWithBackdrop(Font font, BaseComponent<?> component, int x, int y, int width, int color) {
        int textBG = AvoidLibClient.mc.options.getBackgroundColor(0.0F);
        if (textBG != 0) {
            int n = 2;

            int myX = x - n;
            int myY = y - n;

            int myX2 = x + width + n;
            int myY2 = y + 9 + n;

            Objects.requireNonNull(font);

            this.fill(myX, myY, myX2, myY2, ARGB.multiply(textBG, color));
        }

        this.drawString(font, component, x, y, color, true);
    }

    /**
     * Renders a stroked rectangle on the screen using the given RGB color.<br/>
     * <b>NOTE:</b> This uses width and height, instead of x2 and y2!
     */
    public void renderOutline(int x, int y, int w, int h, int color) {
        this.fill(x, y, x + w, y + 1, color);
        this.fill(x, y + h - 1, x + w, y + h, color);
        this.fill(x, y + 1, x + 1, y + h - 1, color);
        this.fill(x + w - 1, y + 1, x + w, y + h - 1, color);
    }

    private static GuiSpriteScaling getSpriteScaling(TextureAtlasSprite textureAtlasSprite) {
        return textureAtlasSprite.contents().getAdditionalMetadata(GuiMetadataSection.TYPE).orElse(GuiMetadataSection.DEFAULT).scaling();
    }

    /**
     * Renders a texture on the screen.
     * @param renderLayer The layer to draw on.
     * @param id The texture ID to draw.
     * @param x The screen X to draw the texture at. <i>(affected by pose)</i>
     * @param y The screen Y to draw the texture at. <i>(affected by pose)</i>
     * @param textureX The X offset to draw the texture from. It's basically {@code u}.
     * @param textureY The Y offset to draw the texture from. It's basically {@code v}.
     * @param width The screen width to draw the texture in. <i>(affected by pose)</i>
     * @param height The screen height to draw the texture in. <i>(affected by pose)</i>
     * @param textureWidth The full width of the texture file.
     * @param textureHeight The full height of the texture file.
     * @param color An overlay color for the texture. {@code -1} if none.
     */
    public void blit(RenderLayer renderLayer, Identification id, int x, int y, float textureX, float textureY, int width, int height, int textureWidth, int textureHeight, int color) {
        this.blit(renderLayer, id, x, y, textureX, textureY, width, height, width, height, textureWidth, textureHeight, color);
    }

    /**
     * Renders a sprite from a texture on the screen.
     * @param renderLayer The layer to draw on.
     * @param id The texture ID to draw.
     * @param x The screen X to draw the texture at. <i>(affected by pose)</i>
     * @param y The screen Y to draw the texture at. <i>(affected by pose)</i>
     * @param textureX The X offset to draw the texture from. It's basically {@code u}.
     * @param textureY The Y offset to draw the texture from. It's basically {@code v}.
     * @param width The screen width to draw the texture in. <i>(affected by pose)</i>
     * @param height The screen height to draw the texture in. <i>(affected by pose)</i>
     * @param textureWidth The full width of the texture file.
     * @param textureHeight The full height of the texture file.
     */
    public void blit(RenderLayer renderLayer, Identification id, int x, int y, float textureX, float textureY, int width, int height, int textureWidth, int textureHeight) {
        this.blit(renderLayer, id, x, y, textureX, textureY, width, height, width, height, textureWidth, textureHeight);
    }

    /**
     * Renders a scaled sprite from a texture on the screen.
     * <br/><br/>
     * If you need an overlay color, see: {@link Drawer#blit(RenderLayer, Identification, int, int, float, float, int, int, int, int, int, int, int)}
     * @param renderLayer The layer to draw on.
     * @param id The texture ID to draw.
     * @param x The screen X to draw the texture at. <i>(affected by pose)</i>
     * @param y The screen Y to draw the texture at. <i>(affected by pose)</i>
     * @param textureX The X offset to draw the texture from. It's basically {@code u}.
     * @param textureY The Y offset to draw the texture from. It's basically {@code v}.
     * @param width The screen width to draw the texture in. <i>(affected by pose)</i>
     * @param height The screen height to draw the texture in. <i>(affected by pose)</i>
     * @param spriteWidth The width of the asset to draw from the texture.
     * @param spriteHeight The height of the asset to draw from the texture.
     * @param textureWidth The full width of the texture file.
     * @param textureHeight The full height of the texture file.
     */
    public void blit(RenderLayer renderLayer, Identification id, int x, int y, float textureX, float textureY, int width, int height, int spriteWidth, int spriteHeight, int textureWidth, int textureHeight) {
        this.blit(renderLayer, id, x, y, textureX, textureY, width, height, spriteWidth, spriteHeight, textureWidth, textureHeight, -1);
    }

    /**
     * Renders a scaled sprite from a texture on the screen.
     * @param renderLayer The layer to draw on.
     * @param id The texture ID to draw.
     * @param x The screen X to draw the texture at. <i>(affected by pose)</i>
     * @param y The screen Y to draw the texture at. <i>(affected by pose)</i>
     * @param textureX The X offset to draw the texture from. It's basically {@code u}.
     * @param textureY The Y offset to draw the texture from. It's basically {@code v}.
     * @param width The screen width to draw the texture in. <i>(affected by pose)</i>
     * @param height The screen height to draw the texture in. <i>(affected by pose)</i>
     * @param spriteWidth The width of the asset to draw from the texture.
     * @param spriteHeight The height of the asset to draw from the texture.
     * @param textureWidth The full width of the texture file.
     * @param textureHeight The full height of the texture file.
     * @param color An overlay color for the texture. {@code -1} if none.
     */
    public void blit(RenderLayer renderLayer, Identification id, int x, int y, float textureX, float textureY, int width, int height, int spriteWidth, int spriteHeight, int textureWidth, int textureHeight, int color) {
        this.innerBlit(renderLayer, id, x, x + width, y, y + height, (textureX + 0.0F) / (float)textureWidth, (textureX + (float)spriteWidth) / (float)textureWidth, (textureY + 0.0F) / (float)textureHeight, (textureY + (float)spriteHeight) / (float)textureHeight, color);
    }

    /**
     * Renders a texture on the screen.
     */
    public void blit(Identification id, int x0, int y0, int x1, int y1, float u0, float u1, float v0, float v1) {
        this.innerBlit(RenderLayers.GUI_TEXTURED, id, x0, x1, y0, y1, u0, u1, v0, v1, -1);
    }

    private void innerBlit(RenderLayer renderLayer, Identification identifier, int x0, int x1, int y0, int y1, float u0, float u1, float v0, float v1, int color) {
        AbstractTexture abstractTexture = AvoidLibClient.mc.getTextureManager().getTexture(IdentificationNative.convert(identifier));
        this.submitBlit(renderLayer, abstractTexture.getTextureView(), abstractTexture.getSampler(), x0, y0, x1, y1, u0, u1, v0, v1, color);
    }

    private void submitBlit(RenderLayer renderLayer, GpuTextureView gpuTextureView, GpuSampler gpuSampler, int x0, int y0, int x1, int y1, float u0, float u1, float v0, float v1, int color) {
        this.graphics.guiRenderState.submitGuiElement(new BlitRenderState(
                RenderLayerNative.convert(renderLayer),
                TextureSetup.singleTexture(gpuTextureView, gpuSampler),
                new Matrix3x2f(this.graphics.pose()),
                x0, y0,
                x1, y1,
                u0, u1,
                v0, v1,
                color,
                this.graphics.scissorStack.peek()
        ));
    }

    /**
     * Returns a screen pose object (a stack of matrix transformations).<br/>
     * The changes to it reflect to the actual screen pose.
     * <br/><br/>
     * For example, when you want to implement scrolling,<br/>
     * this is the method you want to use. Example:<br/>
     * <pre>
     * {@code
     * private int scroll = 0;
     *
     * public void render(Drawer drawer, int mouseX, int mouseY) {
     *     drawer.matrixStack().pushMatrix();
     *     drawer.matrixStack().translate(0, scroll);
     *     // render your stuff here
     *     drawer.matrixStack().popMatrix();
     * }
     *
     * // here an onscroll method that adds 1 to the 'scroll' field
     * }
     * </pre>
     */
    @ApiStatus.Experimental
    public Matrix3x2fStack matrixStack() {
        return this.graphics.pose();
    }
}
