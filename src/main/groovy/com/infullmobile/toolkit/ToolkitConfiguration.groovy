package com.infullmobile.toolkit

import com.infullmobile.toolkit.utils.MultiSourceConfigFile
import com.infullmobile.toolkit.utils.PropertiesParser
import org.gradle.api.Project

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class ToolkitConfiguration {

    public static final String PROPERTIES_FILE_NAME = "toolkit.properties"

    def String tempFilesDir = 'build/temp'
    def String resultsDir = 'build/results'
    def String defaultFabricTeam = "defaultinternal"

    def boolean configureBuildTasks = true;
    def boolean configureFabric = true;
    def boolean configureCoverage = false
    def boolean useJava8 = false;
    def boolean appendVersionNameToAPK = false;

    def boolean configureTests = true;
    def boolean ignoreTestErrors = false;

    def boolean configureCheckstyle = true;
    def boolean ignoreCheckstyleFailures = true;

    def boolean configureLint = true;
    def boolean ignoreLintErrors = false;

    def boolean configureFindbugs = true;
    def boolean ignoreFindbugsFailures = true;
    def boolean findbugsUseXmlReports = true

    def String versionFormat = '${versionMajor}.${versionMinor}.${gitCommitIndex}${versionSuffix}';
    def boolean configureVersionName = false;
    def boolean configureVersionCode = false;

    def boolean configureMisc = true;
    def boolean configureCustomFunctions = true;

    boolean runQAToolsInTests = false;

    // tools version settings
    def Integer compileSdkVersion;
    def String buildToolsVersion;
    def Integer minSdkVersion;
    def Integer targetSdkVersion;

    def final File rootDir;
    def final File projectDir;
    def final String projectName;

    def final MultiSourceConfigFile findbugsExcludedFilesConfig = new MultiSourceConfigFile("findbugsExcludedFilesConfig")
    def final MultiSourceConfigFile checkstyleConfig = new MultiSourceConfigFile("checkstyleConfig")
    def final MultiSourceConfigFile lintConfig = new MultiSourceConfigFile("lintConfig")

    ToolkitConfiguration(Project project) {
        def propertiesFiles = [];
        Project rootProject = project.rootProject;
        if (rootProject && rootProject != project) {
            propertiesFiles.add(new File(rootProject.projectDir, PROPERTIES_FILE_NAME));
        }
        propertiesFiles.add(new File(project.projectDir, PROPERTIES_FILE_NAME));
        propertiesFiles.each { File propFile ->
            if (propFile.exists()) {
                loadPropertiesFromFile(propFile);
            }
        }
        rootDir = project.rootDir.absoluteFile
        projectDir = project.projectDir.absoluteFile
        projectName = project.name
    }

    def loadPropertiesFromFile(File file) {
        def Properties properties = new Properties()
        properties.load(new FileInputStream(file));

        PropertiesParser.applyProperties(properties, this);
        this.findbugsExcludedFilesConfig.loadFromProperties(properties);
        this.checkstyleConfig.loadFromProperties(properties);
        this.lintConfig.loadFromProperties(properties);
    }

    def File getBinaryOutputDir() {
        return new File(getResultDir(), "bin/${projectName}");
    }

    def File getLintReportDir() {
        return new File(getResultDir(), "lint/${projectName}");
    }

    def File getTestReportDir() {
        return new File(getResultDir(), "test/${projectName}");
    }

    def File getCheckstyleReportDir() {
        return new File(getResultDir(), "checkstyle/${projectName}");
    }

    def File getFindbugsReportDir() {
        return new File(getResultDir(), "findbugs/${projectName}");
    }

    def File getResultDir() {
        return new File(rootDir, resultsDir);
    }

    def getTempDir() {
        return new File(projectDir, tempFilesDir);
    }
}