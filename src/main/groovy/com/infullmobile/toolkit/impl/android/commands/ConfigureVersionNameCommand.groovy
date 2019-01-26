package com.infullmobile.toolkit.impl.android.commands

import com.infullmobile.toolkit.types.IConfigCommand
import com.infullmobile.toolkit.types.IProjectConfigurator
import com.infullmobile.toolkit.utils.VersionFormatter

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class ConfigureVersionNameCommand extends IConfigCommand {

    @Override
    boolean isCommandAllowed(IProjectConfigurator configurator) {
        return config.configureVersionName
    }

    @Override
    void performCommand(IProjectConfigurator configurator) {
        String generatedVersionName = VersionFormatter.getVersionWithConfiguration(config, configuredProject)
        configuredProject.configure(configuredProject.android.defaultConfig) {
            versionName = generatedVersionName
        }
    }
}
