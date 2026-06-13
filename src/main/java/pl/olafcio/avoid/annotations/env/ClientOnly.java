package pl.olafcio.avoid.annotations.env;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface ClientOnly {}
