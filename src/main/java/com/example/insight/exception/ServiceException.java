package com.example.insight.exception;

import lombok.Getter;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2023/10/30 上午10:02
 * @Version: 1.0
 */
@Getter
public class ServiceException extends RuntimeException{
    private String code;

    public ServiceException(String code, String msg){
        super(msg);
        this.code = code;
    }
}
