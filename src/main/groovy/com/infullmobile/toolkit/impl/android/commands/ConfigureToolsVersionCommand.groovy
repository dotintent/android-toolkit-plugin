package com.infullmobile.toolkit.impl.android.commands

import com.infullmobile.toolkit.types.IConfigCommand
import com.infullmobile.toolkit.types.IProjectConfigurator

/**
 * Created by Adam Kobus on 09.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class ConfigureToolsVersionCommand extends IConfigCommand {

    @Override
    public boolean isCommandAllowed(IProjectConfigurator configurator) {
        return true;
    }

    @Override
    public void performCommand(IProjectConfigurator configurator) {
        configuredProject.configure(configuredProject.android) {
            if(config.compileSdkVersion) {
                compileSdkVersion config.compileSdkVersion
            }
            if(config.buildToolsVersion) {
                buildToolsVersion config.buildToolsVersion
            }
        }
        configuredProject.configure(configuredProject.android.defaultConfig) {
            if(config.minSdkVersion) {
                minSdkVersion config.minSdkVersion
            }
            if(config.targetSdkVersion) {
                targetSdkVersion config.targetSdkVersion
            }
        }
    }
}