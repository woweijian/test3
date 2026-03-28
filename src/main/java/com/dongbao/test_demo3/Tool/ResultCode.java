package com.dongbao.test_demo3.Tool;

public enum ResultCode {

    // 成功
    SUCCESS(200, "操作成功"),

    // 客户端错误
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权或token已过期"),
    FORBIDDEN(403, "权限不足"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    REQUEST_TIMEOUT(408, "请求超时"),
    CONFLICT(409, "资源冲突"),
    VALIDATION_FAILED(422, "参数验证失败"),
    TOO_MANY_REQUESTS(429, "请求过于频繁"),

    // 业务错误
    USER_NOT_EXIST(1001, "用户不存在"),
    USER_DISABLED(1002, "用户已被禁用"),
    PASSWORD_ERROR(1003, "密码错误"),
    CAPTCHA_ERROR(1004, "验证码错误"),
    PHONE_EXISTS(1005, "手机号已存在"),
    EMAIL_EXISTS(1006, "邮箱已存在"),

    // 数据错误
    DATA_NOT_FOUND(2001, "数据不存在"),
    DATA_EXISTS(2002, "数据已存在"),
    DATA_INVALID(2003, "数据无效"),
    DATA_DUPLICATE(2004, "数据重复"),

    // 系统错误
    INTERNAL_ERROR(500, "系统内部错误"),
    SERVICE_UNAVAILABLE(503, "服务暂不可用"),
    DATABASE_ERROR(5001, "数据库操作失败"),
    REDIS_ERROR(5002, "缓存服务异常"),
    RPC_ERROR(5003, "远程调用失败"),
    FILE_UPLOAD_ERROR(5004, "文件上传失败"),

    // 第三方服务错误
    THIRD_PARTY_ERROR(6001, "第三方服务异常"),
    SMS_SEND_FAILED(6002, "短信发送失败"),
    PAYMENT_FAILED(6003, "支付失败");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}