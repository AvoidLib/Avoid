package pl.olafcio.avoidbuild.generation.annotations

import java.lang.annotation.*

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface BeforeVCSTransform {
    String regex()
}
