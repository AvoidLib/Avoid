package pl.olafcio.avoidbuild.generation.impl

import org.gradle.api.Project
import pl.olafcio.avoidbuild.generation.ITransformer
import pl.olafcio.avoidbuild.generation.annotations.BeforeTransform
import pl.olafcio.langbase.Langbase
import pl.olafcio.langbase.SectionType

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.util.function.Consumer
import java.util.function.Function
import java.util.regex.Matcher

class SpecFiles implements ITransformer {
    private Project project

    SpecFiles(Project project) {
        this.project = project
    }

    private record Spec(String pkg, Record[] statements) {}
    private record ImportStatement(String pkg) {}
    private record AnnotationBuilder(String outOf, String appendix) {}

    public static final LANG = Langbase.<Spec, Record, Record>builder() { LANG ->
        var String pkg

        LANG.findStatement('package (.+?);', (Consumer<Matcher>) (Matcher matcher) -> { pkg = matcher.group(1) }, Map.of())

        LANG.addGlobalStatement('import (.+?);', (Function<Matcher, Record>) (Matcher matcher) -> new ImportStatement(matcher.group(1)))
        LANG.addGlobalSection('make an annotation builder:', SectionType.SPACED) { SECTION ->
            var String outOf
            var String appendix = ""

            SECTION.findStatement('out of "(.+?)"', (Consumer<Matcher>) (Matcher matcher) -> { outOf = matcher.group(1) }, optional: false)
            SECTION.findSection(
                    'onto:',
                    section -> {
                        appendix = section.content
                    },
                    SectionType.SPACED,
                    optional: true
            )

            assert outOf != null

            return new AnnotationBuilder(outOf, appendix)
        }

        assert pkg != null

        return new Spec(pkg, LANG.run().toArray(Record[]::new))
    }

