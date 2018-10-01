package com.wch.contract.response;

import com.wch.contract.constant.CodeEnum;
import lombok.Data;

import java.io.Serializable;

@Data

public final class BaseResult<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    public BaseResult() {}

    public BaseResult(String msg) {
        this();
        this.code = 200;
        this.message = msg;
    }

    public BaseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 与Code码交互
    public BaseResult(CodeEnum codeEnum) {
        this();
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMsg();
    }

    public void success(T object) {
        this.code = CodeEnum.SUCCESS.getCode();
        this.message = CodeEnum.SUCCESS.getMsg();
        this.data = object;
    }

    /**
     * 返回结果代码code和消息msg，不需要返回值
     *
     * @param codeEnum 结果类型
     */
    public final void returnWithoutValue(CodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMsg();
    }

    /**
     * 返回结果代码code和消息msg，并返回值
     *
     * @param codeEnum   结果类型
     * @param object 返回值
     */
    public final void returnWithValue(CodeEnum codeEnum, T object) {
        returnWithoutValue(codeEnum);
        this.data = object;
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

    @Override
    public String toString() {
        return "BaseResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
