package com.dongbao.test_demo3.Exception;

import com.dongbao.test_demo3.Exception.BusinessException;

/**
 * @author : wangjunyue
 * date: 2025/11/5 16:29
 * Description: com.dongbao.test_demo3.Tool
 * project: test_demo3
 */
// 自定义验证异常类
public class ValidatinException extends BusinessException {

        public ValidatinException(String message) {
            super(401,message);
        }


}
