package com.code4reference.gradle;

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class Code4ReferenceTask extends DefaultTask {
    
    @TaskAction
    def showMessage() {
        println "${project.c4rArguments.sender} : ${project.c4rArguments.message}"
    }
}
