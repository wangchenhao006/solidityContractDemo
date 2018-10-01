package com.wch.contract.exception;

import com.hyperchain.exception.BaseException;
import com.wch.contract.constant.CodeEnum;

/**
 * Created by linbo on 2018/9/20.
 */
public class BlockChainException extends BaseException {

    public BlockChainException() {
    }

    public BlockChainException(CodeEnum codeEnum) {
        super(codeEnum.getCode(), codeEnum.getMsg());
    }

    public BlockChainException(int codeInt, String errorMsg) {
        super(codeInt, errorMsg);
    }
}


