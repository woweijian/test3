package com.dongbao.test_demo3.controller;

import com.dongbao.test_demo3.Tool.ApiResult;
import com.dongbao.test_demo3.Exception.BusinessException;
import com.dongbao.test_demo3.Exception.ValidatinException;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;




/**
 * @author : wangjunyue
 * date: 2025/11/7 16:13
 * Description: com.dongbao.test_demo3.controller
 * project: test_demo3
 */

@Slf4j
@RestController
public class ExceptionController {
    private static final Logger logger=LoggerFactory.getLogger(ExceptionController.class);

    @PostMapping("/exception/part")
    public String  partExceptionHanlder(@RequestParam String name){

         if("name".equals(name)){
            throw new ValidatinException("参数name不能为name");
         }

         return "je";
    }

    @ExceptionHandler(ValidatinException.class)
    public ApiResult<Object> handleBusinessException(ValidatinException ex){
//        return "捕获到局部异常，异常信息为："+ex.getMessage()+ex.getCode();
           return  ApiResult.error(ex.getCode(),ex.getMessage());
    }


    @PostMapping("/exception/global")
    public void gloalExceptionHandler(@RequestParam String name){
         logger.error("进入全局异常测试方法，参数name为：{}",name);
        if("global".equals(name)){
          throw new BusinessException(400,"参数name不能为global11");
        }

    }



}
