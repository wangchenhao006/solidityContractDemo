package com.wch.contract.response;



import com.wch.contract.constant.CodeEnum;

/**
 * Created by superlee on 2017/11/7.
 * baseResult工程方法
 */
@SuppressWarnings("unchecked")
public final class BaseResultFactory {


    public static BaseResult produceEmptyResult(CodeEnum codeEnum) {
        return new BaseResult(codeEnum);
    }

    public static BaseResult produceEmptyResult(int codeInt, String msg) {
        return new BaseResult(codeInt, msg);
    }

    public static BaseResult produceResult(int codeInt, String msg, Object data) {
        return new BaseResult(codeInt, msg, data);
    }

    public static BaseResult produceResult(CodeEnum codeEnum, Object data) {
        return new BaseResult(codeEnum.getCode(), codeEnum.getMsg(), data);
    }


}
