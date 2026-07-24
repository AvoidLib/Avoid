package pl.olafcio.avoid.net.fluid.properties;

import java.lang.annotation.*;

/**
 * Enables swimming for the fluid.<br/><br/>
 * Currently, AvoidLib doesn't have the fully-natural swimming mechanic (you can swim but cannot control yourself in-air in any other way).
 * I am working on changing this. It is like that because I simply don't know how to get the fully natural mechanic to work.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface _swimmable {}
