package com.code4reference.gradle;

import org.gradle.api.*;

//Extension class for nested argumetns
class C4RNestedPluginExtention {
     String receiver = "Admin"
     String email = "admin@code4reference.com"

 }
//For keeping passing arguments from gradle script.
class Code4ReferencePluginExtension {
    String message = 'Hello from Code4Reference'
    String sender = 'Code4Reference'
    C4RNestedPluginExtention nested = new C4RNestedPluginExtention()
}
class Code4ReferencePlugin implements Plugin<Project> {
    def void apply(Project project) {
           project.extensions.create("c4rArgs", Code4ReferencePluginExtension)
           project.c4rArgs.extensions.create("nestedArgs",C4RNestedPluginExtention)
           project.task('c4rTask', type: Code4ReferenceTask)
    }

  
}


