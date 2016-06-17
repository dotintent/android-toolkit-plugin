package com.infullmobile.toolkit.impl.android.commands

import com.infullmobile.toolkit.types.IConfigCommand
import com.infullmobile.toolkit.types.IProjectConfigurator

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
public class ExcludeMetaCommand extends IConfigCommand {

    @Override
    public boolean isCommandAllowed(IProjectConfigurator configurator) {
        return config.configureMisc;
    }

    @Override
    public void performCommand(IProjectConfigurator configurator) {
        configuredProject.configure(configuredProject.android.packagingOptions) {
            exclude 'META-INF/DEPENDENCIES.txt'
            exclude 'META-INF/LICENSE.txt'
            exclude 'META-INF/NOTICE.txt'
            exclude 'META-INF/NOTICE'
            exclude 'META-INF/LICENSE'
            exclude 'META-INF/DEPENDENCIES'
            exclude 'META-INF/notice.txt'
            exclude 'META-INF/license.txt'
            exclude 'META-INF/dependencies.txt'
            exclude 'META-INF/LGPL2.1'
            exclude 'LICENSE.txt'
            exclude 'LICENSE'
        }
    }
}