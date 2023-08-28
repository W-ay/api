package com.way.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.way.dubbointerface.common.BaseResponse;
import com.way.dubbointerface.model.entity.InterfaceInfo;
import com.way.dubbointerface.model.entity.UserInterfaceInfo;
import com.way.project.annotation.AuthCheck;
import com.way.project.common.ResultUtils;
import com.way.project.mapper.UserInterfaceInfoMapper;
import com.way.project.model.vo.InterfaceInfoVO;
import com.way.project.service.InterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Way
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {
    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;
    @Resource
    private InterfaceInfoService interfaceInfoService;

    @GetMapping("/top/interface/invoke")
//    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listTopInterfaceInvoke(){
        List<InterfaceInfoVO> interfaceInfoVOS = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        List<Long> idList = interfaceInfoVOS.stream().map(interfaceInfoVO -> {
            return interfaceInfoVO.getId();
        }).collect(Collectors.toList());
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",idList);
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
        interfaceInfoVOS.stream().forEach(item ->{
            for (InterfaceInfo interfaceInfo : interfaceInfoList) {
                if (interfaceInfo.getId().equals(item.getId())){
                    BeanUtils.copyProperties(interfaceInfo,item);
                }
            }
        });
        return ResultUtils.success(interfaceInfoVOS);
    }
}
