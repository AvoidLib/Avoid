package pl.olafcio.avoid.net.item.values;

import net.minecraft.world.entity.EquipmentSlot;

public enum SlotDescription {
    MAINHAND(Type.HAND, EquipmentSlot.MAINHAND, 0, 0, 0, "mainhand"),
    OFFHAND(Type.HAND, EquipmentSlot.OFFHAND, 1, 0, 5, "offhand"),
    FEET(Type.HUMANOID_ARMOR, EquipmentSlot.FEET, 0, 1, 1, "feet"),
    LEGS(Type.HUMANOID_ARMOR, EquipmentSlot.LEGS, 1, 1, 2, "legs"),
    CHEST(Type.HUMANOID_ARMOR, EquipmentSlot.CHEST, 2, 1, 3, "chest"),
    HEAD(Type.HUMANOID_ARMOR, EquipmentSlot.HEAD, 3, 1, 4, "head"),
    BODY(Type.ANIMAL_ARMOR, EquipmentSlot.BODY, 0, 1, 6, "body"),
    SADDLE(Type.SADDLE, EquipmentSlot.SADDLE, 0, 1, 7, "saddle");

    public final Type type;
    final EquipmentSlot equipmentSlot;
    public final int index;
    public final int countLimit;
    public final int id;
    public final String name;

    SlotDescription(Type type, EquipmentSlot equipmentSlot, int index, int countLimit, int id, String name) {
        this.type = type;
        this.equipmentSlot = equipmentSlot;
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
