package com.way.project.controller;

import com.way.dubbointerface.common.BaseResponse;
import com.way.dubbointerface.common.BusinessException;
import com.way.dubbointerface.common.ErrorCode;
import com.way.dubbointerface.model.entity.User;
import com.way.project.common.ResultUtils;
import com.way.project.constant.UserConstant;
import com.way.project.model.dto.user.UserInfoRequest;
import com.way.project.model.dto.user.UserLoginRequest;
import com.way.project.model.dto.user.UserRegisterRequest;
import com.way.project.model.vo.UserInfoVO;
import com.way.project.model.vo.UserVO;
import com.way.project.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户接口
 *
 * @author way
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    // region 登录相关

    /**
     * 用户注册
     *
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<UserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ResultUtils.success(userVO);
    }

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @GetMapping("/get/login")
    public BaseResponse<UserVO> getLoginUser(HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ResultUtils.success(userVO);
    }

    // endregion

    @GetMapping("/info")
    public BaseResponse<UserInfoVO> getInfo(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (user == null) {
            return ResultUtils.error(ErrorCode.NOT_LOGIN_ERROR);
        }
        UserInfoVO userInfo = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfo);
        return ResultUtils.success(userInfo);
    }

    @GetMapping("/sk")
    public BaseResponse<String> getSK(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (user == null) {
            return ResultUtils.error(ErrorCode.NOT_LOGIN_ERROR);
        }
        return ResultUtils.success(user.getSecretKey());
    }

    @PostMapping("/change")
    public BaseResponse<String> changeUserInfo(HttpServletRequest request, @RequestBody UserInfoRequest userInfoRequest) {
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (user == null) {
            return ResultUtils.error(ErrorCode.NOT_LOGIN_ERROR);
        }
        Long id = user.getId();
        if (StringUtils.isNotBlank(userInfoRequest.getUserName())) {
            user.setUserName(userInfoRequest.getUserName());
        }
        if (userInfoRequest.getGender() != null) {
            user.setGender(userInfoRequest.getGender());
        }
        userService.updateById(user);

        return ResultUtils.success("success");
    }
}
