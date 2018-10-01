package com.wch.contract.constant;

/**
 * Created by linbo on 2018/7/31.
 */
public enum CodeEnum {

    //通用部分 0-1000
    UNDEFINED(-1, "未定义"),
    SUCCESS(0, "成功"),

    PARAMETER_ERROR(1, "参数校验异常"),

    //通用 [9000-9999]
    UNKNOWN_ABNORMAL(9002, "未知异常"),
    PARAMETER_ANALYSIS_ERROR(9003, "参数解析异常"),
    PARAMETER_SERVER_INVOKE_ERROR(9004, "服务调用异常"),
    BUSINESS_STATE_ERROR(2000, "业务状态异常"),

    //用户 1001-2000
    USER_NOT_EXIST(1001, "用户不存在"),
    USER_EXIST(1002, "用户已存在"),

    //账单 2001-2800
    TYPE_NOT_EXSIT(2001, "类型不存在"),
    RECEIPT_OWNER_ERROR(2002, "仓单所属企业异常"),
    RECEIVABLE_NOT_EXSIT(2003, "应收账单不存在"),
    RECEIVABLE_BUSINESS_KEEP_ON(2004,"账单处于业务中，不能操作"),
    VALID_TIME_ERROR(2005,"账单过期"),
    //审批驳回
    BUSINESS_STATUS_REJECT(2600, "仓单业务状态为驳回，不允许操作"),
    BUSINESS_STATUS_PASS(2601, "仓单业务状态为通过，不允许操作"),

    //账户 3001-4000
    DELIVERY_NOT_BELONG_COMPANY(3001, "企业信息有误"),
    TRANSACTION_PASSWORD_ERROR(3002, "交易密码错误"),
    OPERATION_CODE_ERROR(3004, "交易操作码错误"),
    TRANSACTION_PASS_NULL(3005, "交易密码不存在,请先设置交易密码"),


    /**
     * 区块链相关 2800-2999
     */
    BLOCK_NUM_NOT_EXSIT(2800, "区块号不存在"),
    TOKEN_NOT_ENOUGH(2801, "平台方token不足"),
    IS_LAST_BLOCK(2802, "已到达最后一个区块"),
    CONTRACT_INVOKE_FAILED(2803, "合约方法调用失败"),
    BLOCK_NULL(2804, "该区块是空区块"),
    ;

    // loy end
    private int code;
    private String msg;

    // 构造方法
    CodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static String getMsgByCodeInt(int codeInt) {
        for (CodeEnum e : CodeEnum.values()) {
            if (e.getCode() == codeInt) {
                return e.msg;
            }
        }
        throw new IllegalArgumentException("未定义的code码:" + codeInt);
    }

    public static CodeEnum getCodeByCodeInt(int codeInt) {
        for (CodeEnum codeEnum : CodeEnum.values()) {
            if (codeEnum.getCode() == codeInt) {
                return codeEnum;
            }
        }
        throw new IllegalArgumentException("未定义的code码:" + codeInt);
    }
}
