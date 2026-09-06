package pl.olafcio.avoid.mods.events_loader;

import com.google.gson.JsonObject;

import java.util.jar.JarFile;

/**
 * Fired just before a mod's main class should be loaded.
 */
public record ModBeforeLoadEvent(String id, JsonObject manifest, JarFile jar) {}
