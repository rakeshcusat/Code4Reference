package com.code4reference.gradle;

import org.gradle.api.*;

class C4r implements Plugin<Project> {
    def void apply(Project project) {
        project.task('demo') << {
            println "Hi from Code4Reference plugin!"
        }
    }
}
