package pl.olafcio.avoid_impl.net.item.values;

import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net.item.values.SlotDescription;

@Native
@ApiStatus.Internal
public final class SlotDescriptionNative {
    @ApiStatus.Internal
    private SlotDescriptionNative() {}

    public static EquipmentSlot get(SlotDescription desc) {
             if (desc == SlotDescription.HEAD)     return EquipmentSlot.HEAD;
        else if (desc == SlotDescription.BODY)     return EquipmentSlot.BODY;
        else if (desc == SlotDescription.CHEST)    return EquipmentSlot.CHEST;
        else if (desc == SlotDescription.LEGS)     return EquipmentSlot.LEGS;
        else if (desc == SlotDescription.MAINHAND) return EquipmentSlot.MAINHAND;
        else if (desc == SlotDescription.OFFHAND)  return EquipmentSlot.OFFHAND;
        else if (desc == SlotDescription.SADDLE)   return EquipmentSlot.SADDLE;
        else if (desc == SlotDescription.FEET)     return EquipmentSlot.FEET;
        else
            throw new RuntimeException("Unknown slot-description '%s'".formatted(desc));
    }
}
