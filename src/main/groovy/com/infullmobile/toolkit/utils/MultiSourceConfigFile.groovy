package com.infullmobile.toolkit.utils

import com.infullmobile.toolkit.tasks.ConfigDownloadTask
import com.infullmobile.toolkit.types.IConfigCommand
import org.apache.commons.lang3.StringUtils
import org.gradle.api.Nullable
import org.gradle.api.Project
import org.gradle.api.UnknownTaskException

/**
 * Created by Adam Kobus on 09.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class MultiSourceConfigFile {

    def String filePath = null;
    def String downloadURL = null;
    def final String configName;

    def MultiSourceConfigFile(String configName) {
        this.configName = configName;
    }

    def loadFromProperties(Properties properties) {
        def String configPathKey = "${configName}FilePath"
        def String configURLKey = "${configName}URL"
        filePath = properties.getProperty(configPathKey, filePath)
        downloadURL = properties.getProperty(configURLKey, downloadURL)
    }

    def createDownloadTaskIfNeeded(IConfigCommand command) {
        if (!shouldUseRemoteConfig(command)) {
            return;
        }
        File outputFile = getTempFile(command)
        def downloadTask;
        def String downloadTaskName = this.getDownloadTaskName(command);
        try {
            command.configuredProject.tasks[downloadTaskName];
        } catch (UnknownTaskException ignored) {
            downloadTask = command.configuredProject.task(downloadTaskName, type: ConfigDownloadTask) {
                description "Downloads configuration from url ${downloadURL}"
                group TaskGroup.PREPARATION.groupName
                outputFilePath outputFile.absolutePath
                it.downloadURL this.downloadURL
            }
            command.configuredProject.tasks["check"].dependsOn downloadTask
            downloadTask.shouldRunAfter "clean"
        }
        return outputFile
    }

    def boolean shouldUseRemoteConfig(IConfigCommand command) {
        File configFile = getFileInProject(command.configuredProject, filePath);
        if (configFile != null) {
            return false;
        }
        return !StringUtils.isEmpty(downloadURL);
    }

    @Nullable
    def File obtainConfigFile(IConfigCommand command) {
        File configFile = getFileInProject(command.configuredProject, filePath);
        if(configFile != null && configFile.exists()) {
            return configFile;
        }
        if (!StringUtils.isEmpty(downloadURL)) {
            return getTempFile(command);
        }
        return null;
    }

    static def File getFileInProject(Project project, String relativePath) {
        if (project == null) {
            throw new NullPointerException("Project must not be null");
        }
        if (relativePath == null) {
            return null;
        }
        File file = new File(project.projectDir, relativePath);
        if (!file.exists()) {
            file = new File(project.rootDir, relativePath);
            if (!file.exists()) {
                file = null;
            }
        }
        return file;
    }

    def File getTempFile(IConfigCommand command) {
        return new File(command.config.tempDir, configName);
    }

    def String getDownloadTaskName(IConfigCommand command) {
        return "download${configName.capitalize()}${command.configuredProject.name.capitalize()}"
    }

    def addTaskDependencies(IConfigCommand command, targetTask) {
        if (shouldUseRemoteConfig(command)) {
            targetTask.dependsOn "${getDownloadTaskName(command)}"
        }
    }
}
