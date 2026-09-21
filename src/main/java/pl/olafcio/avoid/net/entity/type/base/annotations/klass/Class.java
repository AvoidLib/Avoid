package pl.olafcio.avoid.net.entity.type.base.annotations.klass;

import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ApiStatus.Internal
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Class {
    java.lang.Class<? extends Entity> value();
}
