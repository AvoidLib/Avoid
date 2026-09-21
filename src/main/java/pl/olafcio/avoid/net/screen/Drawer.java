package pl.olafcio.avoid.net.screen;

import org.jetbrains.annotations.ApiStatus;
import org.joml.Matrix3x2fStack;
import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.screen.font.Font;
import pl.olafcio.avoid.net.screen.renderlayer.RenderLayer;

/**
 * A class used to draw on a screen.
 * <br/><br/>
 * To apply matrix transformations, such as scaling, rotating or offset,
 * use {@link Drawer#matrixStack()}.
 */
@NeverRemoval
@ApiStatus.NonExtendable
public abstract class Drawer {
    /**
     * Draws a horizontal line, from X {@code x} to {@code x2},
     * on Y {@code y}, using the ARGB color {@code color}.
     */
    public abstract void horizontalLine(int x, int y, int x2, int color);

    /**
     * Draws a vertical line, from Y {@code y} to {@code y2},
     * on X {@code x}, using the ARGB color {@code color}.
     */
    public abstract void verticalLine(int x, int y, int y2, int color);

    /**
     * Enables cropping.
     * <br/><br/>
     * This limits the further drawing region to a rectangle with the specified coordinates.<br/>
     * To disable this effect, use {@link Drawer#disableCrop()}.
     */
    public abstract void enableCrop(int x, int y, int x2, int y2);

    /**
     * Disables cropping.
     * <br/><br/>
     * For more info, see: {@link Drawer#enableCrop}
     */
    public abstract void disableCrop();

    public abstract boolean containsPointInCrop(int x, int y);

    /**
     * Fills a region on the screen with the specified RGB color.
     */
    public abstract void fill(int x, int y, int x2, int y2, int color);

    /**
     * Fills a region on the screen, at the given layer, with the specified RGB color.
     */
    public abstract void fill(RenderLayer renderLayer, int x, int y, int x2, int y2, int color);

    /**
     * Draws a vertical gradient on the screen, in the specified region, with the specified RGB colors.
     */
    public abstract void fillGradient(int x, int y, int x2, int y2, int colorTop, int colorBottom);

    /**
     * Draws a text highlight region on the screen.<br/>
     * <b>NOTE:</b> This uses width and height, instead of x2 and y2!
     */
    public abstract void drawTextHighlight(int x, int y, int w, int h, boolean invert);

    public abstract void drawCenteredString(Font font, String string, int x, int y, int color);

    public abstract void drawCenteredString(Font font, BaseComponent<?> component, int x, int y, int color);

    public abstract void drawCenteredString(Font font, String string, int x, int y, int color, boolean shadow);

    public abstract void drawCenteredString(Font font, BaseComponent<?> component, int x, int y, int color, boolean shadow);

    public abstract void drawString(Font font, @Nullable String string, int x, int y, int color);

    public abstract void drawString(Font font, @Nullable String string, int x, int y, int color, boolean shadow);

    /**
     * Renders a chat component <i>(basically a more advanced form of text)</i> with shadow.
     * @param font The font to render with.
     * @param component The chat component to render.
     * @param x The screen X to render at. <i>(affected by pose)</i>
     * @param y The screen Y to render at. <i>(affected by pose)</i>
     * @param color The text color.
     */
    public abstract void drawString(Font font, BaseComponent<?> component, int x, int y, int color);

    /**
     * Renders a chat component <i>(basically a more advanced form of text)</i>.
     * @param font The font to render with.
     * @param component The chat component to render.
     * @param x The screen X to render at. <i>(affected by pose)</i>
     * @param y The screen Y to render at. <i>(affected by pose)</i>
     * @param color The text color.
     * @param shadow Whether to use native text shadow.
     */
    public abstract void drawString(Font font, BaseComponent<?> component, int x, int y, int color, boolean shadow);

    public abstract void drawWordWrap(Font font, BaseComponent<?> component, int x, int y, int maxWidth, int color);

    public abstract void drawWordWrap(Font font, BaseComponent<?> component, int x, int y, int maxWidth, int color, boolean shadow);

    public abstract void drawStringWithBackdrop(Font font, BaseComponent<?> component, int x, int y, int width, int color);

    /**
     * Renders a stroked rectangle on the screen using the given RGB color.<br/>
     * <b>NOTE:</b> This uses width and height, instead of x2 and y2!
     */
    public abstract void renderOutline(int x, int y, int w, int h, int color);

    /**
     * Renders a stroked rectangle on the screen using the given RGB color and the given renderlayer.<br/>
     * <b>NOTE:</b> This uses width and height, instead of x2 and y2!
     */
    public abstract void renderOutline(RenderLayer layer, int x, int y, int w, int h, int color);

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
    public abstract void blit(RenderLayer renderLayer, Identification id, int x, int y, float textureX, float textureY, int width, int height, int textureWidth, int textureHeight, int color);

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
    public abstract void blit(RenderLayer renderLayer, Identification id, int x, int y, float textureX, float textureY, int width, int height, int textureWidth, int textureHeight);

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
    public abstract void blit(RenderLayer renderLayer, Identification id, int x, int y, float textureX, float textureY, int width, int height, int spriteWidth, int spriteHeight, int textureWidth, int textureHeight);

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
    public abstract void blit(RenderLayer renderLayer, Identification id, int x, int y, float textureX, float textureY, int width, int height, int spriteWidth, int spriteHeight, int textureWidth, int textureHeight, int color);

    /**
     * Renders a texture on the screen.
     */
    public abstract void blit(Identification id, int x0, int y0, int x1, int y1, float u0, float u1, float v0, float v1);

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
    public abstract Matrix3x2fStack matrixStack();
}
