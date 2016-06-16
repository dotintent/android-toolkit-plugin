package com.infullmobile.toolkit.impl.android.commands

import com.infullmobile.toolkit.types.IConfigCommand
import com.infullmobile.toolkit.types.IProjectConfigurator

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class JavaJackSetupCommand extends IConfigCommand {

    @Override
    boolean isCommandAllowed(IProjectConfigurator configurator) {
        return true;
    }

    @Override
    void performCommand(IProjectConfigurator configurator) {
        configuredProject.configure(configuredProject.android.defaultConfig) {
            jackOptions {
                enabled config.useJava8
            }
        }
    }
}
