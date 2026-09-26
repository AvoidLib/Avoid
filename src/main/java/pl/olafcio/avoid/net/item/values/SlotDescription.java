package pl.olafcio.avoid.net.item.values;

public enum SlotDescription {
    MAINHAND(Type.HAND, 0, 0, 0, "mainhand"),
    OFFHAND(Type.HAND, 1, 0, 5, "offhand"),
    FEET(Type.HUMANOID_ARMOR, 0, 1, 1, "feet"),
    LEGS(Type.HUMANOID_ARMOR, 1, 1, 2, "legs"),
    CHEST(Type.HUMANOID_ARMOR, 2, 1, 3, "chest"),
    HEAD(Type.HUMANOID_ARMOR, 3, 1, 4, "head"),
    BODY(Type.ANIMAL_ARMOR, 0, 1, 6, "body"),
    SADDLE(Type.SADDLE, 0, 1, 7, "saddle");

    public final Type type;
    public final int index;
    public final int countLimit;
    public final int id;
    public final String name;

    SlotDescription(Type type, int index, int countLimit, int id, String name) {
        this.type = type;
        this.index = index;
        this.countLimit = countLimit;
        this.id = id;
        this.name = name;
    }

    public enum Type {
        HAND,
        HUMANOID_ARMOR,
        ANIMAL_ARMOR,
        SADDLE
    }
}
