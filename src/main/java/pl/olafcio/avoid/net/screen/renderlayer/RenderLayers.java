package pl.olafcio.avoid.net.screen.renderlayer;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.renderer.RenderPipelines;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.Avoid;

import java.lang.reflect.Modifier;
import java.util.HashMap;

@ApiStatus.Experimental
public final class RenderLayers {
    @ApiStatus.Internal
    private RenderLayers() {}

    private static final HashMap<String, RenderPipeline> PRESENT;
    static {
        PRESENT = new HashMap<>();

        var fields = RenderPipelines.class.getDeclaredFields();
        for (var field : fields) {
            try {
                if (!RenderPipeline.class.isAssignableFrom(field.getType()))
                    continue;

                int modifiers = field.getModifiers();
                if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers))
                    PRESENT.put(field.getName(), (RenderPipeline) field.get(null));
            } catch (IllegalAccessException | ClassCastException e) {
                Avoid.LOGGER.error("Couldn't register RenderPipeline '{}'; ignoring", field);
            }
        }
    }

    public static final RenderLayer SOLID_BLOCK = register("SOLID_BLOCK");
    public static final RenderLayer SOLID_TERRAIN = register("SOLID_TERRAIN");
    public static final RenderLayer WIREFRAME = register("WIREFRAME");
    public static final RenderLayer CUTOUT_BLOCK = register("CUTOUT_BLOCK");
    public static final RenderLayer CUTOUT_TERRAIN = register("CUTOUT_TERRAIN");
    public static final RenderLayer TRANSLUCENT_TERRAIN = register("TRANSLUCENT_TERRAIN");
    public static final RenderLayer TRIPWIRE_BLOCK = register("TRIPWIRE_BLOCK");
    public static final RenderLayer TRIPWIRE_TERRAIN = register("TRIPWIRE_TERRAIN");
    public static final RenderLayer TRANSLUCENT_MOVING_BLOCK = register("TRANSLUCENT_MOVING_BLOCK");
    public static final RenderLayer ARMOR_CUTOUT_NO_CULL = register("ARMOR_CUTOUT_NO_CULL");
    public static final RenderLayer ARMOR_DECAL_CUTOUT_NO_CULL = register("ARMOR_DECAL_CUTOUT_NO_CULL");
    public static final RenderLayer ARMOR_TRANSLUCENT = register("ARMOR_TRANSLUCENT");
    public static final RenderLayer ENTITY_SOLID = register("ENTITY_SOLID");
    public static final RenderLayer ENTITY_SOLID_Z_OFFSET_FORWARD = register("ENTITY_SOLID_Z_OFFSET_FORWARD");
    public static final RenderLayer ENTITY_CUTOUT = register("ENTITY_CUTOUT");
    public static final RenderLayer ENTITY_CUTOUT_NO_CULL = register("ENTITY_CUTOUT_NO_CULL");
    public static final RenderLayer ENTITY_CUTOUT_NO_CULL_Z_OFFSET = register("ENTITY_CUTOUT_NO_CULL_Z_OFFSET");
    public static final RenderLayer ENTITY_TRANSLUCENT = register("ENTITY_TRANSLUCENT");
    public static final RenderLayer ENTITY_TRANSLUCENT_EMISSIVE = register("ENTITY_TRANSLUCENT_EMISSIVE");
    public static final RenderLayer ENTITY_SMOOTH_CUTOUT = register("ENTITY_SMOOTH_CUTOUT");
    public static final RenderLayer ENTITY_NO_OUTLINE = register("ENTITY_NO_OUTLINE");
    public static final RenderLayer BREEZE_WIND = register("BREEZE_WIND");
    public static final RenderLayer ENERGY_SWIRL = register("ENERGY_SWIRL");
    public static final RenderLayer EYES = register("EYES");
    public static final RenderLayer ENTITY_DECAL = register("ENTITY_DECAL");
    public static final RenderLayer ENTITY_SHADOW = register("ENTITY_SHADOW");
    public static final RenderLayer ITEM_ENTITY_TRANSLUCENT_CULL = register("ITEM_ENTITY_TRANSLUCENT_CULL");
    public static final RenderLayer BEACON_BEAM_OPAQUE = register("BEACON_BEAM_OPAQUE");
    public static final RenderLayer BEACON_BEAM_TRANSLUCENT = register("BEACON_BEAM_TRANSLUCENT");
    public static final RenderLayer DRAGON_EXPLOSION_ALPHA = register("DRAGON_EXPLOSION_ALPHA");
    public static final RenderLayer LEASH = register("LEASH");
    public static final RenderLayer WATER_MASK = register("WATER_MASK");
    public static final RenderLayer GLINT = register("GLINT");
    public static final RenderLayer CRUMBLING = register("CRUMBLING");
    public static final RenderLayer TEXT = register("TEXT");
    public static final RenderLayer GUI_TEXT = register("GUI_TEXT");
    public static final RenderLayer TEXT_BACKGROUND = register("TEXT_BACKGROUND");
    public static final RenderLayer TEXT_INTENSITY = register("TEXT_INTENSITY");
    public static final RenderLayer GUI_TEXT_INTENSITY = register("GUI_TEXT_INTENSITY");
    public static final RenderLayer TEXT_POLYGON_OFFSET = register("TEXT_POLYGON_OFFSET");
    public static final RenderLayer TEXT_SEE_THROUGH = register("TEXT_SEE_THROUGH");
    public static final RenderLayer TEXT_BACKGROUND_SEE_THROUGH = register("TEXT_BACKGROUND_SEE_THROUGH");
    public static final RenderLayer TEXT_INTENSITY_SEE_THROUGH = register("TEXT_INTENSITY_SEE_THROUGH");
    public static final RenderLayer LIGHTNING = register("LIGHTNING");
    public static final RenderLayer DRAGON_RAYS = register("DRAGON_RAYS");
    public static final RenderLayer DRAGON_RAYS_DEPTH = register("DRAGON_RAYS_DEPTH");
    public static final RenderLayer END_PORTAL = register("END_PORTAL");
    public static final RenderLayer END_GATEWAY = register("END_GATEWAY");
    public static final RenderLayer FLAT_CLOUDS = register("FLAT_CLOUDS");
    public static final RenderLayer CLOUDS = register("CLOUDS");
    public static final RenderLayer LINES = register("LINES");
    public static final RenderLayer LINES_TRANSLUCENT = register("LINES_TRANSLUCENT");
    public static final RenderLayer SECONDARY_BLOCK_OUTLINE = register("SECONDARY_BLOCK_OUTLINE");
    public static final RenderLayer DEBUG_POINTS = register("DEBUG_POINTS");
    public static final RenderLayer DEBUG_FILLED_BOX = register("DEBUG_FILLED_BOX");
    public static final RenderLayer DEBUG_QUADS = register("DEBUG_QUADS");
    public static final RenderLayer DEBUG_TRIANGLE_FAN = register("DEBUG_TRIANGLE_FAN");
    public static final RenderLayer WORLD_BORDER = register("WORLD_BORDER");
    public static final RenderLayer OPAQUE_PARTICLE = register("OPAQUE_PARTICLE");
    public static final RenderLayer TRANSLUCENT_PARTICLE = register("TRANSLUCENT_PARTICLE");
    public static final RenderLayer WEATHER_DEPTH_WRITE = register("WEATHER_DEPTH_WRITE");
    public static final RenderLayer WEATHER_NO_DEPTH_WRITE = register("WEATHER_NO_DEPTH_WRITE");
    public static final RenderLayer SKY = register("SKY");
    public static final RenderLayer END_SKY = register("END_SKY");
    public static final RenderLayer SUNRISE_SUNSET = register("SUNRISE_SUNSET");
    public static final RenderLayer STARS = register("STARS");
    public static final RenderLayer CELESTIAL = register("CELESTIAL");
    public static final RenderLayer GUI = register("GUI");
    public static final RenderLayer GUI_INVERT = register("GUI_INVERT");
    public static final RenderLayer GUI_TEXT_HIGHLIGHT = register("GUI_TEXT_HIGHLIGHT");
    public static final RenderLayer GUI_TEXTURED = register("GUI_TEXTURED");
    public static final RenderLayer GUI_TEXTURED_PREMULTIPLIED_ALPHA = register("GUI_TEXTURED_PREMULTIPLIED_ALPHA");
    public static final RenderLayer BLOCK_SCREEN_EFFECT = register("BLOCK_SCREEN_EFFECT");
    public static final RenderLayer FIRE_SCREEN_EFFECT = register("FIRE_SCREEN_EFFECT");
    public static final RenderLayer GUI_OPAQUE_TEXTURED_BACKGROUND = register("GUI_OPAQUE_TEXTURED_BACKGROUND");
    public static final RenderLayer GUI_NAUSEA_OVERLAY = register("GUI_NAUSEA_OVERLAY");
    public static final RenderLayer VIGNETTE = register("VIGNETTE");
    public static final RenderLayer CROSSHAIR = register("CROSSHAIR");
    public static final RenderLayer MOJANG_LOGO = register("MOJANG_LOGO");
    public static final RenderLayer ENTITY_OUTLINE_BLIT = register("ENTITY_OUTLINE_BLIT");
    public static final RenderLayer TRACY_BLIT = register("TRACY_BLIT");
    public static final RenderLayer PANORAMA = register("PANORAMA");
    public static final RenderLayer OUTLINE_CULL = register("OUTLINE_CULL");
    public static final RenderLayer OUTLINE_NO_CULL = register("OUTLINE_NO_CULL");
    public static final RenderLayer LIGHTMAP = register("LIGHTMAP");
    public static final RenderLayer ANIMATE_SPRITE_BLIT = register("ANIMATE_SPRITE_BLIT");
    public static final RenderLayer ANIMATE_SPRITE_INTERPOLATE = register("ANIMATE_SPRITE_INTERPOLATE");

    @ApiStatus.Internal
    public static RenderLayer register(String name) {
        var pipeline = PRESENT.get(name);
        var isPresent = pipeline != null;

        return new RenderLayer() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public boolean isPresent() {
                return isPresent;
            }

            @Override
            RenderPipeline get() {
                return pipeline;
            }
        };
    }
}
