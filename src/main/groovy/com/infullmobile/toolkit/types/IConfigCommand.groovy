package com.infullmobile.toolkit.types

import com.infullmobile.toolkit.ToolkitConfiguration
import org.gradle.api.Project

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
abstract class IConfigCommand {

    Project configuredProject
    ToolkitConfiguration config

    abstract boolean isCommandAllowed(IProjectConfigurator configurator);

    abstract void performCommand(IProjectConfigurator configurator);
}
