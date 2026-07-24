package pl.olafcio.avoid.net.creative_tab.property;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _title {
    String value();
}
