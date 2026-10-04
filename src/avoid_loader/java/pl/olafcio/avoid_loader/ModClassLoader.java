package pl.olafcio.avoid_loader;

import org.jetbrains.annotations.ApiStatus;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

@ApiStatus.Internal
public class ModClassLoader extends URLClassLoader {
    public ModClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public ModClassLoader(URL[] urls, ClassLoader parent, boolean startup) {
        this(urls, parent);
        this.startup = startup;
    }

    public boolean startup = true;
    private final ArrayList<String> loaded
            = new ArrayList<>();

    @Override
    protected Class<?> loadClass(String name, boolean resolve)
       throws ClassNotFoundException
    {
        if (startup && name.startsWith("net.minecraft."))
            throw new ClassNotFoundException("Cannot load Minecraft classes during subloading!");

        var result = super.loadClass(name, resolve); // Deferring the adding so that non-existent classes don't count
        loaded.add(name);
        return result;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        var result = super.findClass(name); // Deferring the adding so that non-existent classes don't count
        loaded.add(name);
        return result;
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    public boolean isClassLoaded(String className) {
        return loaded.contains(className);
    }
}
