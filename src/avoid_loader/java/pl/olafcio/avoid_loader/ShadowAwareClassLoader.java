package pl.olafcio.avoid_loader;

import org.jetbrains.annotations.ApiStatus;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;

@ApiStatus.Internal
public class ShadowAwareClassLoader extends URLClassLoader {
    public ShadowAwareClassLoader(URL[] urls, ClassLoader parent) {
        super(shadowArtifactsBytecodeCrimeHall(urls), parent);
    }

    public ShadowAwareClassLoader(URL[] urls) {
        super(shadowArtifactsBytecodeCrimeHall(urls));
    }

    public ShadowAwareClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(shadowArtifactsBytecodeCrimeHall(urls), parent, factory);
    }

    public ShadowAwareClassLoader(String name, URL[] urls, ClassLoader parent) {
        super(name, shadowArtifactsBytecodeCrimeHall(urls), parent);
    }

    public ShadowAwareClassLoader(String name, URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(name, shadowArtifactsBytecodeCrimeHall(urls), parent, factory);
    }

    private static final ArrayList<URL> PASSED_URLS
                   = new ArrayList<>();

    /// Changes bytecode of all the array's URLs to be compliant with {@link ShadowArtifacts}.
    private static URL[] shadowArtifactsBytecodeCrimeHall(URL[] urls) {
        final URL[] out = new URL[urls.length];
        int i = 0;

        for (var url : urls) {
            if (PASSED_URLS.contains(url)) {
                out[i] = url;
                continue;
            }

            try (var patcher = ClassPatcher.create(url)) {
                patcher.patch(is -> {
                    var reader = new ClassReader(is) {
                        @Override
                        public String readUTF8(int offset, char[] charBuffer) {
                            var val = super.readUTF8(offset, charBuffer);
                            if (val == null)
                                return null;

                            return ShadowArtifacts.convertBytecode(val);
                        }
                    };

                    var writer = new ClassWriter(0);

                    reader.accept(writer, 0);

                    return writer.toByteArray();
                });

                var url1 = patcher.getPath().toUri().toURL();

                out[i++] = url1;
                PASSED_URLS.add(url1);
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException("Failed to modify bytecode of Avoid mod '%s' for Shadow library fixes".formatted(url.toString()), e);
            }
        }

        return out;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve)
       throws ClassNotFoundException
    {
        name = fixName(name);

        return super.loadClass(name, resolve);
    }

    @Override
    protected Class<?> findClass(String name)
       throws ClassNotFoundException
    {
        name = fixName(name);

        return super.findClass(name);
    }

    /**
     * <b>WARNING:</b> Please note that this mechanism requires separate classfile modification for non-reflection uses!
     */
    private String fixName(String name) {
        return ShadowArtifacts.convert(name);
    }
}
