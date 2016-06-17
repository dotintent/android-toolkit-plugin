package com.infullmobile.toolkit.utils

import org.gradle.api.Project

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class PluginsUtils {

    private static final String ANDROID_APP_PLUGIN_ID = "com.android.application"
    private static final String ANDROID_LIBRARY_PLUGIN_ID = "com.android.library"
    private static final String JAVA_LIBRARY_PLUGIN_ID = "java"
    private static final String FABRIC_LIBRARY_PLUGIN_ID = "io.fabric"
    public static final String CHECKSTYLE_PLUGIN_ID = "checkstyle"
    public static final String JACOCO_PLUGIN_ID = "jacoco"
    public static final String FINDBUGS_PLUGIN_ID = "findbugs"

    static boolean isAndroidApplication(Project project) {
        return hasPlugin(project, ANDROID_APP_PLUGIN_ID);
    }

    static boolean isAndroidLibrary(Project project) {
        return hasPlugin(project, ANDROID_LIBRARY_PLUGIN_ID);
    }

    static boolean isJavaProject(Project project) {
        return hasPlugin(project, JAVA_LIBRARY_PLUGIN_ID);
    }

    static boolean hasFabricPlugin(Project project) {
        return hasPlugin(project, FABRIC_LIBRARY_PLUGIN_ID);
    }

    static boolean hasCheckstylePlugin(Project project) {
        return hasPlugin(project, CHECKSTYLE_PLUGIN_ID);
    }

    static boolean hasJacocoPlugin(Project project) {
        return hasPlugin(project, JACOCO_PLUGIN_ID);
    }

    static boolean hasFindbugsPlugin(Project project) {
        return hasPlugin(project, FINDBUGS_PLUGIN_ID);
    }

    static boolean hasPlugin(Project project, String pluginName) {
        return project.getPlugins().hasPlugin(pluginName);
    }
}
