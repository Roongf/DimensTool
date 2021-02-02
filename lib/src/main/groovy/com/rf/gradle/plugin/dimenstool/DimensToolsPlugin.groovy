package com.rf.gradle.plugin.dimenstool

import org.gradle.api.Plugin
import org.gradle.api.Project

class DimensToolsPlugin implements Plugin<Project> {

    @Override
    void apply(Project target) {
        target.extensions.create("defineDimensPath", DimensToolExtension)
        target.getTasks().create("aRFDimensTools", DimensToolTask.class)
    }
}