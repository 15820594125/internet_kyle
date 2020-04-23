package com.tensquare.base.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 公共异常处理类 hello world!
 */
@ControllerAdvice
public class BaseExceptionHandler {

    /**
     * 异常处理方法
     */
    /*@ExceptionHandler(NullPointerException.class)  // 只捕获空指针异常
    public Result handlerError(){
    }*/

    @ExceptionHandler(Exception.class)  // 捕获所有异常
    @ResponseBody // 注意：这里必须手动返回json格式数据
    public Result handlerError(Exception e){
        return new Result(false, StatusCode.ERROR,"执行失败："+e.getMessage());
    }

}
