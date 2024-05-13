package com.ds.aix.common.result;

import java.io.Serializable;

/**
 * 通用返回
 *
 * @author ds
 */
public final class Result<T> implements Serializable {

    private static final long serialVersionUID = 6625888347761546266L;

    /**
     * 返回状态码
     *
     * @see ResultCode
     */
    private int code;

    /**
     * 返回消息
     *
     * @see ResultMsg
     */
    private String message;

    /**
     * 是否成功返回
     */
    private boolean success;

    /**
     * 返回数据
     */
    private T data;

    public Result() {
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(int code, String message, T data, boolean success) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = success;
    }

    /**
     * @return 成功返回
     */
    public static <T> Result<T> ok() {
        return new Result<>(ResultCode.SUCCESS, ResultMsg.SUCCESS_MSG, null, true);
    }

    /**
     * @param data 数据
     * @return 带指定数据的成功返回
     */
    public static <T> Result<T> okData(T data) {
        return new Result<>(ResultCode.SUCCESS, ResultMsg.SUCCESS_MSG, data, true);
    }

    /**
     * @param msg 数据
     * @return 带指定消息的成功返回
     */
    public static <T> Result<T> ok(String msg) {
        return new Result<>(ResultCode.SUCCESS, msg, null, true);
    }

    /**
     * @param message 消息
     * @param data    数据
     * @return 带指定消息和数据的成功返回
     */
    public static <T> Result<T> ok(String message, T data) {
        return new Result<>(ResultCode.SUCCESS, message, data, true);
    }

    /**
     * @return 失败返回
     */
    public static <T> Result<T> fail() {
        return new Result<>(ResultCode.FAIL, ResultMsg.FAIL_MSG, null, false);
    }

    /**
     * @param message 数据
     * @return 带指定数据的失败返回
     */
    public static <T> Result<T> fail(String message) {
        return new Result<>(ResultCode.FAIL, message, null, false);
    }

    /**
     * @param message 消息
     * @param data    数据
     * @return 带指定消息和数据的失败返回
     */
    public static <T> Result<T> fail(String message, T data) {
        return new Result<>(ResultCode.FAIL, message, data, false);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

    public static <T> ResultBuilder<T> builder() {
        return new ResultBuilder<>();
    }

    public static final class ResultBuilder<T> {

        private int code;

        private String message;

        private T data;

        private boolean success;

        private ResultBuilder() {
        }

        public ResultBuilder<T> withCode(int code) {
            this.code = code;
            return this;
        }

        public ResultBuilder<T> withMessage(String message) {
            this.message = message;
            return this;
        }

        public ResultBuilder<T> withData(T data) {
            this.data = data;
            return this;
        }

        public ResultBuilder<T> withSuccess(boolean success) {
            this.success = success;
            return this;
        }

        /**
         * Build Result.
         *
         * @see Result
         */
        public Result<T> build() {
            Result<T> result = new Result<>();
            result.setCode(code);
            result.setMessage(message);
            result.setData(data);
            result.setSuccess(success);
            return result;
        }
    }

}
