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
        if(!PluginsUtils.hasFabricPlugin(configuredProject)) {
            return;
        }
        configuredProject.android.buildTypes.each { buildType ->
            buildType.ext.betaDistributionGroupAliases = getFabricTeam()
            def String changelogPath = getChangelogPath()
            if (changelogPath) {
                buildType.ext.betaDistributionReleaseNotesFilePath = changelogPath
            }
        }
    }

    def getFabricTeam() {
        def team = configuredProject.hasProperty('fabricTeam') ? fabricTeam : config.defaultFabricTeam
        println ">> Fabric team is set to $team on project ${configuredProject.name}"
        return team
    }

    def getChangelogPath() {
        def path = configuredProject.hasProperty('fabricChangelogPath') ? changelogPath : null
        println ">> Changelog path is set to $path in project ${configuredProject.name}"
        return path
    }
}
