package com.infullmobile.toolkit.impl.android.commands

import com.infullmobile.toolkit.types.IConfigCommand
import com.infullmobile.toolkit.types.IProjectConfigurator
import com.infullmobile.toolkit.utils.PluginsUtils

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class ConfigureCheckstyleCommand extends IConfigCommand {

    @Override
    boolean isCommandAllowed(IProjectConfigurator configurator) {
        return config.configureCheckstyle
    }

    @Override
    void performCommand(IProjectConfigurator configurator) {
        if (!PluginsUtils.hasCheckstylePlugin(configuredProject)) {
            configuredProject.apply plugin: PluginsUtils.CHECKSTYLE_PLUGIN_ID
        }
        config.checkstyleConfig.createDownloadTaskIfNeeded(this)
    }
}
