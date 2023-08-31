package com.way.project.exception;

import cn.hutool.core.io.IORuntimeException;
import com.alibaba.fastjson.JSONException;
import com.way.dubbointerface.common.BaseResponse;
import com.way.dubbointerface.common.BusinessException;
import com.way.dubbointerface.common.ErrorCode;
import com.way.project.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author way
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("businessException: " + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException: \n", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
//        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public BaseResponse<?> exceptionHandler(Exception e) {
        log.error("runtimeException: \n", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "系统繁忙");
//        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
    }
    @ExceptionHandler(IORuntimeException.class)
    public BaseResponse<?> IORuntimeExceptionHandler(IORuntimeException e) {
        log.error("IORuntimeExceptionHandler: \n", e);
        return ResultUtils.error(ErrorCode.CONNECTION_ERROR, "服务连接失败");
//        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
    }
    @ExceptionHandler(JSONException.class)
    public BaseResponse<?> IORuntimeExceptionHandler(JSONException e) {
        log.error("JSONExceptionHandler: \n", e);
        return ResultUtils.error(ErrorCode.INTERFACE_RESPONSE_ERROR,ErrorCode.INTERFACE_RESPONSE_ERROR.getMessage());
//        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
    }
}
