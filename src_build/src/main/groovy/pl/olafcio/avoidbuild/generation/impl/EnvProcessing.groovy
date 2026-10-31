package pl.olafcio.avoidbuild.generation.impl

import org.gradle.api.Project
import pl.olafcio.avoidbuild.generation.ITransformer

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

class EnvProcessing implements ITransformer {
    private Project project
    private String onlyIn, dist
    private String server

    EnvProcessing(Project project, String onlyIn, String dist, String server = "SERVER") {
        this.project = project
        this.onlyIn = onlyIn
        this.dist = dist
        this.server = server
    }

    @Override
    void transform(Path sub) {
        var data = Files.readString(sub, StandardCharsets.UTF_8)
        var orig = data

        data = data.replace("net.fabricmc.api.Environment", onlyIn) \
                   .replace("net.fabricmc.api.EnvType", dist) \
                   .replace("@Environment(EnvType.SERVER", "@Environment(EnvType." + server) \
                   .replace("@Environment(EnvType.", "@${onlyIn.split("\\.").toList().getLast()}(${dist.split("\\.").toList().getLast()}.")

        if (data != orig)
            Files.writeString(sub, data, StandardCharsets.UTF_8)
    }
}
