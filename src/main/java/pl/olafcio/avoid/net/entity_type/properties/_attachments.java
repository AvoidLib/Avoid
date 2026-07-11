package pl.olafcio.avoid.net.entity_type.properties;

import pl.olafcio.avoid.net.entity_type.properties.attachment.Attachment;
import pl.olafcio.avoid.net.entity_type.values.AttachmentType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface _attachments {
    Attachment[] value();
}
