package com.infullmobile.toolkit.impl.android

import com.infullmobile.toolkit.impl.android.commands.ConfigureCoverageCommand
import com.infullmobile.toolkit.impl.android.commands.ConfigureFindbugsCommand
import com.infullmobile.toolkit.impl.android.commands.ConfigureLintCommand
import com.infullmobile.toolkit.impl.android.commands.ConfigureToolsVersionCommand
import com.infullmobile.toolkit.impl.android.commands.ConfigureVersionCodeCommand
import com.infullmobile.toolkit.impl.android.commands.ConfigureVersionNameCommand
import com.infullmobile.toolkit.types.IProjectConfigurator
import com.infullmobile.toolkit.impl.android.commands.ConfigureCheckstyleCommand
import com.infullmobile.toolkit.impl.android.commands.ConfigureProguardCommand
import com.infullmobile.toolkit.impl.android.commands.ExcludeMetaCommand
import com.infullmobile.toolkit.impl.android.commands.JavaVersionSetupCommand

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
*/
public final class AndroidUtils {

    private AndroidUtils() {

    }

    public static getSourcesFromVariantData(variantData) {
        return variantData.javaSources.find {
            !it.dir.path.contains("generated");
        }
    }

    public static void addCommonAndroidCommands(IProjectConfigurator configurator) {
        configurator.addCommands(
                new ConfigureVersionNameCommand(),
                new ConfigureVersionCodeCommand(),
                new ConfigureFindbugsCommand(),
                new ConfigureToolsVersionCommand(),
                new ConfigureLintCommand(),
                new JavaVersionSetupCommand(),
                new ExcludeMetaCommand(),
                new ConfigureProguardCommand(),
                new ConfigureCheckstyleCommand(),
                new ConfigureCoverageCommand(),
        );
    }

    public static collectVariants(variants) {
        return variants.all
    }
}