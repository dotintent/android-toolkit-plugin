package com.infullmobile.toolkit.impl.android

import com.infullmobile.toolkit.types.IConfigCommand
import com.infullmobile.toolkit.types.IProjectConfigurator

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
abstract class IVariantConfigCommand extends IConfigCommand {

    VariantWrapper variantWrapper

    @Override
    final void performCommand(IProjectConfigurator configurator) {
        if (!configurator instanceof VariantConfigurator) {
            throw new IllegalArgumentException("configurator must be an instance of " +
                    VariantConfigurator.class.getName())
        }
        VariantConfigurator variantConfigurator = (VariantConfigurator) configurator
        this.variantWrapper = variantConfigurator.variantWrapper
        this.performCommandWith(variantConfigurator)
    }

    protected abstract performCommandWith(VariantConfigurator variantConfigurator);
}
