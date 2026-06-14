package pl.olafcio.avoid.net.item.values;

import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@ApiStatus.Internal
public final class SlotDescriptionNative {
    @ApiStatus.Internal
    private SlotDescriptionNative() {}

    public static EquipmentSlot get(SlotDescription desc) {
        return desc.equipmentSlot;
    }
}
