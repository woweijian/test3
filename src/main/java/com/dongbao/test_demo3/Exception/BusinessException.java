package com.dongbao.test_demo3.Exception;

/**
 * @author : wangjunyue
 * date: 2025/11/5 16:27
 * Description: com.dongbao.test_demo3.Tool
 * project: test_demo3
 */
// 自定义业务异常类
public class BusinessException extends RuntimeException{
      private  Integer code;
        public BusinessException(String message) {
            super(message);
        }

        public BusinessException(Integer code, String message) {
            super(message);
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
}


