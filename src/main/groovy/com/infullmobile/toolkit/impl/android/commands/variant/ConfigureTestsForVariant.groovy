package com.infullmobile.toolkit.impl.android.commands.variant

import com.infullmobile.toolkit.impl.android.IVariantConfigCommand
import com.infullmobile.toolkit.impl.android.TestVariantWrapper
import com.infullmobile.toolkit.impl.android.VariantConfigurator
import com.infullmobile.toolkit.types.IProjectConfigurator
import com.infullmobile.toolkit.utils.AndroidTestTaskLookup

/**
 * Created by Adam Kobus on 26.09.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class ConfigureTestsForVariant extends IVariantConfigCommand {

    @Override
    boolean isCommandAllowed(IProjectConfigurator configurator) {
        return config.configureTests
    }

    @Override
    protected performCommandWith(VariantConfigurator variantConfigurator) {
        variantWrapper.testVariants.each { TestVariantWrapper testVariant ->
            def variant = testVariant.variant
            def variantData = variant.variantData
            def task = AndroidTestTaskLookup.findTestTask(configuredProject,
                    testVariant.type, variantData)
            task.ignoreFailures = config.ignoreTestErrors
        }
    }
}
