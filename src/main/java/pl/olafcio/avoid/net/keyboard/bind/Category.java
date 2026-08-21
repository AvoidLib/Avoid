package pl.olafcio.avoid.net.keyboard.bind;

import net.minecraft.client.KeyMapping;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.id.IdentificationNative;

public final class Category {
    final InternalCategory category;

    private Category(Object category) {
        this.category = new InternalCategory((KeyMapping.Category) category);
    }

    public Category(Identification id) {
        this(KeyMapping.Category.register(IdentificationNative.convert(id)));
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
