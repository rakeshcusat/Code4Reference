package com.code4reference.gradle;

import org.gradle.api.*;

class Code4ReferencePlugin implements Plugin<Project> {
    def void apply(Project project) {
        project.task('c4rTask') << {
            println "Hi from Code4Reference plugin!"
        }
    }
}
