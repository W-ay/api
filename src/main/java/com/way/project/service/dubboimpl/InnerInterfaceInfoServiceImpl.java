package com.way.project.service.dubboimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.way.dubbointerface.model.entity.InterfaceInfo;
import com.way.dubbointerface.service.InnerInterfaceInfoService;
import com.way.project.service.InterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author Way
 */
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoService interfaceInfoService;
    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url",url);
        queryWrapper.eq("method",method);
        return interfaceInfoService.getOne(queryWrapper);
    }
}
