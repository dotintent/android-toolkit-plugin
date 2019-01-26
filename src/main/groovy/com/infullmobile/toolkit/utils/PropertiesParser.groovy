package com.infullmobile.toolkit.utils
/**
 * Created by Adam Kobus on 10.06.2016.
 * Copyright (c) 2016, inFullMobile
 * License: MIT, file: /LICENSE
 */
final class PropertiesParser {

    private PropertiesParser() {

    }

    static def applyProperties(Properties properties, Object targetObject) {
        Set<String> propertyKeys = properties.stringPropertyNames()
        for (String key : propertyKeys) {
            MetaProperty property = targetObject.metaClass.properties.find() { field ->
                field.name.equals(key)
            }
            if (property != null) {
                Class propertyType = property.type
                if (propertyType == String) {
                    targetObject[key] = properties.getProperty(key)
                } else if (propertyType == Integer || propertyType == int) {
                    targetObject[key] = Integer.parseInt(properties.getProperty(key))
                } else if (propertyType == Boolean || propertyType == boolean) {
                    targetObject[key] = Boolean.parseBoolean(properties.getProperty(key))
                } else {
                    throw new IllegalStateException("Unsupported property type ${propertyType} for key ${key}")
                }
            }
        }
    }
}
