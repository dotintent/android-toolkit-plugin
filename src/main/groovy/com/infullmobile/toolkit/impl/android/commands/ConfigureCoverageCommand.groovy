package com.infullmobile.toolkit.impl.android.commands

import com.infullmobile.toolkit.types.IConfigCommand
import com.infullmobile.toolkit.types.IProjectConfigurator
import com.infullmobile.toolkit.utils.PluginsUtils

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class ConfigureCoverageCommand extends IConfigCommand {

    @Override
    boolean isCommandAllowed(IProjectConfigurator configurator) {
        return config.configureCoverage;
    }

    @Override
    void performCommand(IProjectConfigurator configurator) {
        configuredProject.android.buildTypes.all() { buildType ->
            buildType.testCoverageEnabled = true
        }
        if(!PluginsUtils.hasJacocoPlugin(configuredProject)) {
            configuredProject.apply plugin: PluginsUtils.JACOCO_PLUGIN_ID
            configuredProject.jacoco {

            }
        }
    }
}
