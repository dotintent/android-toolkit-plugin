package com.infullmobile.toolkit.impl.android.commands.variant

import com.infullmobile.toolkit.impl.android.IVariantConfigCommand
import com.infullmobile.toolkit.impl.android.VariantConfigurator
import com.infullmobile.toolkit.types.IProjectConfigurator

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class AppendVersionNameCommand extends IVariantConfigCommand {

    @Override
    boolean isCommandAllowed(IProjectConfigurator configurator) {
        return config.appendVersionNameToAPK;
    }

    @Override
    protected performCommandWith(VariantConfigurator variantConfigurator) {
        variantWrapper.variant.outputs.each { output ->
            def String versionName = configuredProject.android.defaultConfig.versionName;
            if (output.zipAlign) {
                output.outputFile = getFileWithVersionName(output.outputFile as File, versionName);
            }
            output.packageApplication.outputFile = getFileWithVersionName(
                    output.packageApplication.outputFile as File, versionName);
        }
    }

    static def File getFileWithVersionName(File original, String versionName) {
        def String fileName = original.name
        if (!original.name.contains(versionName) && !original.name.contains("unaligned")) {
            fileName = fileName.replace(".apk", "-${versionName}.apk")
        }
        return new File(original.parent, fileName)
    }
}
