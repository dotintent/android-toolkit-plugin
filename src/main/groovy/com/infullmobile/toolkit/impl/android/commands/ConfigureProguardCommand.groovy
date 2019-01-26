package com.infullmobile.toolkit.impl.android.commands

import com.infullmobile.toolkit.types.IConfigCommand
import com.infullmobile.toolkit.types.IProjectConfigurator

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class ConfigureProguardCommand extends IConfigCommand {

    @Override
    boolean isCommandAllowed(IProjectConfigurator configurator) {
        return config.configureMisc
    }

    @Override
    void performCommand(IProjectConfigurator configurator) {
        configuredProject.android.buildTypes.all { buildType ->
            buildType.proguardFiles configuredProject.android.getDefaultProguardFile('proguard-android.txt'),
                    'proguard-rules.pro'
        }
    }
}
