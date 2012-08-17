package com.code4reference.gradle;

import org.junit.Test
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import static org.junit.Assert.*

class Code4ReferenceTaskTest {
    @Test
    public void canAddTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        def task = project.task('c4rtakstest', type: Code4ReferenceTask)
        assertTrue(task instanceof Code4ReferenceTask)
    }
}
