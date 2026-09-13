package pl.olafcio.avoid.annotations.compile;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@WillRefactor(aspect = "name")
@ApiStatus.Internal
@ApiStatus.NonExtendable
public @interface DoNotRename {}
