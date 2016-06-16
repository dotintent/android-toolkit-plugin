package com.infullmobile.toolkit.utils

/**
 * Created by Adam Kobus on 08.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
public enum TaskGroup {
    PREPARATION,
    VERIFICATION,
    REPORT,
    BUILD;

    String getGroupName() {
        return this.name().toLowerCase(Locale.US);
    }
}
