package pl.olafcio.avoid.net.keyboard.bind;

import net.minecraft.client.KeyMapping;
import pl.olafcio.avoid.annotations.dist.MCEnvironment;
import pl.olafcio.avoid.platform.AvoidWrappedLoader;
import pl.olafcio.avoid.platform.RunningEnv;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid_impl.net.id.IdentificationNative;

public final class Category {
    @MCEnvironment("client")
    InternalCategory category;

    @MCEnvironment("client")
    private Category(Object category) {
        this.category = new InternalCategory((KeyMapping.Category) category);
    }

    public Category(Identification id) {
        if (AvoidWrappedLoader.getRunningEnvironment() == RunningEnv.CLIENT)
            this.init(id);
    }

    @MCEnvironment("client")
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

    @MCEnvironment("client")
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
