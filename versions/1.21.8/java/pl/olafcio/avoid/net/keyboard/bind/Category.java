package pl.olafcio.avoid.net.keyboard.bind;

import net.minecraft.client.KeyMapping;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;

public final class Category {
    final String category;

    private Category(String category) {
        this.category = category;
    }

    public Category(Identification id) {
        this("key.category." + id.namespace() + "." + id.path());
        KeyMapping.CATEGORY_SORT_ORDER.put(category, KeyMapping.CATEGORY_SORT_ORDER.size() + 1);
    }

    public static final Category MOVEMENT    = new Category(KeyMapping.CATEGORY_MOVEMENT);
    public static final Category MISC        = new Category(KeyMapping.CATEGORY_MISC);
    public static final Category MULTIPLAYER = new Category(KeyMapping.CATEGORY_MULTIPLAYER);
    public static final Category GAMEPLAY    = new Category(KeyMapping.CATEGORY_GAMEPLAY);
    public static final Category INVENTORY   = new Category(KeyMapping.CATEGORY_INVENTORY);
    public static final Category CREATIVE    = new Category(KeyMapping.CATEGORY_CREATIVE);
    public static final Category SPECTATOR   = new Category(Identification.of("avoidlib:spectator"));
    public static final Category DEBUG       = new Category(Identification.of("avoidlib:debug"));
}
