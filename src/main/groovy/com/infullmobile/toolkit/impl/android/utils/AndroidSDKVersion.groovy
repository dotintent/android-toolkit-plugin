package com.infullmobile.toolkit.impl.android.utils

import com.infullmobile.toolkit.types.VersionComparator

import java.lang.reflect.Field


class AndroidSDKVersion {

    private final VersionComparator versionComparator

    private AndroidSDKVersion() {
        def andVersion = Class.forName("com.android.builder.Version")
        Field versionField = andVersion.declaredFields.find { field ->
            field.name == "ANDROID_GRADLE_PLUGIN_VERSION"
        }
        if (versionField == null) {
            throw new IllegalStateException("Failed to resolve Android Gradle Plugin version")
        }
        String version = versionField.get(andVersion)
        this.versionComparator = new VersionComparator(version)
    }

    private static AndroidSDKVersion INSTANCE

    static boolean isVersionGTE(String version) {
        createInstanceIfNeeded()
        return INSTANCE.versionComparator.gte(version)
    }

    private static void createInstanceIfNeeded() {
        synchronized (AndroidSDKVersion.class) {
            if (INSTANCE == null) {
                INSTANCE = new AndroidSDKVersion()
            }
        }
    }
}
