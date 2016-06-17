package com.infullmobile.toolkit.impl

import com.infullmobile.toolkit.impl.android.AndroidUtils
import com.infullmobile.toolkit.impl.android.VariantConfigurator
import com.infullmobile.toolkit.impl.android.VariantWrapper
import com.infullmobile.toolkit.impl.android.commands.variant.AddBuildTasksCommand
import com.infullmobile.toolkit.impl.android.commands.variant.AppendVersionNameCommand
import com.infullmobile.toolkit.types.IProjectConfigurator

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class AndroidLibraryConfigurator extends IProjectConfigurator {

    public AndroidLibraryConfigurator() {
        AndroidUtils.addCommonAndroidCommands(this);
    }

    @Override
    void configureProject() {
        super.configureProject()
        def variants = configuredProject.android.libraryVariants
        variants.all { variant ->
            configureVariant(variant);
        }
    }

    def configureVariant(variant) {
        VariantConfigurator variantConfigurator = new VariantConfigurator(
                new VariantWrapper(configuredProject, variant));
        variantConfigurator.setConfig(config);
        variantConfigurator.setConfiguredProject(configuredProject);

        addCommandsToVariantConfigurator(variantConfigurator);

        variantConfigurator.configureProject();
    }

    static def addCommandsToVariantConfigurator(VariantConfigurator variantConfigurator) {
        variantConfigurator.addCommands(
                //no library-specific variants at the moment
        )
    }
}
