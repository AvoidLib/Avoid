package pl.olafcio.avoid_loader;

import org.jetbrains.annotations.ApiStatus;

import java.net.URL;
import java.net.URLClassLoader;

@ApiStatus.Internal
public class ModClassLoader extends URLClassLoader {
    public ModClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public boolean startup = true;

    @Override
    protected Class<?> loadClass(String name, boolean resolve)
       throws ClassNotFoundException
    {
        if (startup && name.startsWith("net.minecraft."))
            throw new ClassNotFoundException("Cannot load Minecraft classes during subloading!");

        return super.loadClass(name, resolve);
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }
}
