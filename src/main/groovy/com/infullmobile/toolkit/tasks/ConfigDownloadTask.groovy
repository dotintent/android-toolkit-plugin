package com.infullmobile.toolkit.tasks

import org.apache.commons.lang3.StringUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by Adam Kobus on 09.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class ConfigDownloadTask extends DefaultTask {

    def String outputFilePath = null;
    def String downloadURL = null;

    @TaskAction
    def download() {
        if (StringUtils.isEmpty(outputFilePath)) {
            throw new IllegalStateException("outputFilePath must not be empty");
        }
        if (StringUtils.isEmpty(downloadURL)) {
            throw new IllegalStateException("downloadURL must not be empty")
        }
        File outputFile = new File(outputFilePath);
        File outputFileDir = outputFile.parentFile
        if (!outputFileDir.exists()) {
            project.mkdir(outputFileDir.absolutePath);
        }
        println ">> Downloading configuration fom ${downloadURL}"
        outputFile.withOutputStream { OutputStream os ->
            new URL("${downloadURL}").withInputStream { InputStream is ->
                os << is
            }
        }
    }
}
