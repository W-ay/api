package com.way.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.way.dubbointerface.model.entity.UserInterfaceInfo;

public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 调用接口统计
     *
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);

    /**
     * 检验剩余调用次数 > 0
     *
     * @param id     接口id
     * @param userid 用户id
     * @return 剩余次数 >0 ? true : false
     */
    boolean verifyCount(long interfaceInfoId, long userid);
}
