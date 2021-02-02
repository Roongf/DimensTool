package com.rf.gradle.plugin.dimenstool


import com.rf.lib.gradle.plugin.dimenstool.CreateDimensTask
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class DimensToolTask extends DefaultTask {
    @Input
    DimensToolExtension extension() {
        return project.extensions.getByType(DimensToolExtension.class)
    }

    @TaskAction
    void createDimensTask() {
        def dexExtension = extension()
        CreateDimensTask.execute(dexExtension.configFilePath)
    }
}