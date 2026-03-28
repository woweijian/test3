package com.dongbao.test_demo3.Exception;

import com.dongbao.test_demo3.Exception.BusinessException;
import com.dongbao.test_demo3.Tool.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

/**
 * @author : wangjunyue
 * date: 2025/11/5 16:31
 * Description: com.dongbao.test_demo3.Tool
 * project: test_demo3
 */
// 全局异常处理类

@Slf4j  //@Slf4j 自动为类生成一个日志对象，让你可以直接使用 log 变量来记录日志，无需手动创建。
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ApiResult<ArrayList<Object>> handleValidationException(BusinessException ex){
        log.error("捕获到全局异常，异常信息为：{}",ex.getMessage());
//        return "捕获到全局异常，异常信息为："+ex.getMessage();
        ArrayList<Object> list = new ArrayList<>();
        list.add("错误");
        list.add("error");
        return ApiResult.success(list,"全局异常捕获成功");
    }


}
