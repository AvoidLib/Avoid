package pl.olafcio.avoidbuild

import groovy.transform.Internal
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import pl.olafcio.avoidbuild.generation.ITransformer
import pl.olafcio.avoidbuild.generation.annotations.BeforeTransform
import pl.olafcio.avoidbuild.generation.annotations.BeforeVCSTransform

import java.lang.reflect.Method
import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.Path
import java.util.function.BiConsumer
import java.util.function.Consumer

class MainPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        CodeExtension.project = project

        project.extensions.create("code", CodeExtension)
        project.tasks.compileJava.doFirst {
            var ext = project.extensions.getByType(CodeExtension)

            for (var gen : ext.generators)
                gen.init()

            if (!ext.transformers.isEmpty())
                new Transformer(ext.transformers).run(project)
        }
    }

    @Internal
    static final class Transformer {
        private final List<ITransformer> transformers

        Transformer(List<ITransformer> transformers) {
            this.transformers = transformers
        }

        private interface VCSAnalyzer {
            void analyze(String path, String content)
            boolean shouldAnalyze(String path)
        }

        private interface PathAnalyzer {
            void analyze(Path path, String relative)
            boolean shouldAnalyze(Path path)
        }

        void run(Project project) {
            var beforeTransform = new ArrayList<PathAnalyzer>()
            var beforeVCS       = new ArrayList<VCSAnalyzer>()

            for (var transformer : transformers) {
                var methods = transformer.class.getMethods()

                for (var m : methods) {
                    if (m.isAnnotationPresent(BeforeTransform.class)) {
                        beforeTransform.add(new PathAnalyzer() {
                            final Method method = m;

                            @Override
                            void analyze(Path path, String relative) {
                                method.invoke(transformer, path, relative)
                            }

                            @Override
                            boolean shouldAnalyze(Path path) {
                                return (path.toAbsolutePath().toString() =~ method.getAnnotation(BeforeTransform.class).regex()).find()
                            }
                        })
                    } else if (m.isAnnotationPresent(BeforeVCSTransform.class)) {
                        beforeVCS.add(new VCSAnalyzer() {
                            final Method method = m;

                            @Override
                            void analyze(String path, String content) {
                                method.invoke(transformer, path, content)
                            }

                            @Override
                            boolean shouldAnalyze(String path) {
                                return (path =~ method.getAnnotation(BeforeVCSTransform.class).regex()).find()
                            }
                        })
                    }
                }
            }

            if (!beforeVCS.isEmpty()) {
                var paths = new String(new ProcessBuilder().command("git", "ls-tree", "--name-only", "-r", "head")
                                                                       .redirectOutput(ProcessBuilder.Redirect.PIPE)
                                                                       .directory(project.projectDir)
                                                                       .start()
                                                                       .in
                                                                       .readAllBytes()).readLines()

                for (var path : paths) {
                    for (var analyzer : beforeVCS) {
                        if (analyzer.shouldAnalyze(path)) {
                            var content = new String(new ProcessBuilder().command("git", "cat-file", "blob", "head:" + path)
                                    .redirectOutput(ProcessBuilder.Redirect.PIPE)
                                    .directory(project.projectDir)
                                    .start()
                                    .in
                                    .readAllBytes())

                            analyzer.analyze(path, content)
                        }
                    }
                }
            }

            if (!beforeTransform.isEmpty()) {
                for (var src : ((SourceSetContainer) project.sourceSets).main.java.srcDirs) {
                    walkAny(src.toPath(), (sub, rel) -> {
                        for (var transformer : beforeTransform)
                            if (transformer.shouldAnalyze(sub))
                                transformer.analyze(sub, rel)

                        if (rel.endsWith(".java"))
                            this.transform(sub)
                    })
                }
            } else {
                for (var src : ((SourceSetContainer) project.sourceSets).main.java.srcDirs) {
                    walk(src.toPath(), this::transform)
                }
            }
        }

        void transform(Path path) {
            transformers*.transform(path)
        }

        static void walk(Path path, Consumer<Path> transform) {
            try (var files = Files.list(path)) {
                files.forEach(sub -> {
                    if (Files.isDirectory(sub)) {
                        walk(sub, transform)
                    } else if (sub.getFileName().toString().endsWith(".java")) {
                        transform(sub)
                    }
                })
            }
        }

        static void walkAny(Path path, BiConsumer<Path, String> transform, String relative = "") {
            try (var files = Files.list(path)) {
                files.forEach(sub -> {
                    if (Files.isDirectory(sub)) {
                        walkAny(sub, transform, relative + "/" + sub.getFileName().toString())
                    } else if (Files.isRegularFile(sub, LinkOption.NOFOLLOW_LINKS)) {
                        transform(sub, relative + "/" + sub.getFileName().toString())
                    }
                })
            }
        }
    }
}
