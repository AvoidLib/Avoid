package pl.olafcio.avoid_loader;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;

/**
 * A classloader used for all mods loaded by Avoid Loader.
 * <br/><br/><hr/><br/>
 * <b>Definitions</b>
 * <br/><br/>
 * <u>wrapped loading</u> - loading returned to the parent ModClassLoader.<br/>
 * <u>loading parent</u> - the classloader to fallback to, if not already loaded.
 */
@ApiStatus.Internal
public class ModClassLoader extends URLClassLoader {
    public ModClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public ModClassLoader(URL[] urls, ClassLoader parent, boolean startup) {
        this(urls, parent);
        this.startup = startup;
    }

    /**
     * If {@code false}, Minecraft classes shouldn't be referencable from this classloader.
     */
    public boolean startup = true;

    private final HashSet<String> loaded
            = new HashSet<>();

    /**
     * An alternative, changeable parent classloader.
     */
    @Nullable
    public ModClassLoader parent;

    /**
     * Returns whether a standard wrapped ModClassLoader will consider loading the specified className from this ModClassLoader.
     * @param name The classname to consider loading from this ModClassLoader.
     */
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

    /**
     * Appends the specified URL to the list of URLs to search for classes and resources.
     * <p>
     * If the URL specified is {@code null} or is already in the list of URLs, or if this loader is closed, then invoking this method has no effect.
     * @param url the URL to be added to the search path of URLs
     */
    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    /**
     * Returns whether this classloader had ever loaded a class under the specified className by itself.<br/>
     * Wrapped loading does not count.
     */
    public boolean isClassLoaded(String className) {
        return loaded.contains(className);
    }

    /**
     * Creates a new ModClassLoader, with a cyclic dependency on this ModClassLoader, but the loading parent changed.
     * @param classLoader Loading parent for the created ModClassLoader.
     */
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

    /**
     * Changes the wrapped loader, with a cyclic dependency on this ModClassLoader, but the given URLs appended at the start of future URLs.
     * @param newURLs URL-s to load future classes from.
     */
    public void prefer(URL[] newURLs) {
        var oldURLs = this.getURLs();
        var URLs = new URL[newURLs.length + oldURLs.length];

        System.arraycopy(newURLs, 0, URLs, 0, newURLs.length);
        System.arraycopy(oldURLs, 0, URLs, newURLs.length, oldURLs.length);

        var wrapped = new ModClassLoader(URLs, getParent()) {
            @Override
            public boolean shouldOverrideParent(String name) {
                return true;
            }
        };

        wrapped.parent = this;
        this.parent = wrapped;
    }
}
