package com.way.dubbointerface.service;

/**
 * @author Way
 */
public interface InnerUserInterfaceInfoService {
    /**
     * 接口调用次数+1
     * @param interfaceInfoId 接口id
     * @param userId 调用者id
     * @return bool
     */
    boolean invokeCount(long interfaceInfoId,long userId);
}
