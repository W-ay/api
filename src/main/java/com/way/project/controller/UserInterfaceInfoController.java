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
import com.way.project.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "用户-接口信息 管理")
public class UserInterfaceInfoController {
    private final UserService userService;
    private final UserInterfaceInfoService userInterfaceInfoService;

    public UserInterfaceInfoController(UserInterfaceInfoService userInterfaceInfoService, UserService userService) {
        this.userInterfaceInfoService = userInterfaceInfoService;
        this.userService = userService;
    }

    @GetMapping("/list/page")
    @ApiOperation("获取用户开通的所有接口信息")
    @ApiImplicitParam(name = "pageSize", value = "页大小", dataType = "String",
            paramType = "query",
            required = true)
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
    @AuthCheck(anyRole = {"admin", "user"})
    @ApiOperation("开通接口")
    public BaseResponse openInterface(@RequestBody@ApiParam("用户开通接口信息") UserInterfaceAddRequest request, HttpServletRequest httpServletRequest) {
        User loginUser = userService.getLoginUser(httpServletRequest);
        Long interfaceInfoId = request.getInterfaceInfoId();
        Long userId = loginUser.getId();
        if (ObjectUtils.anyNull(interfaceInfoId)) {
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

    @AuthCheck(anyRole = {"admin", "user"})
    @PostMapping("/increase")
    public BaseResponse addCount(@RequestBody UserInterfaceAddRequest request) {
        boolean b = userInterfaceInfoService.addCount(request);
        return ResultUtils.success(b);
    }
}
