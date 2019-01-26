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

    String tempFilesDir = 'build/temp'
    String resultsDir = 'build/results'
    String defaultFabricTeam = "defaultinternal"

    boolean configureBuildTasks = true
    boolean configureFabric = true
    boolean configureCoverage = false
    boolean useJava8 = false
    boolean appendVersionNameToAPK = false

    boolean configureTests = true
    boolean ignoreTestErrors = false

    boolean configureCheckstyle = true
    boolean ignoreCheckstyleFailures = true

    boolean configureLint = true
    boolean ignoreLintErrors = false

    boolean configureFindbugs = true
    boolean ignoreFindbugsFailures = true
    boolean findbugsUseXmlReports = true

    String versionFormat = '${versionMajor}.${versionMinor}.${gitCommitIndex}${versionSuffix}'
    boolean configureVersionName = false
    boolean configureVersionCode = false

    boolean configureMisc = true
    boolean configureCustomFunctions = true

    boolean runQAToolsInTests = false

    // tools version settings
    Integer compileSdkVersion
    String buildToolsVersion
    Integer minSdkVersion
    Integer targetSdkVersion

    final File rootDir
    final File projectDir
    final String projectName

    final MultiSourceConfigFile findbugsExcludedFilesConfig = new MultiSourceConfigFile("findbugsExcludedFilesConfig")
    final MultiSourceConfigFile checkstyleConfig = new MultiSourceConfigFile("checkstyleConfig")
    final MultiSourceConfigFile lintConfig = new MultiSourceConfigFile("lintConfig")

    ToolkitConfiguration(Project project) {
        def propertiesFiles = []
        Project rootProject = project.rootProject
        if (rootProject && rootProject != project) {
            propertiesFiles.add(new File(rootProject.projectDir, PROPERTIES_FILE_NAME))
        }
        propertiesFiles.add(new File(project.projectDir, PROPERTIES_FILE_NAME))
        propertiesFiles.each { File propFile ->
            if (propFile.exists()) {
                loadPropertiesFromFile(propFile)
            }
        }
        rootDir = project.rootDir.absoluteFile
        projectDir = project.projectDir.absoluteFile
        projectName = project.name
    }

    def loadPropertiesFromFile(File file) {
        Properties properties = new Properties()
        properties.load(new FileInputStream(file))

        PropertiesParser.applyProperties(properties, this)
        this.findbugsExcludedFilesConfig.loadFromProperties(properties)
        this.checkstyleConfig.loadFromProperties(properties)
        this.lintConfig.loadFromProperties(properties)
    }

    File getBinaryOutputDir() {
        return new File(getResultDir(), "bin/${projectName}")
    }

    File getLintReportDir() {
        return new File(getResultDir(), "lint/${projectName}")
    }

    File getTestReportDir() {
        return new File(getResultDir(), "test/${projectName}")
    }

    File getCheckstyleReportDir() {
        return new File(getResultDir(), "checkstyle/${projectName}")
    }

    File getFindbugsReportDir() {
        return new File(getResultDir(), "findbugs/${projectName}")
    }

    File getResultDir() {
        return new File(rootDir, resultsDir)
    }

    def getTempDir() {
        return new File(projectDir, tempFilesDir)
    }
}