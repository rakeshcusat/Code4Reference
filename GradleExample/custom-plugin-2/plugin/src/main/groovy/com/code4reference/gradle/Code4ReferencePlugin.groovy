package com.code4reference.gradle;

import org.gradle.api.*;

class Code4ReferencePluginExtension {
    String message = 'Hello from Code4Reference'
    String sender = 'Code4Reference'
}

class Code4ReferencePlugin implements Plugin<Project> {
    def void apply(Project project) {
           project.extensions.create("c4rArguments", Code4ReferencePluginExtension)
           project.task('c4rTask', type: Code4ReferenceTask)
    }

  
}


