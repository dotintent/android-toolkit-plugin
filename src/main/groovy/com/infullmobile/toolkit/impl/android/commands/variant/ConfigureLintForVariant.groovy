package com.infullmobile.toolkit.impl.android.commands.variant

import com.infullmobile.toolkit.impl.android.IVariantConfigCommand
import com.infullmobile.toolkit.impl.android.VariantConfigurator
import com.infullmobile.toolkit.types.IProjectConfigurator
import com.infullmobile.toolkit.utils.TaskGroup
import org.gradle.api.tasks.Copy

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class ConfigureLintForVariant extends IVariantConfigCommand {

    @Override
    boolean isCommandAllowed(IProjectConfigurator configurator) {
        return config.configureLint
    }

    @Override
    protected performCommandWith(VariantConfigurator variantConfigurator) {
        String lintTaskName = "lint${variantWrapper.fullName.capitalize()}"
        variantWrapper.baseTask.dependsOn "${lintTaskName}"

        configuredProject.task("copyLintResults${variantWrapper.fullName.capitalize()}", type: Copy) {
            group TaskGroup.REPORT.groupName
            from getLintReportsDir()
            into "${config.lintReportDir.path}"
            include "*lint*${variantWrapper.fullName}**/**"
            variantWrapper.baseTask.finalizedBy "${name}"
            configuredProject.tasks[lintTaskName].finalizedBy "${name}"
        }
        config.lintConfig.addTaskDependencies(this, variantWrapper.baseTask)
    }

    private String getLintReportsDir() {
        return "${configuredProject.buildDir}/reports"
    }
}
