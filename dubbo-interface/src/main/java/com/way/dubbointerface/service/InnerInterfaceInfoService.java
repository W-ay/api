package com.way.dubbointerface.service;

import com.way.dubbointerface.model.entity.InterfaceInfo;

/**
 * @author Way
 */
public interface InnerInterfaceInfoService {
    /**
     * 获取接口信息，为null则不存在接口
     * @param url 接口路径
     * @param method 接口方法
     * @return 接口信息
     */
    InterfaceInfo getInterfaceInfo(String url,String method);
}