    @BeforeTransform(regex = 'spec\\.[a-zA-Z0-9]+$')
    void addSpec(Path path, String relative) {
        var out = ""
        var imports = new HashSet<String>()

        var input = LANG.parseFile(path)

        var root = null
        var name = path.getFileName().toString() \
                                            .substring(5)  // 'spec.'

        for (var stmt : input.statements()) {
            if (stmt instanceof AnnotationBuilder) {
                if (root != null)
                    throw new RuntimeException("Multiple root objects specified:\n" +
                                               "> already has " + root.getClass().getSimpleName() + " as root\n" +
                                               "> tried to specify " + stmt.getClass().getSimpleName())

                root = stmt

                imports.add("org.jetbrains.annotations.ApiStatus")
                imports.add("java.lang.annotation.Annotation")
                imports.add("java.util.ArrayList")
                imports.add("java.lang.reflect.AnnotatedElement")
                imports.add("org.jetbrains.annotations.NotNull")
                imports.add("org.jspecify.annotations.NullMarked")

                out += """@ApiStatus.Experimental
                          public class ${name} {
                            protected final ArrayList<Annotation> annotations
                                      = new ArrayList<>();
                            
                            public AnnotatedElement build() {
                                return new AnnotatedElement() {
                                    @Override
                                    @SuppressWarnings("unchecked")
                                    public <T extends Annotation> T getAnnotation(@NotNull Class<T> annotationClass) {
                                        return (T) annotations.stream().filter(a -> a.annotationType().equals(annotationClass)).findAny().orElse(null);
                                    }
                        
                                    @Override
                                    @NullMarked
                                    public Annotation[] getAnnotations() {
                                        return annotations.toArray(Annotation[]::new);
                                    }
                        
                                    @Override
                                    @NullMarked
                                    public Annotation[] getDeclaredAnnotations() {
                                        return annotations.toArray(Annotation[]::new);
                                    }
                                };
                            }
                            
                            ${stmt.appendix()}

                          """

                var directory = path.getParent().resolve(stmt.outOf())
                var files = Files.list(directory)

                try {
                    for (var file : files) {
                        var fn = file.getFileName().toString()
                        if (fn.endsWith(".java")) {
                            // Parse
                            var annotationName = fn.substring(0, fn.size() - 5)
                            var annotationProperties = []

                            var content       = Files.readString(file, StandardCharsets.UTF_8)
                            var contentLines = content.split("\n")

                            for (var line : contentLines) {
                                if (line.startsWith("package ") || line.trim().isEmpty() || line.startsWith("/") || line.startsWith("*"))
                                    continue
                                else if (line.startsWith("import "))
                                    imports.add(line.substring(7, line.length() - 2))
                                else
                                    break
                            }

                            var body   = content.substring(content.indexOf("{"), content.lastIndexOf("}"))
                            var lines = body.split("\n")

                            for (var line : lines) {
                                var trim = line.stripLeading()

                                if (line.startsWith("    ") && !trim.startsWith("/") && !trim.startsWith("*")) {
                                    var beforeParen = trim.substring(0, trim.indexOf('('))
                                    var space = beforeParen.lastIndexOf(' ')

                                    annotationProperties.add([
                                            beforeParen.substring(0, space),
                                            beforeParen.substring(space + 1)
                                    ])
                                }
                            }

                            // Append
                            var srcDir = path.toAbsolutePath().toString()
                            if (srcDir[1] == ':')
                                srcDir = srcDir.substring(2)

                            srcDir = srcDir.substring(0, relative.length())

                            var annotationPackage = file.toAbsolutePath().toString()
                            if (annotationPackage[1] == ':')
                                annotationPackage = annotationPackage.substring(2)

                            annotationPackage = annotationPackage.substring(srcDir.size())
                            annotationPackage = annotationPackage.substring(1, annotationPackage.size() - 5)
                            annotationPackage = annotationPackage.replace("\\", ".")
                            annotationPackage = annotationPackage.replace("/", ".")

                            if (annotationName == name) {
                                annotationName = annotationPackage
                            } else {
                                imports.add(annotationPackage)
                            }

                            var methodName = fn.substring(0, 1).toLowerCase() + annotationName
                            var methodArgs = []
                            var methodImpl = [
                                    "\n" +
                                    "            @Override\n" +
                                    "            public Class<? extends Annotation> annotationType() {\n" +
                                    "                return ${annotationName}.class;\n" +
                                    "            }\n"
                            ]

                            for (var property : annotationProperties) {
                                var signature = property[0] + " " + property[1]

                                methodArgs.add(signature)
                                methodImpl.add("\n" +
                                               "            @Override\n" +
                                               "            public ${signature}() {\n" +
                                               "                return ${property[1]};\n" +
                                               "            }\n")
                            }

                            out += "    public ${name} ${methodName}(${String.join(", ", methodArgs)}) {\n"
                            out += "        annotations.add(new ${annotationName}() {"
                            out +=              String.join("", methodImpl)
                            out += "        });\n"
                            out += "\n"
                            out += "        return this;\n"
                            out += "    }\n"
                            out += "\n"
                        }
                    }
                } finally {
                    files.close()
                }
            }
        }

        if (root == null)
            throw new RuntimeException("No root object specified")

        out += "}\n"

        // Package & imports
        var start = ""

        start += "package " + input.pkg() + ";"
        start += "\n\n"

        var hadImports = false

        for (var stmt : input.statements()) {
            if (stmt instanceof ImportStatement) {
                hadImports = true

                start += "import " + stmt.pkg() + ";"
                start += "\n"
            }
        }

        for (var pkg : imports) {
            start += "import " + pkg + ";"
            start += "\n"
        }

        if (hadImports) {
            start += "\n\n"
        }

        out = start + out

        // Write & return
        var slash = relative.lastIndexOf('/')

        var rel1 = relative.substring(1, slash)
        var rel2 = relative.substring(slash + 1)

        var outPath = project.file("src/main/generated").toPath() \
                                                                    .resolve(rel1) \
                                                                    .resolve(rel2.substring(5) + ".java")

        Files.createDirectories(outPath.getParent())
        Files.writeString(outPath, out, StandardCharsets.UTF_8)
    }

    @Override
    void transform(Path sub) {}
}
