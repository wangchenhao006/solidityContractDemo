package com.wch.contract.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hyperchain.ESDKUtil;
import com.wch.contract.constant.CodeEnum;
import com.wch.contract.constant.HyperchainConsts;
import com.wch.contract.entity.Receivable;
import com.wch.contract.entity.User;
import com.wch.contract.mapper.ReceivableMapper;
import com.wch.contract.mapper.UserMapper;
import com.wch.contract.response.BaseResultFactory;

import com.wch.contract.service.IReceivable;
import com.wch.contract.utils.DateUtil;
import com.wch.contract.utils.DesUtils;
import com.wch.contract.utils.HyperchainUtils;
import com.wch.contract.utils.NumCreateUtil;
import com.wch.contract.response.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ReceivableImpl implements IReceivable {
    String pwd = "123";
    String contractAddr = null;
    String contractName = "Receivable";
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ReceivableMapper receivableMapper;
    /**
     * @param companyName
     * @param companySocialcreditCode
     * @param companyContactAddress
     * @param accoutType
     * @return
     * @throws Exception
     */
    @Override
    public BaseResult createAccountInvoke(String companyName, String companySocialcreditCode, String companyContactAddress, int accoutType) throws Exception {
        BaseResult baseResult = new BaseResult(CodeEnum.SUCCESS);

        List<String> list1 = HyperchainUtils.getAddrAndPriKeyEncryptSM4(companySocialcreditCode);
        String bcAddress = list1.get(0);
        String privateKey = list1.get(1);

        //插入数据库User，保存私钥
        User user =new User();
        user.setCompanySocialcreditCode(companySocialcreditCode);
        EntityWrapper ew=new EntityWrapper(user);
        List<User> users = userMapper.selectList(ew);
        if (users != null && users.size() > 0) {
            baseResult.setCode(CodeEnum.USER_EXIST.getCode());
            baseResult.setMessage(CodeEnum.USER_EXIST.getMsg());
            return baseResult;
        }

        List<Object> list = ReceivableContractInvoke.createAccountInvoke(companyName,companySocialcreditCode,companyContactAddress,bcAddress,accoutType);
        if (list != null && list.size() > 0) {
            if ("000000".equals(list.get(0))) {
                user.setCompanyName(companyName);
                user.setCompanyBcAddr(bcAddress);
                user.setCompanyContactAddress(companyContactAddress);
                user.setPrivateKey(privateKey);
                user.insert();
                return baseResult;
            }
            if ("000002".equals(list.get(0))) {
                baseResult.setCode(CodeEnum.PARAMETER_SERVER_INVOKE_ERROR.getCode());
                baseResult.setMessage(CodeEnum.PARAMETER_SERVER_INVOKE_ERROR.getMsg());
                return baseResult;
            }
        }
        return baseResult;
    }

    /**
     * 添加应收账单
     * @param userId
     * @param toId
     * @param parValue
     * @param validUntil
     * @return
     */
    @Override
    public BaseResult createReceivable(String userId, String toId, String parValue, int validUntil) throws Exception {
        //todo 检查账户余额是否足够
        //if (parValue > )
        //创建账单

        User user = userMapper.selectById(userId);
        User toUser = userMapper.selectById(toId);

        String receivableNum = NumCreateUtil.getReceivableNum(user.getCompanySocialcreditCode().substring(0,4));

        String accountJson = DesUtils.decryptSM4(user.getPrivateKey());
        String receivableToChainTxHash = " ";
        log.info("调用智能合约方法，信息上链");
        List<Object> list = ReceivableContractInvoke.createReceivableInvoke(user.getCompanyBcAddr(), toUser.getCompanyBcAddr(), parValue, receivableNum, validUntil, receivableToChainTxHash,
               HyperchainConsts.contract.getContractAddr(),
                accountJson, user.getCompanySocialcreditCode().substring(0, 8));
        //todo 根据list中返回码封装不同状态
        //写到数据库
        Receivable receivable = new Receivable();
        receivable.setLastOwnerId(userId);
        receivable.setOwnerId(toId);
        receivable.setParValue(parValue);
        receivable.setReceivableNum(receivableNum);
        receivable.setValidUntil(validUntil);
        receivable.insert();
        return BaseResultFactory.produceResult(CodeEnum.SUCCESS, list.get(list.size() - 1).toString());
    }

    /**
     * 转发账单
     * @param userId
     * @param toId
     * @param receivableNum
     * @return
     */
    @Override
    public BaseResult transferReceivable(String userId, String toId, String receivableNum) throws Exception {
        //todo 检查转移对象地址是否正确

        User user = userMapper.selectById(userId);
        User toUser = userMapper.selectById(toId);
        String accountJson = DesUtils.decryptSM4(user.getPrivateKey());
        int nowTime = DateUtil.dateToInt(new Date());

        EntityWrapper ew = new EntityWrapper();
        ew.eq("owner_id",userId).eq("receivable_num",receivableNum);
        List<Receivable> receivables = receivableMapper.selectList(ew);
        //检查账单是否存在且属于当前用户
        if (receivables != null && receivables.size() > 0) {
            Receivable receivable = new Receivable();
            receivable = receivables.get(0);
            if (!userId.equals(receivable.getOwnerId())){
                return BaseResultFactory.produceResult(CodeEnum.RECEIPT_OWNER_ERROR, CodeEnum.RECEIPT_OWNER_ERROR.getMsg());
            }
            //检查账单是否过期
            if (receivable.getValidUntil()>nowTime){
                return BaseResultFactory.produceResult(CodeEnum.VALID_TIME_ERROR, CodeEnum.VALID_TIME_ERROR.getMsg());
            }
            //转移账单
            log.info("调用智能合约方法，信息上链");
            List<Object> list = ReceivableContractInvoke.transferReceivableInvoke(user.getCompanyBcAddr(), toUser.getCompanyBcAddr(), receivableNum, nowTime, " ", HyperchainConsts.contract.getContractAddr(), accountJson, user.getCompanySocialcreditCode().substring(0, 8));
            log.debug("id={}",receivable.getId());
            //写到数据库
            receivable.setOwnerId(toId);
            receivable.setLastOwnerId(userId);
            receivable.updateById();
            return BaseResultFactory.produceResult(CodeEnum.SUCCESS, list.get(list.size() - 1).toString());

        }
        //todo 根据list中返回码封装不同状态
       return BaseResultFactory.produceResult(CodeEnum.RECEIVABLE_NOT_EXSIT, CodeEnum.RECEIVABLE_NOT_EXSIT.getMsg());
    }

    /**
     * 查询用户账单列表
     * @param userId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public BaseResult receivableList(String userId, Integer pageNumber, Integer pageSize) throws Exception {
        //todo 根据用户链上地址查询
        Page<Receivable> page = new Page<>(pageNumber, pageSize);
        //Receivable receivable = new Receivable();
        EntityWrapper ew=new EntityWrapper();
        ew.eq("owner_id",userId);
        List<Receivable> list = receivableMapper.selectList(ew);

        Page<Receivable> receivableVOPage = page.setRecords(list);

        if (list != null && list.size() > 0) {
            return BaseResultFactory.produceResult(CodeEnum.SUCCESS, receivableVOPage.getRecords());
        }
        return  BaseResultFactory.produceResult(CodeEnum.RECEIVABLE_NOT_EXSIT, CodeEnum.RECEIVABLE_NOT_EXSIT.getMsg());
    }

    /**
     * 查询账单详情
     * @param userId
     * @param receivableNum
     * @return
     */
    @Override
    public BaseResult receivableInfo(String userId, String receivableNum) {
        EntityWrapper<Receivable> ew = new EntityWrapper();
        ew.eq("owner_id",userId).eq("receivable_num",receivableNum);
        List<Receivable> list = receivableMapper.selectList(ew);
        if (list != null && list.size() > 0) {
            return BaseResultFactory.produceResult(CodeEnum.SUCCESS, list.get(0));
        }
        return  BaseResultFactory.produceResult(CodeEnum.RECEIVABLE_NOT_EXSIT, CodeEnum.RECEIVABLE_NOT_EXSIT.getMsg());
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public boolean receivableInit(String userId) throws Exception {

        BaseResult baseResult = createAccountInvoke("中国银行", "91330110MA2B1A986", " hhvz", 1);

        BaseResult baseResult1 = createAccountInvoke("平安商贸", "91330110MA2B1A986J", "杭州",  1);
        return true;
    }

    /**
     * @return
     * @param tokenUserId
     */
    @Override
    public BaseResult contractUpdate(String tokenUserId) throws Exception {
        log.info("开始进行合约升级");
        String newBin = ESDKUtil.getContractBin(contractName);
        String newAbi = ESDKUtil.getContractAbi(contractName);
        ESDKUtil.upgradeContract(HyperchainConsts.contract.getAddressAdmin(), HyperchainConsts.contract.getContractAddr(),
                contractName, HyperchainConsts.contract.getPrikeyAdmin(), newAbi, newBin, true, HyperchainConsts.contract.getPwdAdmin());
        log.info("合约升级成功");
        return BaseResultFactory.produceResult(CodeEnum.SUCCESS, CodeEnum.SUCCESS.getMsg());
    }
}
