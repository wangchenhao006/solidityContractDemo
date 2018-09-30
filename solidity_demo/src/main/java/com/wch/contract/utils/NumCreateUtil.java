package com.wch.contract.utils;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

/**
 *
 * Created by linbo on 2018/8/6.
 */
public class NumCreateUtil {

    /**
     * 生成仓单编号 17
     * 仓单编号生成规则： 仓单类型（1位）+公司短码（4位）+ 日期（yyyyMMdd） + 4位随机数（字母+数字）
     *
     * @param receiptType
     * @return
     */
    public static String getReceiptNum(String receiptType, String companyShortCode) {
        StringBuilder stringBuilder = new StringBuilder(receiptType);
        stringBuilder.append(companyShortCode);
        stringBuilder.append(LocalDate.now().toString().replaceAll("-", ""));
        stringBuilder.append(getRandomStr("0", 4));
        return stringBuilder.toString();
    }

    public static String getReceivableNum( String companyShortCode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(companyShortCode);
        stringBuilder.append(LocalDate.now().toString().replaceAll("-", ""));
        stringBuilder.append(getRandomStr("0", 4));
        return stringBuilder.toString();
    }

    /**
     * 生成业务流水编号  18
     * 业务类型（2位） + 时间戳 + 8位纯数字，**20180620******** ，yyyyMMdd**
     *
     * @return
     */
    public static String getBusinessNum(String businessType) {
        StringBuilder stringBuilder = new StringBuilder();
        if (businessType.length() == 1) {
            stringBuilder.append("0");
        }
        stringBuilder.append(businessType);
        stringBuilder.append(LocalDate.now().toString().replaceAll("-", ""));
        stringBuilder.append(getRandomStr("1", 8));
        return stringBuilder.toString();
    }

    /**
     * 获取交易操作码 加密
     *
     * @return
     */
    public static String getTxOperationCode() throws UnsupportedEncodingException {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        uuid = uuid.substring(8, 24);
        return DesUtils.encryptSM4(uuid);
    }


    /**
     * 交易操作码解密
     *
     * @return
     */
    public static String getTxOperationCodeecryptSM4(String encryptStr) throws UnsupportedEncodingException {
        return DesUtils.decryptSM4(encryptStr);
    }


    /**
     * @param type   0:字母加数字  1：纯数字
     * @param length
     * @return
     */
    private static String getRandomStr(String type, int length) {
        String baseStrAlphaAndNum = "01234567890abcdefghijklmnopqrstuvwxyz";
        String baseStrNum = "01234567890";
        StringBuilder stringBuilder = new StringBuilder();
        if (type.equals(0)) {
            for (int i = 0; i < length; i++) {
                Random random = new Random();
                int num = random.nextInt(36);
                stringBuilder.append(baseStrAlphaAndNum.charAt(num));
            }
        } else {
            for (int i = 0; i < length; i++) {
                Random random = new Random();
                int num = random.nextInt(10);
                stringBuilder.append(baseStrNum.charAt(num));
            }
        }
        return stringBuilder.toString();
    }

//    public static void main(String[] args) throws UnsupportedEncodingException {
//        System.out.println(DesUtils.decryptSM4("Kb6W+8ZQ3eM9IDwSnr0DXSkoKD1d2MwF0aBCHM8HroA="));
//    }
}
