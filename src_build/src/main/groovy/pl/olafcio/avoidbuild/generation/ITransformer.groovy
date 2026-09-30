package pl.olafcio.avoidbuild.generation

import java.nio.file.Path

interface ITransformer {
    void transform(Path sub)
}