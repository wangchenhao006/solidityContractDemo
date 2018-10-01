package com.wch.contract.constant;

import java.util.regex.Pattern;

/**
 * 项目中要用到的正则表达式汇总
 *
 * @author chenyufeng
 * @date 2018/4/25
 */
public class RegexConsts {
    /**
     * 最常规的金额校验，可以匹配正整数，小数点后两位。也就是大于等于0的正整数和两位小数点的小数，也就是可以传入金额为零。
     * 可匹配的：1，1.0，1.00，0，0.0， 0.00
     * 金额范围[0,999999999.99]    9亿
     */
    public static final String COMMON_MONEY = "^(([1-9]{1}\\d{0,8})|([0]{1}))(\\.(\\d){0,2})?$";

    public static final String COMMON_MONEY_MSG = "金额信息错误";

    /**
     * 额度范围：[0,999999999999.99]   9999亿
     */
    public static final String LIMIT_RANGE = "^(([1-9]{1}\\d{0,11})|([0]{1}))(\\.(\\d){0,2})?$";

    public static final String LIMIT_RANGE_MSG = "额度信息错误";

    /**
     * 对上面的COMMON_MONEY做的优化，不能传入金额为零。
     * 金额范围(0,999999999.99]   9亿
     */
    public static final String NON_ZERO_MONEY = "^(([1-9]{1}\\d{0,8})(\\.(\\d){0,2})?|0(\\.0[1-9]{1})|0(\\.[1-9]{1,2})|(0(\\.[1-9]{1}0?)))$";

    public static final String NON_ZERO_MONEY_MSG = "金额信息错误";

    /**
     * 不能传入特殊字符，只能传入数字、字母的组合，在一些银单模糊搜索时可以使用。
     */
    public static final String NUMBER_LETTER_NON_SPECIAL_CHAR = "^([0-9]|[a-z]|[A-Z])*$";

    /**
     * 银单编号
     */
    public static final String BILL_CODE = "^([0-9]|[a-z]|[A-Z])*$";

    public static final String BILL_CODE_MSG = "银单编号信息错误";

    /**
     * 不能传入特殊字符，只能传入数字、字母、汉字的组合，在一些企业模糊搜索时可以使用。
     */
    public static final String NUMBER_LETTER_HANZI_NON_SPECIAL_CHAR = "^([0-9]|[a-z]|[A-Z]|[\\u4e00-\\u9fa5])*$";

    /**
     * 上传文件名，不包括后缀长度为30字符。
     */
    public static final String OSS_ORIGIN_FILENAME_LENGTH = "^([0-9]|[a-z]|[A-Z]|[\\u4e00-\\u9fa5]){1,30}$";

    /**
     * 时间戳校验，只能输入13位时间戳。
     */
    public static final String TIMESTAMP_LENGTH_CHECK = "^(\\-{0,1}\\d{1,13})$";

    public static final String TIMESTAMP_LENGTH_CHECK_LIKE = "^(\\-{0,1}\\d{0,13})$";

    public static final String TIMESTAMP_LENGTH_CHECK_MSG = "时间信息错误";

    /**
     * 用于备注的校验，匹配除换行符以外的任意字符，长度[0,100]
     */
    public static final String REMARK_ANY_CHAR = "^(.{0,100})$";

    /**
     * 各种利息的费率 [0,100],最多保留两位小数
     */
    public static final String INTEREST_FEE_RATE = "^(([1-9]{1}\\d{0,1})|(100)|([0]{1}))(\\.(\\d){0,2})?$";

    public static final String INTEREST_FEE_RATE_MSG = "费率信息错误";

    /**
     * 企业名称    长度[1,50]
     */
    public static final String COMPANY_NAME = "^([0-9]|[a-z]|[A-Z]|[\\u4e00-\\u9fa5]|\\(|\\)|（|）){1,50}$";

    public static final String COMPANY_NAME_MSG = "企业名称信息错误";

