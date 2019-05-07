package com.infullmobile.toolkit.impl.android

import com.infullmobile.toolkit.impl.android.commands.*
import com.infullmobile.toolkit.types.IProjectConfigurator

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
final class AndroidUtils {

    private AndroidUtils() {

    }

    static getSourcesFromVariant(variant) {
        def ret = new ArrayList<String>()
        variant.sourceSets.each { sourceSet ->
            if(sourceSet != null) {
                sourceSet.java.source.each { source ->
                    if(source != null) {
                        ret.add(source)
                    }
                }
            }
        }
        return ret
    }

    static void addCommonAndroidCommands(IProjectConfigurator configurator) {
        configurator.addCommands(
                new ConfigureVersionNameCommand(),
                new ConfigureVersionCodeCommand(),
                new ConfigureFindbugsCommand(),
                new ConfigureToolsVersionCommand(),
                new ConfigureLintCommand(),
                new ExcludeMetaCommand(),
                new ConfigureProguardCommand(),
                new ConfigureCheckstyleCommand(),
                new ConfigureCoverageCommand(),
        )
    }

    static collectVariants(variants) {
        return variants.all
    }
}
