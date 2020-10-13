package com.infullmobile.toolkit.utils

import com.infullmobile.toolkit.impl.android.TestVariantWrapper.TestType
import org.gradle.api.Project

class AndroidTestTaskLookup {

    def static findTestTask(Project project, TestType testType, def variantData) {
        if (testType == TestType.CONNECTED) {
            return findConnectedTestTask(project, variantData)
        } else {
            return findUnitTestTask(project, variantData)
        }
    }

    def static findConnectedTestTask(Project project, def variantData) {
        def ret = null
        try {
            ret = project.tasks.findByPath(variantData.connectedTestTask.name)
        } catch (MissingPropertyException ignored) {
        }
        if (ret == null) {
            try {
                ret = project.tasks.findByPath("connected${variantData.variantDependency.name.capitalize()}")
            } catch (MissingPropertyException ignored) {
                for (dependency in variantData.variantDependencies) {
                    ret = project.tasks.findByPath("connected${dependency.name.capitalize()}")
                    if (ret != null) {
                        break
                    }
                }
            }
        }
        return ret
    }

    def static findUnitTestTask(Project project, def variantData) {
        try {
            def ret = project.tasks.findByPath(
                    "test${variantData.variantDependency.name.capitalize()}")
            if (ret == null) {
                ret = project.tasks.findByPath(
                        "test${variantData.variantDependency.name.capitalize()}UnitTest")
            }
            return ret
        } catch (MissingPropertyException ignored) {
            return findUnitTestTaskForGradle650(project, variantData)
        }
    }

    def static findUnitTestTaskForGradle650(Project project, def variantData) {
        def ret = null
        for (dependency in variantData.variantDependencies) {
            ret = project.tasks.findByPath("test${dependency.name.capitalize()}")
            if (ret != null) {
                break
            }
        }
        if (ret == null) {
            for (dependency in variantData.variantDependencies) {
                ret = project.tasks.findByPath("test${dependency.name.capitalize()}UnitTest")
                if (ret != null) {
                    break
                }
            }
        }
        return ret
    }
}
