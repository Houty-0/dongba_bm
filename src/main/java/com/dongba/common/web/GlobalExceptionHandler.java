package com.dongba.common.web;

import com.dongba.common.vo.JsonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    //JDK中的自带的日志API
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public JsonResult doHandleRuntimeException(
            RuntimeException e){
        e.printStackTrace();//也可以写日志
        return new JsonResult(e);//封装异常信息
    }
}
