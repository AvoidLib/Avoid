package pl.olafcio.avoid_platform.loader_impl;

import pl.olafcio.avoid_loader.AvoidModContainer;
import pl.olafcio.avoid_loader.ModClassLoader;
import pl.olafcio.avoid_loader.abst.ModFile;

import java.nio.file.Path;

public class JIJModFile extends ModFile {
    protected final AvoidModContainer parent;

    public JIJModFile(Path path, AvoidModContainer parent) {
        super(path);
        this.parent = parent;
    }

    public JIJModFile(Path path, ModClassLoader classLoader, AvoidModContainer parent) {
        super(path, classLoader);
        this.parent = parent;
    }
}
