package com.infullmobile.toolkit.impl.android.commands.variant

import com.infullmobile.toolkit.impl.android.IVariantConfigCommand
import com.infullmobile.toolkit.impl.android.TestVariantWrapper
import com.infullmobile.toolkit.impl.android.VariantConfigurator
import com.infullmobile.toolkit.types.IProjectConfigurator

/**
 * Created by Adam Kobus on 26.09.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class ConfigureTestsForVariant extends IVariantConfigCommand {

    @Override
    boolean isCommandAllowed(IProjectConfigurator configurator) {
        return config.configureTests;
    }

    @Override
    protected performCommandWith(VariantConfigurator variantConfigurator) {
        variantWrapper.testVariants.each { TestVariantWrapper testVariant ->
            def variant = testVariant.variant
            def variantData = variant.variantData
            String taskDependency;
            def boolean isConnected = (testVariant.type == TestVariantWrapper.TestType.CONNECTED)

            if (isConnected) {
                taskDependency = variantData.connectedTestTask.name
            } else {
                taskDependency = "test${variantData.variantDependency.name.capitalize()}"
            }
            def task = configuredProject.tasks.findByPath(taskDependency)
            task.ignoreFailures = config.ignoreTestErrors
        }
    }
}
