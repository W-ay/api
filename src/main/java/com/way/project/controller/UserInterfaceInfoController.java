package com.way.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.way.dubbointerface.common.BaseResponse;
import com.way.dubbointerface.common.ErrorCode;
import com.way.dubbointerface.common.ResultUtils;
import com.way.dubbointerface.model.entity.UserInterfaceInfo;
import com.way.project.model.dto.userinterfaceinfo.UserInterfaceAddRequest;
import com.way.project.service.UserInterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user-interface")
public class UserInterfaceInfoController {
    private final UserInterfaceInfoService userInterfaceInfoService;

    public UserInterfaceInfoController(UserInterfaceInfoService userInterfaceInfoService) {
        this.userInterfaceInfoService = userInterfaceInfoService;
    }

    @PostMapping("/open")
    public BaseResponse openInterface(@RequestBody UserInterfaceAddRequest request) {
        Long interfaceInfoId = request.getInterfaceInfoId();
        Long userId = request.getUserId();
        if (ObjectUtils.anyNull(interfaceInfoId,userId)){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"请输入正确的参数");
        }
        LambdaQueryWrapper<UserInterfaceInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId);
        wrapper.eq(UserInterfaceInfo::getUserId, userId);
        //检测接口是否存在
        UserInterfaceInfo one = userInterfaceInfoService.getOne(wrapper);
        if (one != null) {
            log.info("接口已开通，InterfaceInfoId:{} UserId:{}", interfaceInfoId, userId);
            return ResultUtils.error(ErrorCode.OPERATION_ERROR, "接口已开通");
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        userInterfaceInfo.setInterfaceInfoId(interfaceInfoId);
        userInterfaceInfo.setUserId(userId);
        userInterfaceInfoService.save(userInterfaceInfo);
        return ResultUtils.success("接口开通成功");
    }

    @PostMapping("/increase")
    public BaseResponse addCount(UserInterfaceAddRequest request) {

        return ResultUtils.success(null);
    }
}
