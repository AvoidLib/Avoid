package pl.olafcio.avoid;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import pl.olafcio.avoid.listeners.*;
import pl.olafcio.avoid.mods.loader.AvoidModLoader;
import pl.olafcio.avoid.mods.AvoidModMeta;

public class A4Paper extends JavaPlugin {
    public static A4Paper INSTANCE;

    { INSTANCE = this; }

    @Override
    public void onEnable() {
        Avoid.INSTANCE.onInitialize();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new FoodListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
    }

    @Override
    public void onDisable() {
        var addons = AvoidModLoader.getLoadedAddons();
        for (AvoidModMeta mod : addons) {
            var main = AvoidModLoader.getLoadedAddonClass(mod);

            main.onDisable();
            main.onServerDisable();
        }
    }
}
