package pl.olafcio.avoid_platform.loader_impl;

import pl.olafcio.avoid_loader.AvoidModContainer;
import pl.olafcio.avoid_loader.PreModLoader;
import pl.olafcio.avoid_loader.interfaces.IMod;
import pl.olafcio.avoid_loader.interfaces.services.IModProcessor;
import pl.olafcio.avoid_loader.subloaders.ILoader;

import java.lang.reflect.InvocationTargetException;

@Deprecated(since = "v2.0", forRemoval = true)
public final class AvoidModPluginLoader implements IModProcessor {
    @Override
    public void process(IMod imod) {
        if (imod instanceof AvoidModContainer mod && mod.manifest().has("loaders")) {
            var subloaders = mod.manifest().get("loaders").getAsJsonArray();

            try {
                for (var cn : subloaders) {
                    var pluginClass = mod.classLoader().loadClass(cn.getAsString());
                    var plugin = (ILoader) pluginClass.getDeclaredConstructor().newInstance();

                    plugin.load();

                    for (var mod2 : PreModLoader.getMods())
                        if (mod2 instanceof AvoidModContainer avoid)
                            plugin.processAvoidMod(avoid);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Failed to load subloader of Avoid mod: '%s'  /ClassNotFoundException".formatted(mod.path().toAbsolutePath()), e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Failed to load subloader of Avoid mod: '%s'  /InvocationTargetException".formatted(mod.path().toAbsolutePath()), e);
            } catch (InstantiationException e) {
                throw new RuntimeException("Failed to load subloader of Avoid mod: '%s'  /InstantiationException".formatted(mod.path().toAbsolutePath()), e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to load subloader of Avoid mod: '%s'  /IllegalAccessException\n  > Is your subloader's constructor and class public?".formatted(mod.path().toAbsolutePath()), e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Failed to load subloader of Avoid mod: '%s'  /NoSuchMethodException\n  > Does your subloader have a default constructor?".formatted(mod.path().toAbsolutePath()), e);
            }
        }
    }
}
