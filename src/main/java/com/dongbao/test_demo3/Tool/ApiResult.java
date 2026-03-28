package com.dongbao.test_demo3.Tool;


import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * API统一返回结果封装
 * @param <T>
 */
@Data
public class ApiResult <T>{
    private  Integer code;
    private  String message;
    private  T data;
    private Long timeStamp;


    public  static<T>  ApiResult<T> success(T data){
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.code=200;
        apiResult.message="success";
        apiResult.data=data;
        apiResult.timeStamp=System.currentTimeMillis();
        return apiResult;
    }

    public  static<T>  ApiResult<T> success(T data,String message){
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.code=(ResultCode.SUCCESS.getCode());
        apiResult.setMessage(ResultCode.SUCCESS.getMessage());
        apiResult.data=data;
        apiResult.timeStamp=System.currentTimeMillis();
        return apiResult;
    }

    public static <T> ApiResult<T> error(Integer code, String message){
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(code);
        apiResult.setMessage(message);
        apiResult.timeStamp=System.currentTimeMillis();
        apiResult.setData((T) Collections.emptyList());
        return apiResult;
    }

    public ApiResult<T> error(String message){
       return error(500,message);
    }

}
