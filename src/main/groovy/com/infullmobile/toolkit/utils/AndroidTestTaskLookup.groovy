package com.infullmobile.toolkit.utils

import com.infullmobile.toolkit.impl.android.TestVariantWrapper.TestType
import org.gradle.api.Project

class AndroidTestTaskLookup {

    def static findTestTask(Project project, TestType testType, def variantData) {
        if(testType == TestType.CONNECTED) {
            return findConnectedTestTask(project, variantData)
        } else {
            return findUnitTestTask(project, variantData)
        }
    }

    def static findConnectedTestTask(Project project, def variantData) {
        def ret = null
        try {
            ret = project.tasks.findByPath(variantData.connectedTestTask.name)
        } catch(MissingPropertyException ignored) {}
        if(ret == null) {
            ret = project.tasks.findByPath("connected${variantData.variantDependency.name.capitalize()}")
        }
        return ret
    }

    def static findUnitTestTask(Project project, def variantData) {
        def ret = project.tasks.findByPath(
                "test${variantData.variantDependency.name.capitalize()}")
        if(ret == null) {
            ret = project.tasks.findByPath(
                    "test${variantData.variantDependency.name.capitalize()}UnitTest")
        }
        return ret
    }
}
