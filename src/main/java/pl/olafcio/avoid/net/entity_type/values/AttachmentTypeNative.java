package pl.olafcio.avoid.net.entity_type.values;

import net.minecraft.world.entity.EntityAttachment;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@ApiStatus.Internal
@Native
public final class AttachmentTypeNative {
    @ApiStatus.Internal
    private AttachmentTypeNative() {}

    public static EntityAttachment convertFrom(AttachmentType type) {
             if (type == AttachmentType.PASSENGER)    return EntityAttachment.PASSENGER;
        else if (type == AttachmentType.NAME_TAG)     return EntityAttachment.NAME_TAG;
        else if (type == AttachmentType.VEHICLE)      return EntityAttachment.VEHICLE;
        else if (type == AttachmentType.WARDEN_CHEST) return EntityAttachment.WARDEN_CHEST;

        throw new UnsupportedOperationException("Unknown Avoid attachment type '" + type + "'");
    }
}
