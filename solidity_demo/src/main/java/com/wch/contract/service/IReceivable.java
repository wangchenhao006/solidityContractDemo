package com.wch.contract.service;


import com.wch.contract.response.BaseResult;

/**
 * 合约方法接口
 * @author wangchenhao
 */
public interface IReceivable {

    /**
     * 创建账户
     * @param companyName
     * @param companySocialcreditCode
     * @param companyContactAddress
     *
     * @param accoutType
     * @return
     */
    BaseResult createAccountInvoke(String companyName, String companySocialcreditCode, String companyContactAddress,  int accoutType) throws Exception;

    /**
     * 签发入账凭证
     * @return
     */
    BaseResult createReceivable(String userId, String toId, String parValue, int validUntil) throws Exception;

    /**
     * 转让入账凭证
     * @return
     */
    BaseResult transferReceivable(String userId, String toId, String receivableNum) throws Exception;

    /**
     * 查询当前用户的账款列表
     * @param userId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    BaseResult receivableList(String userId, Integer pageNumber, Integer pageSize) throws Exception;

    /**
     * 查询账单详情
     * @param userId
     * @param receivableNum
     * @return
     */
    BaseResult receivableInfo(String userId, String receivableNum);

    boolean receivableInit(String userId) throws Exception;

    BaseResult contractUpdate(String tokenUserId) throws Exception;
}
