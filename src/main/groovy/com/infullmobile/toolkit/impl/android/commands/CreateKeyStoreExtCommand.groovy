package com.infullmobile.toolkit.impl.android.commands

import com.infullmobile.toolkit.types.IConfigCommand
import com.infullmobile.toolkit.types.IProjectConfigurator

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
class CreateKeyStoreExtCommand extends IConfigCommand {

    @Override
    boolean isCommandAllowed(IProjectConfigurator configurator) {
        return config.configureCustomFunctions;
    }

    @Override
    void performCommand(IProjectConfigurator configurator) {
        configuredProject.ext.createSigningConfigFromFile = { String defaultPath ->
            def String path = configuredProject.hasProperty('keystorePropPath') ?
                    configuredProject.keystorePropPath : defaultPath;
            println ">> Keystore properties path is set to $path";
            def propFile = new File(path);
            def propDir = propFile.getParentFile().getAbsolutePath();
            def propDirFile = new File(propDir);

            def Properties keyProps = new Properties()
            keyProps.load(new FileInputStream(propFile));
            def String propFileRelativePath = keyProps['key.store'];

            def keystoreFile = new File(propDirFile, propFileRelativePath);
            def ret = {
                keyAlias keyProps['key.alias'];
                storeFile keystoreFile;
                storePassword keyProps['key.store.password'];
                keyPassword keyProps['key.alias.password'];
            }
            return ret;
        }
    }
}
