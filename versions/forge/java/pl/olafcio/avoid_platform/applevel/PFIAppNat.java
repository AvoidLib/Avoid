package pl.olafcio.avoid_platform.applevel;

import net.minecraftforge.fml.ModLoader;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

public enum PFIAppNat {
    ;

    /**
     * Adds the injection for {@linkplain ModLoader#LOGGER}
     * @param inject Callback to run on any fatal message, it's <b>your responsibility</b> to make sure this doesn't trigger code twice
     * @param myURLs The URL of this class + AvoidLoader
     */
    public static void thisIsInsane(Runnable inject, URL[] myURLs) {
        var ucl = new URLClassLoader(myURLs, PFIAppNat.class.getClassLoader());   // Prefer not to load avoid_platform onto APP, that'd be quite destructive

        try {
            ucl.loadClass("pl.olafcio.avoid_platform.applevel.ActualInjection")
               .getDeclaredMethod("withdepends", Runnable.class)
               .invoke(null, inject);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to run PFIAppNat::withdepends([Runnable] inject) in sub-classloader", e);
        }
    }
}
