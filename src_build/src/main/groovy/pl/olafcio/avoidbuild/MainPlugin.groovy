package pl.olafcio.avoidbuild

import org.gradle.api.Plugin
import org.gradle.api.Project
import pl.olafcio.avoidbuild.scripts.Generation

class MainPlugin implements Plugin<Project> {
    @Override
    void apply(Project target) {
        new Generation(target).init()
    }
}
