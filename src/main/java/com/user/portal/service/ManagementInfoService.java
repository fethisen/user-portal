package com.user.portal.service;

import io.quarkus.runtime.configuration.ProfileManager;
import com.user.portal.config.ToggInfo;
import com.user.portal.service.dto.ManagementInfoDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
* Provides information for management/info resource
*/
@ApplicationScoped
public class ManagementInfoService {

    private final ToggInfo ToggInfo;

    @Inject
    public ManagementInfoService(ToggInfo ToggInfo) {
        this.ToggInfo = ToggInfo;
    }

    public ManagementInfoDTO getManagementInfo(){
        var info = new ManagementInfoDTO();
        if(ToggInfo.isEnable()){
            info.activeProfiles.add("swagger");
        }
        info.activeProfiles.add(ProfileManager.getActiveProfile());
        info.displayRibbonOnProfiles = ProfileManager.getActiveProfile();
        return info;
    }
}

