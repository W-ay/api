package com.way.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.way.dubbointerface.common.BaseResponse;
import com.way.dubbointerface.common.BusinessException;
import com.way.dubbointerface.common.ErrorCode;
import com.way.dubbointerface.common.ResultUtils;
import com.way.dubbointerface.model.entity.User;
import com.way.dubbointerface.model.entity.UserInterfaceInfo;
import com.way.project.annotation.AuthCheck;
import com.way.project.constant.CommonConstant;
import com.way.project.constant.UserConstant;
import com.way.project.model.dto.userinterfaceinfo.UserInterfaceAddRequest;
import com.way.project.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.way.project.model.vo.UserInterfaceInfoVO;
import com.way.project.service.UserInterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/userInterfaceInfo")
public class UserInterfaceInfoController {
    private final UserInterfaceInfoService userInterfaceInfoService;

    public UserInterfaceInfoController(UserInterfaceInfoService userInterfaceInfoService) {
        this.userInterfaceInfoService = userInterfaceInfoService;
    }

    @GetMapping("/list/page")
    public BaseResponse listUserInterfaceInfo(UserInterfaceInfoQueryRequest queryRequest, HttpServletRequest request) {
        if (queryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (ObjectUtils.anyNull(user)) {
            return ResultUtils.error(ErrorCode.NOT_LOGIN_ERROR, "未登录");
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(queryRequest, userInterfaceInfo);
        long current = queryRequest.getCurrent();
        long size = queryRequest.getPageSize();
        String sortField = queryRequest.getSortField();
        String sortOrder = queryRequest.getSortOrder();
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>(userInterfaceInfo);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        queryWrapper.eq("userId", user.getId());
        Page<UserInterfaceInfo> userInterfaceInfoPage = userInterfaceInfoService.page(new Page<>(current, size), queryWrapper);

        List<UserInterfaceInfoVO> userInterfaceInfoVOS = userInterfaceInfoService.listInterfaceInfoByUserId(user.getId(), current, size);
        return ResultUtils.success(userInterfaceInfoVOS);
    }

    @PostMapping("/open")
    public BaseResponse openInterface(@RequestBody UserInterfaceAddRequest request) {
        Long interfaceInfoId = request.getInterfaceInfoId();
        Long userId = request.getUserId();
        if (ObjectUtils.anyNull(interfaceInfoId, userId)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "请输入正确的参数");
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

    @AuthCheck(anyRole = {"admin","user"})
    @PostMapping("/increase")
    public BaseResponse addCount(@RequestBody UserInterfaceAddRequest request) {
        boolean b = userInterfaceInfoService.addCount(request);
        return ResultUtils.success(b);
    }
}
