package com.infullmobile.toolkit.impl.android.commands.variant

import com.infullmobile.toolkit.impl.android.VariantConfigurator
import com.infullmobile.toolkit.impl.android.IVariantConfigCommand
import com.infullmobile.toolkit.types.IProjectConfigurator
import com.infullmobile.toolkit.utils.PluginsUtils
import com.infullmobile.toolkit.utils.TaskGroup
import org.gradle.api.tasks.Copy

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class AddBuildTasksCommand extends IVariantConfigCommand {

    @Override
    boolean isCommandAllowed(IProjectConfigurator configurator) {
        return config.configureBuildTasks
    }

    @Override
    protected performCommandWith(VariantConfigurator variantConfigurator) {
        def String buildTaskName = "build${variantWrapper.fullName.capitalize()}";
        configuredProject.task("${buildTaskName}",
                type: Copy,
                dependsOn: "assemble${variantWrapper.fullName.capitalize()}") {
            group TaskGroup.BUILD.groupName
            from "${configuredProject.buildDir}/outputs/apk/"
            into "${config.binaryOutputDir}/"
            exclude '**/*-unaligned*.apk'
        }
        if (config.configureFabric && PluginsUtils.hasFabricPlugin(configuredProject)) {
            configuredProject.task("${buildTaskName}AndUpload", dependsOn: "${buildTaskName}") {
                group TaskGroup.BUILD.groupName
                finalizedBy "crashlyticsUploadDistribution${variantWrapper.fullName.capitalize()}"
            }
        }
    }
}
