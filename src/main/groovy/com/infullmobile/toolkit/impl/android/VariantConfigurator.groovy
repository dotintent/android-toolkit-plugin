package com.infullmobile.toolkit.impl.android

import com.infullmobile.toolkit.impl.android.commands.variant.ConfigureCheckstyleForVariant
import com.infullmobile.toolkit.impl.android.commands.variant.ConfigureFindbugsInVariant
import com.infullmobile.toolkit.impl.android.commands.variant.ConfigureLintForVariant
import com.infullmobile.toolkit.impl.android.commands.variant.CopyTestResultsCommand
import com.infullmobile.toolkit.types.IProjectConfigurator

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class VariantConfigurator extends IProjectConfigurator {

    public VariantWrapper variantWrapper

    public VariantConfigurator(VariantWrapper variantWrapper) {
        this.variantWrapper = variantWrapper;
        addCommands(
                new ConfigureLintForVariant(),
                new ConfigureCheckstyleForVariant(),
                new ConfigureFindbugsInVariant(),
                new CopyTestResultsCommand(),
        )
    }

    @Override
    void configureProject() {
        super.configureProject()
    }
}
