package pl.olafcio.avoid_loader.interfaces.standard;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.util.ModEnvironment;
import pl.olafcio.avoid_loader.interfaces.IModMetadata;

import java.util.List;

@ApiStatus.Experimental
public interface IAvoidModMetadata extends IModMetadata {
    String versionSystem();
    String description();

    String mainClass();
    ModEnvironment env();

    List<JIJ> jars();

    List<String> libraries();
    List<String> skipClasses();

    interface JIJ {
        String path();
        Boolean library();
        Boolean mod();
    }
}
