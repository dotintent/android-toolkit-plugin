package com.infullmobile.toolkit.impl.android.commands

import com.infullmobile.toolkit.types.IConfigCommand
import com.infullmobile.toolkit.types.IProjectConfigurator
import org.gradle.api.JavaVersion

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class JavaVersionSetupCommand extends IConfigCommand {

    @Override
    boolean isCommandAllowed(IProjectConfigurator configurator) {
        return true
    }

    @Override
    void performCommand(IProjectConfigurator configurator) {
        def javaVersion = config.useJava8 ? JavaVersion.VERSION_1_8 : JavaVersion.VERSION_1_7
        configuredProject.configure(configuredProject.android.compileOptions) {
            sourceCompatibility javaVersion
            targetCompatibility javaVersion
        }
    }
}