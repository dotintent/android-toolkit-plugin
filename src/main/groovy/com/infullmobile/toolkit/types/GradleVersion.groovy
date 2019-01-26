package com.infullmobile.toolkit.types

import org.gradle.api.Project


class GradleVersion {

    private GradleVersion() {

    }

    static boolean isVersionGTE(Project project, String version) {
        final VersionComparator versionComparator = new VersionComparator(project.gradle.gradleVersion)
        return versionComparator.gte(version)
    }
}
