package pl.olafcio.avoid_common;

import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@ApiStatus.Internal
@ApiStatus.Experimental
public final class JarManager {
    private final Class<?> klass;

    public JarManager(Class<?> klass) {
        this.klass = klass;
    }

    public String[] listFilenames(String pkg) throws IOException {
        if (!klass.getClassLoader().getResource("").getPath().contains("!")) {
            // IDE
            try (var stream = klass.getClassLoader().getResourceAsStream(pkg)) {
                if (stream == null)
                    return new String[0];

                return new String(stream.readAllBytes()).split("\n");
            }
        } else {
            // Prod
            if (!pkg.endsWith("/"))
                pkg += "/";

            // multi-classloader support with package name specified
            try (var zip = new ZipFile(klass.getClassLoader().getResource(pkg.substring(0, pkg.length() - 1)).getPath().split("!")[0].substring(5))) { //file:
                var entries = zip.entries();
                var list = new ArrayList<String>();

                while (entries.hasMoreElements()) {
                    var entry = entries.nextElement();
                    var name = entry.getName();

                    if (name.startsWith(pkg) && !name.equals(pkg) && !name.equals(pkg.substring(0, pkg.length() - 1))) {
                        var sub = name.substring(pkg.length());
                        if (!sub.contains("/"))
                            list.add(sub);
                        else if (sub.substring(sub.indexOf("/") + 1).isEmpty())
                            list.add(sub.substring(0, sub.indexOf("/")));
                    }
                }

                return list.toArray(String[]::new);
            }
        }
    }
}
