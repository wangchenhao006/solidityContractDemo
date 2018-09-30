package com.wch.contract.constant;

/**
 * Created by linbo on 2018/7/31.
 */
public enum Code {

    //通用部分
    UNDEFINED(-1, "未定义"),
    SUCCESS(0, "成功"),

    PARAMETER_ERROR(1999, "参数校验异常"),

    //通用 [9000-9999]
    UNKNOWN_ABNORMAL(9002, "未知异常"),
    PARAMETER_ANALYSIS_ERROR(9003, "参数解析异常"),
    PARAMETER_SERVER_INVOKE_ERROR(9004, "服务调用异常"),

    COMPANY_ACCOUNT_STATUS_ERROR(1009, "企业认证状态异常"),

    DELIVERY_WAREHOUSE_NOT_EXSIT(1001, "交割仓不存在"),

    TX_RECEIPT_STOCKIN_CHANGE_OWNER(1236, "入库码单所有权转移失败"),
    TYPE_NOT_EXSIT(1237, "类型不存在"),
    TX_RIVALID_ERROR(1238, "交易对手异常"),
    LOCK_LIST_ERROR(1239, "锁定入库码单失败"),
    LIST_ERROR(1240, "入库码单异常"),
    RECEIPT_OWNER_ERROR(1241, "仓单所属企业异常"),
    RECEIPT_TYOE_NOT_EXSIT(1242, "仓单类型不存在"),
    RECEIPT_BUSINESS_NOT_EXSIT(1243, "业务历史不存在"),
    RECEIPT_STATUS_ERROR(1244, "业务状态异常"),
    RECEIPT_NOT_EXSIT(1245, "仓单不存在"),
    CURRENCY_TYPE_NOT_EXSIT(1246, "币种不存在"),
    LIST_IS_EMPTY(1247, "入库码单为空"),
    LIST_LOCKED_NOT_EMPTY(1248, "入库码单锁定量不为空"),
    LIST_FILED_NOT_SAME(1249, "入库码单字段不一致"),
    DELIVERY_NOT_BELONG_COMPANY(1250, "企业信息有误"),

    USER_NOT_EXIST(1254, "用户不存在"),
    USER_EXIST(1255, "用户已存在"),

    WAREHOUSE_AGENCY_FROZEN(1506,"仓储机构处于冻结状态，不能操作"),
    DELIVERY_WAREHOUSE_FROZEN(1513,"交割仓处于冻结状态，不能操作"),


    WAREHOUSE_AGENCY_NOT_EXSIT(1501, "仓储机构不存在"),
    QUALITY_INSPECTION_INS_NOT_EXSIT(1502, "质检机构不存在"),
    POLICY_INSTITUTION_NOT_EXSIT(1503, "承保机构不存在"),
    QUALITY_INSPECTION_INS_FROZEN(1507,"质检机构处于冻结状态，不能编辑"),
    POLICY_INSTITUTION_FROZEN(1508,"承保机构处于冻结状态，不能编辑"),
    UNLOCK_FAILURE(1509,"入库码单解锁失败"),
    RECEIPT_BUSINESS_KEEP_ON(1510,"仓单处于业务中，不能操作"),

    TRANSACTION_PASSWORD_ERROR(1500, "交易密码错误"),
    OPERATION_CODE_ERROR(1499, "交易操作码错误"),
    TRANSACTION_PASS_NULL(1498, "交易密码不存在,请先设置交易密码"),

    //审批驳回
    BUSINESS_STATUS_REJECT(1600, "仓单业务状态为驳回，不允许操作"),
    BUSINESS_STATUS_PASS(1601, "仓单业务状态为通过，不允许操作"),


    /**
     * 融通业务 2000-3000
     */

    BUSINESS_STATE_ERROR(2000, "业务状态异常"),
    ACCESSORYS_NUM_ERROR(2001, "附件个数超过10个"),
    FILE_NAME_ERROR(2002, "提交异常,文件名长度不予超过30"),
    /**
     * 2400-2599 wangchenhao
     */
    VALID_TIME_ERROR(2401,"账单过期"),

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
    Code(int code, String msg) {
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
        for (Code e : Code.values()) {
            if (e.getCode() == codeInt) {
                return e.msg;
            }
        }
        throw new IllegalArgumentException("未定义的code码:" + codeInt);
    }

    public static Code getCodeByCodeInt(int codeInt) {
        for (Code code : Code.values()) {
            if (code.getCode() == codeInt) {
                return code;
            }
        }
        throw new IllegalArgumentException("未定义的code码:" + codeInt);
    }
}
