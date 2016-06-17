package com.infullmobile.toolkit.impl.android.commands

import com.infullmobile.toolkit.types.IConfigCommand
import com.infullmobile.toolkit.types.IProjectConfigurator
import com.infullmobile.toolkit.utils.VersionFormatter

/**
 * Created by Adam Kobus on 17.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class ConfigureVersionCodeCommand extends IConfigCommand {

    @Override
    boolean isCommandAllowed(IProjectConfigurator configurator) {
        return config.configureVersionCode;
    }

    @Override
    void performCommand(IProjectConfigurator configurator) {
        int generatedVersionCode = VersionFormatter.getVersionCodeWithConfiguration(config, configuredProject);
        configuredProject.configure(configuredProject.android.defaultConfig) {
            versionCode = generatedVersionCode
        }
    }
}
