package com.infullmobile.toolkit.impl.android

import com.infullmobile.toolkit.utils.TaskGroup
import org.gradle.api.Project
import org.gradle.api.UnknownTaskException

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class VariantWrapper {
    def variant
    def baseTask;
    def connectedTask;
    def Project project;
    def testVariants = []

    VariantWrapper(Project project, variant) {
        this.variant = variant
        this.project = project;
        this.baseTask = project.task("check${fullName.capitalize()}") {
            group TaskGroup.VERIFICATION.groupName;
        }
        this.connectedTask = project.task("check${fullName.capitalize()}Connected",
                dependsOn: this.baseTask) {
            group TaskGroup.VERIFICATION.groupName;
        }
        if (variant.unitTestVariant) {
            testVariants.add(new TestVariantWrapper(project, variant.unitTestVariant));
        }
        if (variant.testVariant) {
            testVariants.add(new TestVariantWrapper(project, variant.testVariant));
        }
        def groupName = variant.variantData.variantConfiguration.buildType.name
        def String groupTaskName = "checkBuildType${groupName.capitalize()}"
        def groupTaskDef
        try {
            groupTaskDef = project.tasks[groupTaskName];
        } catch (UnknownTaskException ignored) {
            groupTaskDef = project.task(groupTaskName) {
                group TaskGroup.VERIFICATION.groupName;
                project.tasks.check.finalizedBy it
            }
        }
        if (groupTaskDef) {
            groupTaskDef.dependsOn this.baseTask
        }
        def String connectedGroupTaskName = "checkBuildType${groupName.capitalize()}Connected"
        def connectedGroupTaskDef
        try {
            connectedGroupTaskDef = project.tasks[connectedGroupTaskName];
        } catch (UnknownTaskException ignored) {
            connectedGroupTaskDef = project.task(connectedGroupTaskName) {
                group TaskGroup.VERIFICATION.groupName;
                project.tasks.connectedCheck.finalizedBy it
            }
        }
        if (connectedGroupTaskDef) {
            connectedGroupTaskDef.dependsOn this.connectedTask
        }
    }

    def String getFullName() {
        return "${this.variant.variantData.variantConfiguration.fullName}"
    }

    def getFlavorName() {
        def String ret = this.variant.variantData.variantConfiguration.flavorName
        if (ret != null && ret.length() > 0) {
            return ret;
        }
        return "";
    }

    def String getDirName() {
        return "${this.variant.variantData.variantConfiguration.dirName}"
    }

    def getSourceDirs() {
        return AndroidUtils.getSourcesFromVariantData(variant.variantData)
    }
}
