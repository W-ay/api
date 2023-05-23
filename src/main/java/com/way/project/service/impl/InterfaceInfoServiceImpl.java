package com.way.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.way.dubbointerface.model.entity.InterfaceInfo;
import com.way.project.common.ErrorCode;
import com.way.project.exception.BusinessException;
import com.way.project.service.InterfaceInfoService;
import com.way.project.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author Way
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2023-03-14 21:40:13
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService{

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {

        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();
        String description = interfaceInfo.getDescription();
        String url = interfaceInfo.getUrl();
        String requestHeader = interfaceInfo.getRequestHeader();
        String responseHeader = interfaceInfo.getResponseHeader();
        Integer status = interfaceInfo.getStatus();
        String method = interfaceInfo.getMethod();
        Long userid = interfaceInfo.getUserid();

        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(name, description, url, requestHeader, responseHeader) || ObjectUtils.anyNull( method)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
    }
}




