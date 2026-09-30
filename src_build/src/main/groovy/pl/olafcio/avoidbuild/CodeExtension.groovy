package pl.olafcio.avoidbuild

import groovy.transform.PackageScope
import org.gradle.api.Project
import pl.olafcio.avoidbuild.generation.IGenerator
import pl.olafcio.avoidbuild.generation.ITransformer
import pl.olafcio.avoidbuild.generation.annotations.AsTask
import pl.olafcio.avoidbuild.generation.impl.*

class CodeExtension {
    @PackageScope
    static Project project

    @PackageScope def generators   = new ArrayList<IGenerator>()
    @PackageScope def transformers = new ArrayList<ITransformer>()

    def apply(Map map) {
        if ('generator' in map) {
            for (var k in map.keySet())
                if (k != 'generator')
                    throw new RuntimeException("Invalid apply(generator: ..., [!]) parameter: '${k}'")

            if (map['generator'] !instanceof IGenerator)
                throw new RuntimeException("Invalid apply(generator: [!]) provided: '${map['generator']}'")

            generators.add((IGenerator) map['generator'])
        } else if ('transformer' in map) {
            for (var k in map.keySet())
                if (k != 'transformer')
                    throw new RuntimeException("Invalid apply(transformer: ..., [!]) parameter: '${k}'")

            if (map['transformer'] !instanceof ITransformer)
                throw new RuntimeException("Invalid apply(transformer: [!]) provided: '${map['transformer']}'")

            var transformer = (ITransformer) map['transformer']
            if (transformer.class.isAnnotationPresent(AsTask.class)) {
                var name = transformer.class.getAnnotation(AsTask.class)
                                                   .value()

                project.tasks.register(name) {
                    it.doFirst {
                        new MainPlugin.Transformer([transformer]).run(project)
                    }
                }

                project.tasks.compileJava.dependsOn name

                return
            }

            transformers.add(transformer)
        } else {
            for (var k in map.keySet())
                throw new RuntimeException("Invalid apply([!]) parameter: '${k}'")

            throw new RuntimeException("Called apply() with no parameters")
        }
    }

    def replaceEnvAnnot(String onlyIn, String dist) {
        apply transformer: new EnvProcessing(project, onlyIn, dist)
    }

    def reflectionizeMixinAccessors() {
        apply transformer: new MixinToReflect(project)
    }

    def entityTypes() {
        apply generator: new Generation(project)
    }
}
