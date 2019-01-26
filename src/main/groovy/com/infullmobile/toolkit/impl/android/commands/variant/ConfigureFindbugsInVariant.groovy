package com.infullmobile.toolkit.impl.android.commands.variant

import com.infullmobile.toolkit.impl.android.IVariantConfigCommand
import com.infullmobile.toolkit.impl.android.TestVariantWrapper
import com.infullmobile.toolkit.impl.android.VariantConfigurator
import com.infullmobile.toolkit.types.IProjectConfigurator
import com.infullmobile.toolkit.utils.TaskGroup
import org.gradle.api.plugins.quality.FindBugs

/**
 * Created by Adam Kobus on 09.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class ConfigureFindbugsInVariant extends IVariantConfigCommand {

    @Override
    boolean isCommandAllowed(IProjectConfigurator configurator) {
        return config.configureFindbugs && !config.useJava8
    }

    @Override
    protected performCommandWith(VariantConfigurator variantConfigurator) {
        configuredProject.task("findbugs${variantWrapper.fullName.capitalize()}",
                type: FindBugs,
                dependsOn: "assemble${variantWrapper.fullName.capitalize()}") {

            classes = variantConfigurator.configuredProject.files(
                    "${configuredProject.buildDir}/intermediates/classes/${variantWrapper.dirName}")
            source = variantConfigurator.variantWrapper.variant.javaCompile.source
            classpath = variantConfigurator.variantWrapper.variant.javaCompile.classpath

            configureFindbugsTask(it, variantConfigurator)
        }

        if (config.runQAToolsInTests) {
            configureTestVariants(variantConfigurator)
        }
    }

    def configureTestVariants(VariantConfigurator variantConfigurator) {
        variantConfigurator.variantWrapper.testVariants.each { TestVariantWrapper testVariant ->
            configuredProject.task("findbugs${variantWrapper.fullName.capitalize()}${testVariant.typeName.capitalize()}",
                    type: FindBugs,
                    dependsOn: "assemble${variantWrapper.fullName.capitalize()}${testVariant.typeName.capitalize()}") {
                classes = variantConfigurator.configuredProject.files(
                        "${configuredProject.buildDir}/intermediates/classes/" +
                                "${testVariant.testClassesPath}/${variantWrapper.dirName}")
                source = testVariant.variant.javaCompile.source
                classpath = testVariant.variant.javaCompile.classpath

                configureFindbugsTask(it, variantConfigurator)
            }
        }
    }

    def configureFindbugsTask(task, VariantConfigurator variantConfigurator) {
        File excludedFilesConfig = config.findbugsExcludedFilesConfig.obtainConfigFile(this)
        task.group TaskGroup.VERIFICATION.groupName
        task.ignoreFailures = variantConfigurator.config.ignoreFindbugsFailures
        task.reportLevel = "low"
        task.effort = "max"
        if (excludedFilesConfig) {
            task.excludeFilter = excludedFilesConfig
        }

        task.reports {
            xml.enabled variantConfigurator.config.findbugsUseXmlReports
            html.enabled !xml.enabled
            xml.withMessages = true
        }
        variantWrapper.baseTask.dependsOn task
        variantConfigurator.config.findbugsExcludedFilesConfig.addTaskDependencies(this, task)
    }
}
