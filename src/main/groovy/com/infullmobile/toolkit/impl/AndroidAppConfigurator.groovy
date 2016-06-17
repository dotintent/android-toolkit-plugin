package com.infullmobile.toolkit.impl

import com.infullmobile.toolkit.impl.android.AndroidUtils
import com.infullmobile.toolkit.impl.android.VariantConfigurator
import com.infullmobile.toolkit.impl.android.commands.JavaJackSetupCommand
import com.infullmobile.toolkit.impl.android.commands.variant.AddBuildTasksCommand
import com.infullmobile.toolkit.impl.android.VariantWrapper
import com.infullmobile.toolkit.impl.android.commands.ConfigureFabricCommand
import com.infullmobile.toolkit.impl.android.commands.CreateKeyStoreExtCommand
import com.infullmobile.toolkit.impl.android.commands.variant.AppendVersionNameCommand
import com.infullmobile.toolkit.types.IProjectConfigurator

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class AndroidAppConfigurator extends IProjectConfigurator {

    public AndroidAppConfigurator() {
        AndroidUtils.addCommonAndroidCommands(this);

        addCommands(
                new JavaJackSetupCommand(),
                new CreateKeyStoreExtCommand(),
                new ConfigureFabricCommand(),
        )
    }

    @Override
    void configureProject() {
        super.configureProject()
        def variants = configuredProject.android.applicationVariants
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
                new AppendVersionNameCommand(),
                new AddBuildTasksCommand(),
        )
    }
}
