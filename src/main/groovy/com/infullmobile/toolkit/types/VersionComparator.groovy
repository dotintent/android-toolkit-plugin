package com.infullmobile.toolkit.types

import org.apache.maven.artifact.versioning.DefaultArtifactVersion


class VersionComparator {

    private final DefaultArtifactVersion version;

    public VersionComparator(String version) {
        this.version = new DefaultArtifactVersion(version);
    }

    public boolean gte(String version) {
        DefaultArtifactVersion other = new DefaultArtifactVersion(version);
        return this.version >= other
    }
}
