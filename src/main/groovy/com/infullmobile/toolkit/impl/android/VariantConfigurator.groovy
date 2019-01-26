package com.infullmobile.toolkit.impl.android

import com.infullmobile.toolkit.impl.android.commands.variant.*
import com.infullmobile.toolkit.types.IProjectConfigurator

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class VariantConfigurator extends IProjectConfigurator {

    public VariantWrapper variantWrapper

    VariantConfigurator(VariantWrapper variantWrapper) {
        this.variantWrapper = variantWrapper
        addCommands(
                new ConfigureLintForVariant(),
                new ConfigureCheckstyleForVariant(),
                new ConfigureFindbugsInVariant(),
                new CopyTestResultsCommand(),
                new ConfigureTestsForVariant()
        )
    }

    @Override
    void configureProject() {
        super.configureProject()
    }
}
