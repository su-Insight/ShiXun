package com.example.insight.exception;

import com.example.insight.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Insight
 * @Description: 业务异常
 * @Date: 2023/10/30 上午10:01
 * @Version: 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
        return Result.error(se.getCode(), se.getMessage());
    }
}
