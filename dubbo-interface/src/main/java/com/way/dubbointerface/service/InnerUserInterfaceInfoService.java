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
    /**
     * 检验剩余调用次数 > 0
     *
     * @param id     接口id
     * @param userid 用户id
     * @return 剩余次数 >0 ? true : false
     */
    boolean verifyCount(long id, long userid);
}
