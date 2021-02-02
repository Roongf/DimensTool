package com.rf.gradle.plugin.dimenstool

class DimensToolExtension {
    String configFilePath

    DimensToolExtension configFile(String path) {
        this.configFilePath = path
        return this
    }
}