package com.infullmobile.toolkit.impl.android

import org.gradle.api.Project

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class TestVariantWrapper {

    enum TestType {
        CONNECTED,
        UNIT;
    }

    def variant
    TestType type
    Project project

    TestVariantWrapper(Project project, variant) {
        this.project = project
        this.variant = variant
        if (variant.variantData.connectedTestTask != null) {
            type = TestType.CONNECTED
        } else {
            type = TestType.UNIT
        }
    }

    def getSourceDirs() {
        return AndroidUtils.getSourcesFromVariantData(this.variant.variantData)
    }

    String getTypeName() {
        if (type == TestType.CONNECTED) {
            return "androidTest"
        } else {
            return "unitTest"
        }
    }

    String getTestClassesPath() {
        if (type == TestType.CONNECTED) {
            return "androidTest"
        } else {
            return "test"
        }
    }
}