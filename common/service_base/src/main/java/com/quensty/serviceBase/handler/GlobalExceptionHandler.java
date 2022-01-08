package com.quensty.serviceBase.handler;

import com.quensty.commonutils.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 * @author quensty
 * @version 1.0
 * @date 2021/10/29 20:16
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResult errot(Exception exception){
        exception.printStackTrace();
        log.error(exception.getMessage());
        return CommonResult.error().message("执行了全局处理异常");
    }

    /**
     * 特定异常
     * @param exception
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public CommonResult errot(ArithmeticException exception){
        exception.printStackTrace();
        log.error(exception.getMessage());
        return CommonResult.error();
    }
    /**
     * 自定义异常
     * @param exception
     * @return
     */
    @ExceptionHandler(GuLiException.class)
    @ResponseBody
    public CommonResult errot(GuLiException exception){
        exception.printStackTrace();
        log.error(exception.getMessage());
        return CommonResult.error().code(exception.getCode()).message(exception.getMessage());
    }
}
