package pl.olafcio.avoidbuild.generation.impl

import org.gradle.api.Project
import pl.olafcio.avoidbuild.generation.ITransformer
import pl.olafcio.avoidbuild.generation.annotations.BeforeVCSTransform

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.util.regex.Pattern

class MixinToReflect implements ITransformer {
    private Project project

    MixinToReflect(Project project) {
        this.project = project
    }

    private def replacements = new HashMap<GString, String>()

    @BeforeVCSTransform(regex = '/mixin/accessors/')
    void analyze(String path, String content) {
        var mixin = path.substring(0, path.length() - 5) //.java
                        .replace("/", ".")
                        .split("\\.")
                        .drop(3)
                        .join(".")

        var imports = new HashMap<String, String>()

        String field
        String invoke
        String target

        replacements.put("(" + Pattern.quote("import ${mixin};") + ")", '// $1')

        for (var line : content.lines()) {
            var orig = line

            line = line.trim()

            if (orig.startsWith(" ") && line.endsWithAny(';', '{') && line.contains("(")) {
                def method = line.split("\\(")[0].trim().split(" ").last()
                def ref = mixin.split("\\.").last()

                def type = line.split("\\(")[0].trim().split(" ")
                type         = type[type.length - 2]

                var _import = type.split("<")[0]

                var _fir = _import.split("\\.")[0]
                var _sub = _import.contains(".") ? _import.substring(_import.indexOf(".")) : ""

                if (field != null)
                    replacements.put(/\(\(${ref}(|<.*>)\) *(|\(.+?\) *)*([a-zA-Z][a-zA-Z0-9]+)\)\.${Pattern.quote(method)}\(\)/,
                                     /pl.olafcio.avoid_impl.Reflect.get(${target}.class, "${field}", ${imports.getOrDefault(_fir, _fir) + _sub}.class, $3)/)
                else if (invoke != null) {
                    var args = line.split("\\(")[1].split("\\)")[0].trim().split(",")
                                          .findAll { !it.isEmpty() }
                                          .collect { it.substring(0, it.lastIndexOf(" ")) }
                                          .collect { it.split("<")[0].trim() + ".class" }
                                          .join(", ")

                    if (_fir != 'void')
                        replacements.put(/\(\(${ref}(|<.*>)\) *(|\(.+?\) *)*([a-zA-Z][a-zA-Z0-9]+)\)\.${Pattern.quote(method)}\(/,
                                         /((${imports.getOrDefault(_fir, _fir) + _sub}) pl.olafcio.avoid_impl.Reflect.callx(${target}.class, "${invoke}", $3, new java.lang.Class<?>[]{$args}${args.isEmpty() ? '' : ', '}/)
                    else
                        replacements.put(/\(\(${ref}(|<.*>)\) *(|\(.+?\) *)*([a-zA-Z][a-zA-Z0-9]+)\)\.${Pattern.quote(method)}\(/,
                                         /pl.olafcio.avoid_impl.Reflect.callx(${target}.class, "${invoke}", $3, new java.lang.Class<?>[]{$args}${args.isEmpty() ? '' : ', '}/)
                }
            } else if (line.startsWith("@Accessor(")) {
                field = line.substring(11).split('"')[0]
            } else if (line.startsWith("@Invoker(")) {
                invoke = line.substring(10).split('"')[0]
            } else if (line.startsWith("@Mixin(")) {
                target = line.substring(7).split('\\.class')[0]
            } else if (line.startsWith("import ")) {
                var nosemi = line.substring(0, line.length() - 1)
                                        .substring(7)

                imports[nosemi.split("\\.").last()] = nosemi
            }
        }
    }

    @Override
    void transform(Path sub) {
        var data = Files.readString(sub, StandardCharsets.UTF_8)

        for (var entry : replacements.entrySet())
            data = data.replaceAll(entry.getKey(), entry.getValue());

        Files.writeString(sub, data, StandardCharsets.UTF_8)
    }
}
