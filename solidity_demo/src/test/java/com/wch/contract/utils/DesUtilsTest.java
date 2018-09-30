package com.wch.contract.utils;

import cn.hyperchain.sdk.exception.PwdException;
import cn.hyperchain.sdk.rpc.HyperchainAPI;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DesUtilsTest {


    @Test
    public void encryptSM4() throws UnsupportedEncodingException, GeneralSecurityException, PwdException {
        List<String> list = HyperchainUtils.getAddrAndPriKeyEncryptSM4("764DEBF4D5F7811744B00162ECC3E23234C38B35");
        System.out.println("--------------------------");
        System.out.println(list);
        String accountJson = HyperchainAPI.newAccountSM2("123");
        JSONObject jsonObject = JSON.parseObject(accountJson);
        List<String> list1 = new ArrayList<>(2);
        list1.add(jsonObject.getString("address"));
//      对已经加密的私钥用国密4再次加密
        list1.add(accountJson);
        System.out.println("--------------------------");
        System.out.println(list1);
    }

    @Test
    public void decryptSM4() {
    }
}