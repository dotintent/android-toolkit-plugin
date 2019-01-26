package com.infullmobile.toolkit.impl.android.commands

import com.infullmobile.toolkit.types.IConfigCommand
import com.infullmobile.toolkit.types.IProjectConfigurator
import com.infullmobile.toolkit.utils.PluginsUtils

/**
 * Created by Adam Kobus on 09.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class ConfigureFindbugsCommand extends IConfigCommand {

    @Override
    boolean isCommandAllowed(IProjectConfigurator configurator) {
        return config.configureFindbugs
    }

    @Override
    void performCommand(IProjectConfigurator configurator) {
        if (!PluginsUtils.hasFindbugsPlugin(configuredProject)) {
            configuredProject.apply plugin: PluginsUtils.FINDBUGS_PLUGIN_ID
            configuredProject.findbugs {
                toolVersion = "3.0.1"
                reportsDir = configurator.config.findbugsReportDir
            }
        }
        config.findbugsExcludedFilesConfig.createDownloadTaskIfNeeded(this)
    }
}
