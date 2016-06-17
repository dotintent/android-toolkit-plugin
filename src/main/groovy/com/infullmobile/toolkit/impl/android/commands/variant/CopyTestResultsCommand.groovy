package com.infullmobile.toolkit.impl.android.commands.variant

import com.infullmobile.toolkit.impl.android.IVariantConfigCommand
import com.infullmobile.toolkit.impl.android.TestVariantWrapper
import com.infullmobile.toolkit.impl.android.VariantConfigurator
import com.infullmobile.toolkit.types.IProjectConfigurator
import com.infullmobile.toolkit.utils.TaskGroup
import org.gradle.api.tasks.Copy

/**
 * Created by Adam Kobus on 09.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class CopyTestResultsCommand extends IVariantConfigCommand {

    @Override
    boolean isCommandAllowed(IProjectConfigurator configurator) {
        return config.configureTests;
    }

    @Override
    protected performCommandWith(VariantConfigurator variantConfigurator) {
        variantWrapper.testVariants.each { TestVariantWrapper testVariant ->
            def variant = testVariant.variant
            def variantData = variant.variantData
            def boolean isConnected = (testVariant.type == TestVariantWrapper.TestType.CONNECTED)
            def String taskDependency;
            def targetCheckTask
            def String sourceDir;
            def String targetDir;
            if (isConnected) {
                taskDependency = variantData.connectedTestTask.name
                targetCheckTask = variantWrapper.connectedTask
                sourceDir = "${configuredProject.buildDir}/outputs/androidTest-results"
                targetDir = "${config.testReportDir}"
            } else {
                taskDependency = "test${variantData.variantDependency.name.capitalize()}"
                targetCheckTask = variantWrapper.baseTask
                sourceDir = "${configuredProject.buildDir}/test-results/${variantWrapper.fullName}"
                targetDir = "${config.testReportDir}/unitTest"
            }
            configuredProject.task("copy${taskDependency.capitalize()}Results", type: Copy,
                    dependsOn: "${taskDependency}") {
                group TaskGroup.REPORT.groupName
                from sourceDir
                into targetDir
                targetCheckTask.finalizedBy it
            }
        }
    }
}
