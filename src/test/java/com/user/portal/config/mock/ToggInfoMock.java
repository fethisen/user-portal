package com.user.portal.config.mock;

import com.user.portal.config.ToggInfo;
import io.quarkus.test.Mock;

import javax.enterprise.context.ApplicationScoped;

@Mock
@ApplicationScoped
public class ToggInfoMock implements ToggInfo {

    public static Boolean enable;

    @Override
    public Boolean isEnable() {
        return enable;
    }
}
