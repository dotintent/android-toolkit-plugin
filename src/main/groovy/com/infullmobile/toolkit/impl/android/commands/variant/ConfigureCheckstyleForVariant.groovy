package com.infullmobile.toolkit.impl.android.commands.variant

import com.infullmobile.toolkit.impl.android.IVariantConfigCommand
import com.infullmobile.toolkit.impl.android.VariantConfigurator
import com.infullmobile.toolkit.types.IProjectConfigurator
import com.infullmobile.toolkit.utils.TaskGroup
import org.gradle.api.Task
import org.gradle.api.logging.LogLevel
import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.reporting.internal.TaskGeneratedSingleFileReport

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class ConfigureCheckstyleForVariant extends IVariantConfigCommand {

    @Override
    boolean isCommandAllowed(IProjectConfigurator configurator) {
        return config.configureCheckstyle
    }

    private File makeReport(String fileName) {
        return new File(fileName)
    }

    @Override
    protected performCommandWith(VariantConfigurator vC) {
        File checkstyleConfig = config.checkstyleConfig.obtainConfigFile(this)

        configuredProject.task("checkstyle${variantWrapper.fullName.capitalize()}", type: Checkstyle) {
            group TaskGroup.VERIFICATION.groupName
            description "Checkstyle task for ${variantWrapper.fullName}"
            if (checkstyleConfig) {
                configFile checkstyleConfig
            }
            variantWrapper.sourceDirs.each { sourceDir ->
                source sourceDir
            }
            include '**/*.java'
            exclude '**/gen/**'
            classpath = configuredProject.files()
            ignoreFailures vC.config.ignoreCheckstyleFailures

            reports.xml.enabled true
            reports.xml.destination makeReport("${vC.config.checkstyleReportDir}/" +
                    "${variantWrapper.fullName}.xml")
            reports.html.enabled true
            reports.html.destination makeReport("${vC.config.checkstyleReportDir}/${variantWrapper.fullName}.html")

            logging.setLevel(LogLevel.LIFECYCLE)
            vC.config.checkstyleConfig.addTaskDependencies(this, it)
            variantWrapper.baseTask.dependsOn it
        }
        if (vC.config.runQAToolsInTests) {
            addCheckstyleToTestVariants(checkstyleConfig, vC)
        }
    }

    protected addCheckstyleToTestVariants(File checkstyleConfig, VariantConfigurator vC) {
        variantWrapper.testVariants.each { testVariant ->
            configuredProject.task("checkstyle${variantWrapper.fullName.capitalize()}${testVariant.typeName.capitalize()}",
                    type: Checkstyle) {
                group TaskGroup.VERIFICATION.groupName
                description "Checkstyle task for ${testVariant.typeName} - ${variantWrapper.fullName}"
                if (checkstyleConfig) {
                    configFile checkstyleConfig
                }
                testVariant.sourceDirs.each { sourceDir ->
                    source sourceDir
                }
                include '**/*.java'
                exclude '**/gen/**'
                classpath = configuredProject.files()
                ignoreFailures vC.config.ignoreCheckstyleFailures

                reports.xml.enabled true
                reports.xml.destination makeReport("${vC.config.checkstyleReportDir}/" +
                        "${variantWrapper.fullName}-${testVariant.typeName}.xml")
                reports.html.enabled true
                reports.html.destination makeReport("${vC.config.checkstyleReportDir}/" +
                        "${variantWrapper.fullName}-${testVariant.typeName}.html")
                logging.setLevel(LogLevel.LIFECYCLE)
                vC.config.checkstyleConfig.addTaskDependencies(this, it)
                variantWrapper.baseTask.dependsOn it
            }
        }
    }
}
