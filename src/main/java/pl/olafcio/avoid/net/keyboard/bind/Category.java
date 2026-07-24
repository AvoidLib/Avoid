package pl.olafcio.avoid.net.keyboard.bind;

import net.minecraft.client.KeyMapping;

public final class Category {
    final KeyMapping.Category category;

    Category(KeyMapping.Category category) {
        this.category = category;
    }

    public static final Category MOVEMENT    = new Category(KeyMapping.Category.MOVEMENT);
    public static final Category MISC        = new Category(KeyMapping.Category.MISC);
    public static final Category MULTIPLAYER = new Category(KeyMapping.Category.MULTIPLAYER);
    public static final Category GAMEPLAY    = new Category(KeyMapping.Category.GAMEPLAY);
    public static final Category INVENTORY   = new Category(KeyMapping.Category.INVENTORY);
    public static final Category CREATIVE    = new Category(KeyMapping.Category.CREATIVE);
    public static final Category SPECTATOR   = new Category(KeyMapping.Category.SPECTATOR);
    public static final Category DEBUG       = new Category(KeyMapping.Category.DEBUG);
}
