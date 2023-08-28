package com.way.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.way.dubbointerface.model.entity.UserInterfaceInfo;
import com.way.project.model.dto.userinterfaceinfo.UserInterfaceAddRequest;
import com.way.project.model.vo.UserInterfaceInfoVO;

import java.util.List;

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

    /**
     * 获取指定用户已开通的接口信息
     * @param userId 用户id
     * @param current 当前页
     * @param pageSize 页大小
     * @return 接口信息
     */
    List<UserInterfaceInfoVO> listInterfaceInfoByUserId(long userId,long current,long pageSize);

    /**
     * 添加接口次数
     * @param request
     * @return
     */
    boolean addCount(UserInterfaceAddRequest request);
}
