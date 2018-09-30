package com.wch.contract.utils;

import cn.hyperchain.sdk.rpc.utils.Utils;

import java.io.UnsupportedEncodingException;

/**
 * Created by linbo on 2018/9/6.
 */
public class DesUtils {

    private static final String SERECT_KEY = "1234567890101112";

    /**
     * 交易操作码 加密
     *
     * @return
     */
    public static String encryptSM4(String str) throws UnsupportedEncodingException {
        return Utils.encryptSM4(str.getBytes("utf-8"), SERECT_KEY);
    }

    public static String decryptSM4(String encryptStr) throws UnsupportedEncodingException {
        return new String(Utils.decryptSM4(encryptStr, SERECT_KEY), "utf-8");
    }

}
