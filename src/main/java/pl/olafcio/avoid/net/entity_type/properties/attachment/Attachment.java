package pl.olafcio.avoid.net.entity_type.properties.attachment;

import pl.olafcio.avoid.net.entity_type.values.AttachmentType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({})
@Documented
public @interface Attachment {
    AttachmentType type();
    AttachmentOperation op();

    double x() default Double.MIN_VALUE;
    double y() default Double.MIN_VALUE;
    double z() default Double.MIN_VALUE;
}
