package com.way.dubbointerface.service;

import com.way.dubbointerface.model.entity.User;

/**
 * @author Way
 */
public interface InnerUserService {
    /**
     *  获取调用者信息
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);
}
