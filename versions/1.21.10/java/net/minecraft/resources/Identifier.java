package net.minecraft.resources;

public final class Identifier extends ResourceLocation {
    private Identifier(String string, String string2) {
        super(string, string2);
    }

    private static Identifier createUntrusted(String string, String string2) {
        return new Identifier(assertValidNamespace(string, string2), assertValidPath(string, string2));
    }

    public static Identifier fromNamespaceAndPath(String string, String string2) {
        return createUntrusted(string, string2);
    }

    public static Identifier bySeparator(String string, char c) {
        int i = string.indexOf(c);
        if (i >= 0) {
            String string2 = string.substring(i + 1);
            if (i != 0) {
                String string3 = string.substring(0, i);
                return createUntrusted(string3, string2);
            } else {
                return withDefaultNamespace(string2);
            }
        } else {
            return withDefaultNamespace(string);
        }
    }

    public static Identifier withDefaultNamespace(String string) {
        return new Identifier("minecraft", assertValidPath("minecraft", string));
    }
}
