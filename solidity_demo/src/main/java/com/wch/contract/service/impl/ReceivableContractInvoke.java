package com.wch.contract.service.impl;

import cn.hyperchain.sdk.rpc.HyperchainAPI;
import cn.hyperchain.sdk.rpc.Transaction.Transaction;
import cn.hyperchain.sdk.rpc.base.VMType;
import cn.hyperchain.sdk.rpc.returns.ReceiptReturn;
import com.hyperchain.ESDKConnection;
import com.hyperchain.ESDKUtil;
import com.wch.contract.constant.Code;
import com.wch.contract.constant.HyperchainConstant;
import com.wch.contract.exception.BlockChainException;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by linbo on 2018/9/4.
 */
@Service
@Slf4j
public class ReceivableContractInvoke {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceivableContractInvoke.class);
    private static String contractName = "Receivable";

    private static String CONTRACT_INVOKE_SUCCESS = "000000";


    /**
     * 添加用户
     *
     * @param companyName
     * @param companyContactAddress
     * @param accountAddr
     * @param accoutType            1:标准仓单企业, 2:仓储机构, 3:质检机构, 4:保险机构
     * @return
     */

    public static List<Object> createAccountInvoke(String companyName, String companySocialcreditCode, String companyContactAddress, String accountAddr, int accoutType) throws Exception {
        LOGGER.info("合约方法入参为， companyName={},companyContactAddress={},accountAddr={},accoutType={},invokerAddr={}",
                companyName, companyContactAddress, accountAddr, accoutType);

        String contractAddr = HyperchainConstant.contract.getContractAddr();
        String invokerAddr = HyperchainConstant.contract.getAddressAdmin();
        String pwd = HyperchainConstant.contract.getPwdAdmin();
        String accountJson = HyperchainConstant.contract.getPrikeyAdmin();

        //先对私钥的第二层加密进行解密
//        accountJson = DesUtils.decryptSM4(accountJson);

        Object[] objects = new Object[5];
        objects[0] = companyName;
        objects[1] = companySocialcreditCode;
        objects[2] = companyContactAddress;
        objects[3] = accountAddr;
        objects[4] = accoutType;

        String contractMethodName = "createAccount";
        String abi = ESDKUtil.getContractAbi(contractName);
        Transaction transaction = ESDKUtil.getTransaction(invokerAddr, contractAddr, contractMethodName, contractName, abi, objects, false);
        String ret = ESDKConnection.invokeContractMethod(transaction, true, pwd, accountJson).get(0);
        List<Object> list = ESDKUtil.retDecodeAbi(contractMethodName, contractName, ret, abi);
        if (!CONTRACT_INVOKE_SUCCESS.equals(list.get(0).toString())){
            LOGGER.error("新建账户合约方法调用失败，错误码为 ： " + list.get(0).toString());
            throw new BlockChainException(Code.CONTRACT_INVOKE_FAILED);
        }
        return list;
    }

    /**
     * 新建仓单
     *
     * @param receivableNum
     *
     * @param receiptToChainTxHash
     * @param outAddr
     * @param toAddr               合约方法调用者地址
     * @param contractAddr
     * @param accountJson          合约方法调用者地址对应的私钥
     * @param pwd
     * @return
*/
    public static List<Object> createReceivableInvoke(String outAddr, String toAddr, String parValue, String receivableNum, int validUntil, String receiptToChainTxHash,
                                                      String contractAddr, String accountJson, String pwd) throws Exception {

        Object[] objects = new Object[4];
        objects[0] = toAddr;
        objects[1] = parValue;
        objects[2] = receivableNum;
        objects[3] = validUntil;

        String contractMethodName = "createReceivable";
        String abi = ESDKUtil.getContractAbi(contractName);
        Transaction transaction = ESDKUtil.getTransaction(outAddr, contractAddr, contractMethodName, contractName, abi, objects, false);

        List<String> result = ESDKConnection.invokeContractMethod(transaction, true, pwd, accountJson);
        List<Object> list = ESDKUtil.retDecodeAbi(contractMethodName, contractName, result.get(0), abi);

        if (!CONTRACT_INVOKE_SUCCESS.equals(list.get(0).toString())){
            LOGGER.error("新建仓单合约方法调用失败，错误码为 ： " + list.get(0).toString());
            throw new BlockChainException(Code.CONTRACT_INVOKE_FAILED);
        }
        list.add(result.get(1));
        return list;
    }


    public static List<Object> transferReceivableInvoke(String outAddr, String toAddr, String receivableNum, int nowTime, String receiptToChainTxHash,
                                                        String contractAddr, String accountJson, String pwd) throws Exception {

        Object[] objects = new Object[3];
        objects[0] = toAddr;
        objects[1] = receivableNum;
        objects[2] = nowTime;

        String contractMethodName = "transferReceivable";
        String abi = ESDKUtil.getContractAbi(contractName);
        Transaction transaction = ESDKUtil.getTransaction(outAddr, contractAddr, contractMethodName, contractName, abi, objects, false);

        List<String> result = ESDKConnection.invokeContractMethod(transaction, true, pwd, accountJson);
        List<Object> list = ESDKUtil.retDecodeAbi(contractMethodName, contractName, result.get(0), abi);

        if (!CONTRACT_INVOKE_SUCCESS.equals(list.get(0).toString())){
            LOGGER.error("合约方法调用失败，错误码为 ： " + list.get(0).toString());
            throw new BlockChainException(Code.CONTRACT_INVOKE_FAILED);
        }
        list.add(result.get(1));
        return list;
    }

    public static List<Object> listReceivableInfoInvoke(String ownerAddr,  String receiptToChainTxHash, String contractAddr, String accountJson, String pwd) throws Exception {

        Object[] objects = new Object[1];
        objects[0] = ownerAddr;

        String contractMethodName = "listReceivable";
        String abi = ESDKUtil.getContractAbi(contractName);
        Transaction transaction = ESDKUtil.getTransaction(ownerAddr, contractAddr, contractMethodName, contractName, abi, objects, false);

        List<String> result = ESDKConnection.invokeContractMethod(transaction, true, pwd, accountJson);
        List<Object> list = ESDKUtil.retDecodeAbi(contractMethodName, contractName, result.get(0), abi);

        list.add(result.get(1));
        return list;
    }

    public static List<Object> getReceivableInfoInvoke(String receivableNum,  String invokerAddr, String contractAddr, String accountJson, String pwd) throws Exception {
        Object[] objects = new Object[1];
        objects[0] = receivableNum;
        String contractMethodName = "getReceivableInfo";
        String abi = ESDKUtil.getContractAbi("Receivable");
        Transaction transaction = ESDKUtil.getTransaction(invokerAddr, contractAddr, contractMethodName, contractName, abi, objects, true);
        String ret = ESDKConnection.invokeContractMethod(transaction, true, pwd, accountJson).get(0);
        List<Object> list = ESDKUtil.retDecodeHash(contractMethodName, contractName, ret, abi);
        list.set(0, new String(Hex.decode(list.get(0).toString()), "utf-8").substring(0, 6));
        list.set(2, new String(Hex.decode(list.get(2).toString()), "utf-8"));
        list.set(3, new String(Hex.decode(list.get(3).toString()), "utf-8"));
        return list;
    }



    /**
     * 查询账户信息
     * <p>
     * 合约方法调用者地址
     *
     * @param contractAddr
     * @param accountJson  合约方法调用者地址对应的私钥
     * @param pwd
     * @return
     */

    public static List<Object> getAccountInfoInvoke(String invokerAddr, String contractAddr, String accountJson, String pwd) throws Exception {
        Object[] objects = new Object[0];
        String contractMethodName = "getAccountInfo";
        String abi = ESDKUtil.getContractAbi("Receivable");
        Transaction transaction = ESDKUtil.getTransaction(invokerAddr, contractAddr, contractMethodName, contractName, abi, objects, true);
        String ret = ESDKConnection.invokeContractMethod(transaction, true, pwd, accountJson).get(0);
        List<Object> list = ESDKUtil.retDecodeAbi(contractMethodName, contractName, ret, abi);
        return list;
    }

    /**
     * @param data        上链数据
     * @param invokerAddr 卖方公司账户地址
     * @param accountJson 卖方公司私钥
     * @param pwd         组织社会信用代码前8位
     * @return
     * @throws Exception
     */
    public static String dataToBlockchain(String data, String invokerAddr, String accountJson, String pwd) throws Exception {
        Transaction transaction = new Transaction(invokerAddr, invokerAddr, 0, false, VMType.EVM, data);
        transaction.signWithSM2(accountJson, pwd);
        HyperchainAPI hyperchainAPI = new HyperchainAPI();
        ReceiptReturn receiptReturn = hyperchainAPI.sendTx(transaction);
        return receiptReturn.getTxHash();
    }

//    bytes32 contractInvokeTxHash, bytes32 operationType

}
