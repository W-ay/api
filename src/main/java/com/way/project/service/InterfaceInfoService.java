package com.way.project.service;

import com.way.project.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.way.project.model.entity.InterfaceInfo;

/**
* @author Way
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-03-14 21:40:13
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    /**
     * 校验
     *
     * @param interfaceInfo
     * @param add 是否为创建校验
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
