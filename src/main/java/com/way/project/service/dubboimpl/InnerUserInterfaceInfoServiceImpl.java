package com.way.project.service.dubboimpl;

import com.way.dubbointerface.service.InnerUserInterfaceInfoService;
import com.way.project.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author Way
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId,userId);
    }

    @Override
    public boolean verifyCount(long id, long userid) {
        return userInterfaceInfoService.verifyCount(id,userid);

    }
}
