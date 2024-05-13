package com.ds.aix.common.result;

/**
 * @author ds
 * @see org.springframework.http.HttpStatus
 */
public enum ResultEnum {

    /**
     * 业务处理成功
     */
    OK(ResultCode.SUCCESS, ResultMsg.SUCCESS_MSG),

    /**
     * 业务处理失败
     */
    FAIL(ResultCode.FAIL, ResultMsg.FAIL_MSG),

    /**
     * 服务器发生了未知错误
     */
    INTERNAL_SERVER_ERROR(500, "系统繁忙，请稍后重试!");

    /**
     * code
     */
    private final int code;

    /**
     * 消息
     */
    private final String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
