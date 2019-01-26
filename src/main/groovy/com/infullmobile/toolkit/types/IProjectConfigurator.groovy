package com.infullmobile.toolkit.types

import com.infullmobile.toolkit.ToolkitConfiguration
import org.gradle.api.Project

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
abstract class IProjectConfigurator {

    ToolkitConfiguration config
    Project configuredProject

    private List<IConfigCommand> commands = new ArrayList<>()

    IProjectConfigurator() {

    }

    final void addCommands(IConfigCommand... commands) {
        commands.each {
            this.addCommand(it)
        }
    }

    protected final void addCommand(IConfigCommand command) {
        commands.add(command)
    }

    void configureProject() {
        commands.each { command ->
            command.configuredProject = configuredProject
            command.config = config
            if (command.isCommandAllowed(this)) {
                command.performCommand(this)
            }
        }
    }
}