    /**
     * 企业名称模糊查询    长度[1,50]
     */
    public static final String COMPANY_NAME_Like = "^([0-9]|[a-z]|[A-Z]|[\\u4e00-\\u9fa5]|\\(|\\)|（|）){0,50}$";

    /**
     * 身份证校验
     */
    public static final String ID_CARD = "^[1-9]\\d{7}((0[1-9])|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0[1-9])|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";

    public static final String ID_CARD_MSG = "身份证信息错误";

    /***
     * 人员姓名 1-20 位
     */
    public static final String USER_REAL_NAME = "^([\\w|[\\u4e00-\\u9fa5]]){1,20}$";

    public static final String USER_REAL_NAME_MSG = "人员名称信息错误";
    /**
     * 手机号
     */
    public static final String PHONE = "^1[1-9][0-9]{9}$";

    public static final String PHONE_MSG = "手机号信息错误";


    /**
     * 短信验证码    纯数字
     */
    public static final String SMS_VALIDATED = "^(\\d)+$";

    public static final String SMS_VALIDATED_MSG = "短信验证码格式错误";

    /**
     * 银行卡号     12-19位数字
     */
    public static final String BANK_CARD = "^(\\d){12,30}$";

    public static final String BANK_CARD_MSG = "银行卡号信息错误";

    /**
     * 统一社会信用代码     18位数字和字母
     */
    public static final String CREDIT_CODE = "^(\\w){18}$";

    public static final String CREDIT_CODE_MSG = "统一社会信用代码信息错误";

    /**
     * 营业执照号码       15位数字和字母
     */
    public static final String BUSINESS_LICENSE = "^(\\w){15}$";

    public static final String BUSINESS_LICENSE_MSG = "营业执照信息错误";

    /***
     * 组织机构
     */
    public static final String ORG_CODE = "^([\\d|[A-Z]]){8}-[\\d|[A-Z]]$";

    public static final String ORG_CODE_MSG = "组织机构代码信息错误";

    /***
     * 组织规模
     */
    public static final String ORG_SCALE = "^[1-9]\\d{0,6}$";

    public static final String ORG_SCALE_MSG = "规模信息错误";


    /**
     * 邮编   6位数字
     */
    public static final String ZIP_CODE = "^\\d{6}$";

    public static final String ZIP_CODE_MSG = "邮编信息错误";


    /**
     * 地址信息    长度[1,50]
     */
    public static final String ADDRESS = "^([\\s|\\S]{0,50})$";

    public static final String ADDRESS_MSG = "地址信息错误";

    /**
     * 描述    长度[1,300]
     */
    public static final String DESCRIPTION = "^([\\s|\\S]{0,300})$";

    public static final String DESCRIPTION_MSG = "描述信息错误";


    /**
     * 备注    长度[1,100]
     */
    public static final String REMARK = "^([\\s|\\S]{0,100})$";

    public static final String REMARK_MSG = "备注信息过长";

    public static final String INFO_MSG = "填写信息过长";


    /**
     * 经营信息    长度[0,50]
     */
    public static final String BUSINESS_SCOPE = "^([\\s|\\S]{0,50})$";

    public static final String BUSINESS_SCOPE_MSG = "经营信息错误";


    /**
     * 联系电话
     */
    public static final String CMY_PHONE = "^[\\d|-]{7,20}$";

    public static final String CMY_PHONE_MSG = "联系电话信息错误";

    public static final String POSITIVE_INTEGER= "(^[1-9][0-9]*$)|(^$)";
    public static final String POSITIVE_INTEGER_MSG= "非正整数";

    /**
     * 文件名 1-30个字符
     */
    public static final String FILE_NAME = "^.{1,30}$";

    public static final String FILE_NAME_MSG = "文件名应该为1-30个字符";


    /**
     * 校验方法
     *
     * @param regexConstant 正则表达式
     * @param checkString   需要检测的字符串
     * @return Boolean
     */
    public static Boolean validCheck(String regexConstant, String checkString) {
        Pattern pattern = Pattern.compile(regexConstant);
        return pattern.matcher(checkString).matches();
    }

}


















