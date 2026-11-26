package pl.olafcio.avoid_loader;

import org.jetbrains.annotations.ApiStatus;

// Public if yo ass does unclever classloader shit like me

/**
 * If you need to use classloaders yourself, and you encounter errors related to Avoid's JIJ-ed libraries,<br/>
 * <b>don't hardcode the ifs.</b> <b><u>Use this namespace instead.</u></b>
 * <br/><br/>
 * This class contains the {@link #convert} method, used to runtimize classnames.
 */
@ApiStatus.Internal
public enum ShadowArtifacts {
    ;

    private static boolean isLoaded(String cn) {
        try {
            Class.forName(cn);
        } catch (Throwable e) {
            return false;
        }

        return true;
    }

    private static final boolean ASM = isLoaded("pl.olafcio.avoid.__shadow__.asm.ClassReader");
    private static final boolean YAML = isLoaded("pl.olafcio.avoid.__shadow__.snakeyaml.Yaml");
    private static final boolean COMMONS = isLoaded("pl.olafcio.avoid.__shadow__.apcommons.lang3.Strings");
    private static final boolean JSPECIFY = isLoaded("pl.olafcio.avoid.__shadow__.jspecify.annotations.Nullable");
    private static final boolean MIXINEXTRAS = isLoaded("pl.olafcio.avoid.__shadow__.mixinextras.MixinExtrasBootstrap");

    public static String convert(String name) {
        // Unconstanted for Shadow
        if (ASM && name.startsWith("org.objectweb." + __("asm.")))
            name = "pl.olafcio.avoid.__shadow__.asm." + name.substring(18);
        else if (YAML && name.startsWith("org.yaml." + __("snakeyaml.")))
            name = "pl.olafcio.avoid.__shadow__.snakeyaml." + name.substring(19);
        else if (COMMONS && name.startsWith("org.apache." + __("commons.")))
            name = "pl.olafcio.avoid.__shadow__.apcommons." + name.substring(19);
        else if (JSPECIFY && name.startsWith("org." + __("jspecify.")))
            name = "pl.olafcio.avoid.__shadow__.jspecify." + name.substring(13);
        else if (MIXINEXTRAS && name.startsWith("com.llamalad7." + __("mixinextras.")))
            name = "pl.olafcio.avoid.__shadow__.mixinextras." + name.substring(26);

        return name;
    }

    public static String convertBytecode(String name) {
        // Unconstanted for Shadow

        if (ASM) {
            name = name.replace("org/objectweb/" + __("asm/"), "pl/olafcio/avoid/__shadow__/asm/");
            name = name.replace("org.objectweb." + __("asm."), "pl.olafcio.avoid.__shadow__.asm.");
        }

        if (YAML) {
            name = name.replace("org/yaml/" + __("snakeyaml/"), "pl/olafcio/avoid/__shadow__/snakeyaml/");
            name = name.replace("org.yaml." + __("snakeyaml."), "pl.olafcio.avoid.__shadow__.snakeyaml.");
        }

        if (COMMONS) {
            name = name.replace("org/apache/" + __("commons/"), "pl/olafcio/avoid/__shadow__/apcommons/");
            name = name.replace("org.apache." + __("commons."), "pl.olafcio.avoid.__shadow__.apcommons.");
        }

        if (JSPECIFY) {
            name = name.replace("org/" + __("jspecify/"), "pl/olafcio/avoid/__shadow__/jspecify/");
            name = name.replace("org." + __("jspecify."), "pl.olafcio.avoid.__shadow__.jspecify.");
        }

        if (MIXINEXTRAS) {
            name = name.replace("com/llamalad7/" + __("mixinextras/"), "pl/olafcio/avoid/__shadow__/mixinextras/");
            name = name.replace("com.llamalad7." + __("mixinextras."), "pl.olafcio.avoid.__shadow__.mixinextras.");
        }

        return name;
    }

    private static String __(String val) {
        return val;
    }
}
