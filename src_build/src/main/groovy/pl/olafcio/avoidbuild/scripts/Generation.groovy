package pl.olafcio.avoidbuild.scripts

import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.ZipFile

class Generation {
    private Project project

    Generation(Project project) {
        this.project = project
    }

    void init() {
        project.tasks.register("generate") { doFirst {
            def module = project.rootProject.file("src/main/generated").toPath()

            Files.createDirectories(module)

            def mcJar = \
                   (Files.list(project.rootProject.file(".gradle/loom-cache/minecraftMaven/net/minecraft").toPath())
                         .flatMap { Files.list(it) }
                         .filter { it.getFileName().toString() == project.minecraft_version || it.getFileName().toString().startsWith(project.minecraft_version + "-") }
                         .flatMap { Files.list(it) }
                         .find { it.getFileName().toString().endsWith(".jar") && !it.getFileName().toString().contains("sources") } as Path)
                         .toFile()

            try (var zip = new ZipFile(mcJar)) {
                var entries = zip.entries()

                while (entries.hasMoreElements()) {
                    var entry = entries.nextElement()
                    if (entry.name.startsWith("net/minecraft/world/entity") && entry.name.endsWith(".class")) {
                        var        node = new ClassNode()
                        var byte[] bytes

                        try (var stream = zip.getInputStream(entry)) {
                            bytes = stream.readAllBytes()
                        }

                        new ClassReader(bytes).accept(node, 0)

                        var supers = ["net/minecraft/world/entity/Entity", "net/minecraft/world/entity/LivingEntity", "net/minecraft/world/entity/Mob", "net/minecraft/world/entity/PathfinderMob", "net/minecraft/world/entity/monster/Monster"]

                        if (node.superName !in supers || node.name in supers)
                            continue

                        if ((node.access & Opcodes.ACC_PUBLIC) != Opcodes.ACC_PUBLIC)
                            continue

                        if ((node.access & Opcodes.ACC_MODULE) == Opcodes.ACC_MODULE)
                            continue

                        if ((node.access & Opcodes.ACC_ABSTRACT) == Opcodes.ACC_ABSTRACT)
                            continue

                        var datas = new HashMap<String, String>()

                        for (var field : node.fields) {
                            if (field.desc == "Lnet/minecraft/network/syncher/EntityDataAccessor;" && field.name.startsWith("DATA_")) {
                                datas.put(field.name.substring(5).replace("_", "").toLowerCase(), field.name)
                            }
                        }

                        var simpleName = node.name.split("/").last()
                        var outPath = "pl/olafcio/avoid/net/entity/type/${simpleName}"

                        if (project.file("src/main/java/" + outPath + ".java").exists())
                            continue

                        if (project.file("src/main/generated/" + outPath + ".disable").exists())
                            continue

                        var out = """
package ${outPath.substring(0, outPath.lastIndexOf("/")).replace("/", ".")};

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.chat.component.BaseComponent;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity.type.base.annotations.AutoEntityAttach;
import pl.olafcio.avoid.net.entity.type.base.annotations.klass.Class;
import pl.olafcio.avoid.net.entity_type.EntityType;
import pl.olafcio.avoid.net.world.vect3.IVect3;

import java.util.UUID;

@AutoEntityAttach
@Class(${node.name.replace("/", ".")}.class)

@ApiStatus.Experimental

public class ${simpleName} extends Entity {
    public ${simpleName}(int id, EntityType type, IVect3 position, IVect3 velocity, UUID uuid, BaseComponent<?> name, net.minecraft.world.entity.Entity underlyingEntity) {
        super(id, type, position, velocity, uuid, name, underlyingEntity);
    }
""".trim()

                        loop: for (var method : node.methods) {
                            if ((method.access & Opcodes.ACC_PUBLIC) != Opcodes.ACC_PUBLIC)
                                continue

                            def name = method.name.toLowerCase()

                            for (var n : datas.keySet()) {
                                if (name == ("get" + n) || name == ("is" + n) || name == ("hasbeen" + n)) {
                                    var returns = [
                                            "()I": "int",
                                            "()F": "float",
                                            "()Z": "boolean",
                                            "()D": "double",
                                            "()J": "long"
                                    ][method.desc]

                                    if (!returns)
                                        continue loop

                                    out += "\n"
                                    out += "\n    public ${returns} ${method.name}() {"
                                    out += "\n        return __cast(${node.name.replace("/", ".")}.class).${method.name}();"
                                    out += "\n    }"

                                    continue loop
                                } else if (name == ("set" + n) || name == ("setbeing" + n)) {
                                    var arg = [
                                            "(I)V": "int",
                                            "(F)V": "float",
                                            "(Z)V": "boolean",
                                            "(D)V": "double",
                                            "(J)V": "long"
                                    ][method.desc]

                                    if (!arg)
                                        continue loop

                                    out += "\n"
                                    out += "\n    public void ${method.name}(${arg} value) {"
                                    out += "\n        __cast(${node.name.replace("/", ".")}.class).${method.name}(value);"
                                    out += "\n    }"

                                    continue loop
                                }
                            }
                        }

                        out += "\n}\n"

                        Files.createDirectories(module.resolve(outPath).getParent())
                        Files.writeString(module.resolve(outPath + ".java"), out, StandardCharsets.UTF_8)
                    }
                }
            }
        }}

        project.tasks.build.dependsOn "generate"
    }
}
