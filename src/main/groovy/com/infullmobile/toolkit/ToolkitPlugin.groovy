package com.infullmobile.toolkit

import com.infullmobile.toolkit.impl.AndroidAppConfigurator
import com.infullmobile.toolkit.impl.AndroidLibraryConfigurator
import com.infullmobile.toolkit.impl.JavaProjectConfigurator
import com.infullmobile.toolkit.types.IProjectConfigurator
import com.infullmobile.toolkit.utils.PluginsUtils
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */

class ToolkitPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        IProjectConfigurator projectConfigurator = null;
        if(PluginsUtils.isAndroidApplication(project)) {
            projectConfigurator = new AndroidAppConfigurator();
        } else if(PluginsUtils.isAndroidLibrary(project)) {
            projectConfigurator = new AndroidLibraryConfigurator();
        } else if(PluginsUtils.isJavaProject(project)) {
            projectConfigurator = new JavaProjectConfigurator();
        }

        if(projectConfigurator == null) {
            throw new IllegalStateException("Failed to configure project ${project} , unknown type");
        }
        projectConfigurator.setConfig(new ToolkitConfiguration(project));
        projectConfigurator.setConfiguredProject(project);
        projectConfigurator.configureProject();
    }
}