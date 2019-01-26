package com.infullmobile.toolkit.utils

import com.infullmobile.toolkit.ToolkitConfiguration
import org.apache.commons.lang3.text.StrSubstitutor
import org.gradle.api.Project

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class VersionFormatter {

    public static final String VERSION_PROPERTIES_FILE = "version.properties"
    public static final String TOKEN_GIT_COMMIT_INDEX = 'gitCommitIndex'
    public static final String TOKEN_VERSION_MAJOR = 'versionMajor'
    public static final String TOKEN_VERSION_MINOR = 'versionMinor'
    public static final String TOKEN_VERSION_ITERATION = 'versionIteration'
    public static final String TOKEN_SUFFIX = 'versionSuffix'

    public static final String TOKEN_VERSION_NAME = 'versionName'
    public static final String TOKEN_VERSION_CODE = 'versionCode'

    private static final String[] CONFIGURABLE_TOKENS = [
            TOKEN_VERSION_MAJOR,
            TOKEN_VERSION_ITERATION,
            TOKEN_VERSION_MINOR,
            TOKEN_SUFFIX
    ]

    private static final String DEFAULT_VERSION_TOKEN_VALUE = ""

    static String getVersionWithConfiguration(ToolkitConfiguration configuration, Project project) {
        String format = configuration.versionFormat
        Map<String, String> tokens = createTokens()

        Properties properties = loadPropertiesForProject(project)
        if (properties != null && properties.containsKey(TOKEN_VERSION_NAME)) {
            return properties[TOKEN_VERSION_NAME]
        }
        fillInTokensFromPropertiesAndArgs(project, tokens, properties)

        StrSubstitutor substitutor = new StrSubstitutor(tokens)
        return substitutor.replace(format)
    }

    static Map<String, String> createTokens() {
        HashMap<String, String> ret = new HashMap<>()
        ret.put(TOKEN_GIT_COMMIT_INDEX, getCommitCount())
        return ret
    }

    static int getVersionCodeWithConfiguration(ToolkitConfiguration configuration, Project project) {
        Properties properties = loadPropertiesForProject(project)
        String versionString
        if (properties != null && properties.containsKey(TOKEN_VERSION_CODE)) {
            versionString = properties[TOKEN_VERSION_CODE]
        } else {
            versionString = getCommitCount()
        }
        if (versionString == null || versionString.isEmpty()) {
            versionString = "0"
        }
        return Integer.parseInt(versionString)
    }

    static Properties loadPropertiesForProject(Project project) {
        Properties properties = getVersionPropertiesForProject(project)
        if (properties == null) {
            properties = getVersionPropertiesForProject(project.rootProject)
        }
        return properties
    }

    static Properties getVersionPropertiesForProject(Project project) {
        if (project != null) {
            File propertiesFile = new File(project.projectDir, VERSION_PROPERTIES_FILE)
            if (propertiesFile.exists()) {
                Properties properties = new Properties()
                properties.load(new FileInputStream(propertiesFile))
                return properties
            }
        }
        return null
    }

    static def fillInTokensFromPropertiesAndArgs(Project project, Map<String, String> tokens, Properties properties) {
        CONFIGURABLE_TOKENS.each { token ->
            String tokenValue
            if (project.hasProperty(token)) {
                tokenValue = project[token]
            } else if (properties != null && properties.containsKey(token)) {
                tokenValue = properties[token]
            } else {
                tokenValue = DEFAULT_VERSION_TOKEN_VALUE
            }
            tokens.put(token, tokenValue)
        }
        tokens.put(TOKEN_GIT_COMMIT_INDEX, getCommitCount())
    }

    static String getCommitCount() {
        try {
            def ret = "git rev-list HEAD --count".execute().text.trim()
            Integer.parseInt(ret)
            return ret
        } catch (Exception ignored) {
            return "0"
        }
    }
}
