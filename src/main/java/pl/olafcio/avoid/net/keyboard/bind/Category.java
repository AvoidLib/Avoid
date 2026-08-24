package pl.olafcio.avoid.net.keyboard.bind;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.RunningEnv;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;

public final class Category {
    @Environment(EnvType.CLIENT)
    InternalCategory category;

    @Environment(EnvType.CLIENT)
    private Category(Object category) {
        this.category = new InternalCategory((KeyMapping.Category) category);
    }

    public Category(Identification id) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.CLIENT)
            this.init(id);
    }

    @Environment(EnvType.CLIENT)
    private void init(Identification id) {
        this.category = new InternalCategory(KeyMapping.Category.register(IdentificationNative.convert(id)));
    }

    public static Category MOVEMENT;
    public static Category MISC;
    public static Category MULTIPLAYER;
    public static Category GAMEPLAY;
    public static Category INVENTORY;
    public static Category CREATIVE;
    public static Category SPECTATOR;
    public static Category DEBUG;

    @Environment(EnvType.CLIENT)
    private static void clinit() {
        MOVEMENT    = new Category(KeyMapping.Category.MOVEMENT);
        MISC        = new Category(KeyMapping.Category.MISC);
        MULTIPLAYER = new Category(KeyMapping.Category.MULTIPLAYER);
        GAMEPLAY    = new Category(KeyMapping.Category.GAMEPLAY);
        INVENTORY   = new Category(KeyMapping.Category.INVENTORY);
        CREATIVE    = new Category(KeyMapping.Category.CREATIVE);
        SPECTATOR   = new Category(KeyMapping.Category.SPECTATOR);
        DEBUG       = new Category(KeyMapping.Category.DEBUG);
    }

    static {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.CLIENT)
            clinit();
    }
}
