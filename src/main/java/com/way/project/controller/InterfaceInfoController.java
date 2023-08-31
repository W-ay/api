package com.way.project.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.way.apiclient.client.ApiClient;
import com.way.dubbointerface.common.BaseResponse;
import com.way.dubbointerface.common.BusinessException;
import com.way.dubbointerface.common.ErrorCode;
import com.way.dubbointerface.model.entity.InterfaceInfo;
import com.way.dubbointerface.model.entity.User;
import com.way.project.annotation.AuthCheck;
import com.way.project.common.*;
import com.way.project.constant.CommonConstant;
import com.way.project.constant.UserConstant;
import com.way.project.model.dto.interfaceinfo.InterfaceInfoAddRequest;
import com.way.project.model.dto.interfaceinfo.InterfaceInfoInvokeRequest;
import com.way.project.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.way.project.model.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.way.project.model.enums.InterfaceInfoEnum;
import com.way.project.service.InterfaceInfoService;
import com.way.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户信息接口
 *
 * @author way
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    @Resource
    private ApiClient apiClient;

    /**
     * 创建
     *
     * @param interfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);
        // 校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserid(loginUser.getId());
        boolean result = interfaceInfoService.save(interfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newInterfaceInfoId = interfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserid().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param interfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest,
                                                     HttpServletRequest request) {
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, interfaceInfo);
        // 参数校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, false);
        User user = userService.getLoginUser(request);
        long id = interfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldInterfaceInfo.getUserid().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<InterfaceInfo> getInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        return ResultUtils.success(interfaceInfo);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfo>> listInterfaceInfo(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        if (interfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
        return ResultUtils.success(interfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
//    @AuthCheck(mustRole = "admin")
    @AuthCheck(anyRole = {"admin","user"})
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(InterfaceInfoQueryRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        String description = interfaceInfoQuery.getDescription();
        // description 需支持模糊搜索
        interfaceInfoQuery.setDescription(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        User loginUser = userService.getLoginUser(request);
        //普通用户只能查开放的接口
        if (loginUser.getUserRole().equals("user")){
            queryWrapper.eq("status",1);
        }
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceInfoPage);
    }

    /**
     * 上限接口
     *
     * @param idRequest
     * @param request
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @PostMapping("/online")
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest, HttpServletRequest request) {
        //参数校验
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = idRequest.getId();
        //判断接口是否存在
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        //判断接口是否可以调用
//        com.way.apiclient.model.User user = new com.way.apiclient.model.User();
//        user.setUsername("张接口");
//        String byPost = apiClient.getNameByPost(user);
//        if (StringUtils.isBlank(byPost)) {
//            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "接口调用失败");
//        }


        //修改状态字段为1
        InterfaceInfo interfaceInfo1 = new InterfaceInfo();
        interfaceInfo1.setId(id);
        interfaceInfo1.setStatus(InterfaceInfoEnum.ONLINE.getValue());
        boolean update = interfaceInfoService.updateById(interfaceInfo1);
        return ResultUtils.success(update);

    }

    /**
     * 下线接口
     *
     * @param idRequest
     * @param request
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @PostMapping("/offline")
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest, HttpServletRequest request) {
        //参数校验
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = idRequest.getId();
        //判断接口是否存在
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        //修改状态字段为1
        InterfaceInfo interfaceInfo1 = new InterfaceInfo();
        interfaceInfo1.setId(id);
        interfaceInfo1.setStatus(InterfaceInfoEnum.OFFLINE.getValue());
        boolean update = interfaceInfoService.updateById(interfaceInfo1);
        return ResultUtils.success(update);
    }

    /**
     * 在线调用接口
     *
     * @param interfaceInfoInvokeRequest
     * @param request
     * @return
     */
    @AuthCheck(anyRole = {"admin","user"})
    @PostMapping("/invoke")
    public BaseResponse invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest, HttpServletRequest request, HttpServletResponse response) {
        //参数校验
        if (interfaceInfoInvokeRequest == null || interfaceInfoInvokeRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = interfaceInfoInvokeRequest.getId();
        //判断接口是否存在
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        //接口是否开启
        if (interfaceInfo.getStatus() == InterfaceInfoEnum.OFFLINE.getValue()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口未开启");
        }
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        //TODO 根据地址调用接口调用接口
        //TODO 存储client

        ApiClient client = new ApiClient(accessKey, secretKey);
        String resp = client.invokeInterface(interfaceInfo.getUrl(), interfaceInfoInvokeRequest.getUserRequestParams(), interfaceInfo.getMethod());
//        Gson gson = new Gson();
//        com.way.apiclient.model.User user = gson.fromJson(interfaceIanfoInvokeRequest.getUserRequestParams(), com.way.apiclient.model.User.class);
//        String resp = tempClient.getNameByPost(user);
        BaseResponse baseResponse = JSON.parseObject(resp, BaseResponse.class);
        if (baseResponse==null){
            return ResultUtils.error(ErrorCode.CONNECTION_ERROR,resp);
        }else if (baseResponse.getCode()!=0){
            return ResultUtils.error(ErrorCode.OPERATION_ERROR,resp);
        }
        return ResultUtils.success(resp);
    }

}
