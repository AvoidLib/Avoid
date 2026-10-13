package pl.olafcio.avoid_loader;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;

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
    private final HashSet<String> loaded
            = new HashSet<>();

    @Nullable
    public ModClassLoader parent;

    public boolean shouldOverrideParent(String name) {
        return isClassLoaded(name);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve)
       throws ClassNotFoundException
    {
        if (startup && name.startsWith("net.minecraft."))
            throw new ClassNotFoundException("Cannot load Minecraft classes during subloading!");

        if (parent != null && parent.shouldOverrideParent(name) && !isClassLoaded(name))
            return parent.loadClass(name, resolve);

        var result = super.loadClass(name, resolve); // Deferring the adding so that non-existent classes don't count
        loaded.add(name);
        return result;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (parent != null && parent.shouldOverrideParent(name) && !isClassLoaded(name))
            return parent.findClass(name);

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

    public ModClassLoader wrap(ClassLoader classLoader) {
        var wrapped = new ModClassLoader(this.getURLs(), classLoader) {
            @Override
            public boolean shouldOverrideParent(String name) {
                return true;
            }
        };

        wrapped.parent = this;
        this.parent = wrapped;

        return wrapped;
    }
}
