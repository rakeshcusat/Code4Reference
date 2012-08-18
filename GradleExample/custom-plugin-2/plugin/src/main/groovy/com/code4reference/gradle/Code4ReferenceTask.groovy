package com.code4reference.gradle;

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class Code4ReferenceTask extends DefaultTask {
    
    @TaskAction
    def showMessage() {
        println "------------showMessage-------------------"
        println "From : ${project.c4rArgs.sender},\
                 message : ${project.c4rArgs.message}"
        println "To : ${project.c4rArgs.nestedArgs.receiver},\
                 email : ${project.c4rArgs.nestedArgs.email}"
        
    }
}
