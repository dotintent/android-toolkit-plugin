package com.infullmobile.toolkit.impl.android.commands

import com.infullmobile.toolkit.types.IConfigCommand
import com.infullmobile.toolkit.types.IProjectConfigurator
import com.infullmobile.toolkit.utils.PluginsUtils

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class ConfigureFabricCommand extends IConfigCommand {

    @Override
    boolean isCommandAllowed(IProjectConfigurator configurator) {
        return config.configureFabric;
    }

    @Override
    void performCommand(IProjectConfigurator configurator) {
        if (!PluginsUtils.hasFabricPlugin(configuredProject)) {
            return;
        }
        configuredProject.android.buildTypes.all { buildType ->
            def String team = getChosenFabricTeam();
            if (team) {
                buildType.ext.betaDistributionGroupAliases = team
            }
            def String path = getChangelogPath()
            if (path) {
                buildType.ext.betaDistributionReleaseNotesFilePath = path
            }
        }
    }

    def getChosenFabricTeam() {
        return configuredProject.hasProperty('fabricTeam') ? configuredProject.fabricTeam : config.defaultFabricTeam
    }

    def getChangelogPath() {
        return configuredProject.hasProperty('fabricChangelogPath') ? configuredProject.fabricChangelogPath : null
    }
}
