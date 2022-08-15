package com.user.portal.config;

import io.quarkus.arc.config.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;


@ConfigProperties(prefix = "togg.info")
public interface ToggInfo {

    @ConfigProperty(name = "swagger.enable", defaultValue = "true")
    Boolean isEnable();

}
