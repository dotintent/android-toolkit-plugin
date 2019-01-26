package com.infullmobile.toolkit.impl.android.commands

import com.infullmobile.toolkit.types.IConfigCommand
import com.infullmobile.toolkit.types.IProjectConfigurator

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class ConfigureLintCommand extends IConfigCommand {

    @Override
    boolean isCommandAllowed(IProjectConfigurator configurator) {
        return config.configureLint
    }

    @Override
    void performCommand(IProjectConfigurator configurator) {
        config.lintConfig.createDownloadTaskIfNeeded(this)
        File configFile = config.lintConfig.obtainConfigFile(this)
        configuredProject.configure(configuredProject.android.lintOptions) {
            abortOnError !configurator.config.ignoreLintErrors
            noLines false
            if (configFile) {
                lintConfig configFile
            }
        }
    }
}
