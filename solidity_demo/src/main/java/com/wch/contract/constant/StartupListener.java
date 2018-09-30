package com.wch.contract.constant;

import cn.hyperchain.sdk.rpc.Transaction.Transaction;
import com.hyperchain.ESDKConnection;
import com.hyperchain.ESDKUtil;

import com.wch.contract.entity.Contract;

import com.wch.contract.utils.HyperchainUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    //初始密码怎么处理，怎么传？
    static String pwdAdmin = "123";
    String contractAddr = null;
    String contractName = "Receivable";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        try {

            List<Contract> contracts = new Contract().selectAll();
            if (contracts != null && contracts.size() > 0) {

                HyperchainConstant.contract = contracts.get(0);
                if("true".equals(ESDKUtil.getHyperchainInfo("isUpgrade"))){
                    log.info("开始进行合约升级");
                    String newBin = ESDKUtil.getContractBin(contractName);
                    String newAbi = ESDKUtil.getContractAbi(contractName);
                    ESDKUtil.upgradeContract(HyperchainConstant.contract.getAddressAdmin(),HyperchainConstant.contract.getContractAddr(),
                            contractName,HyperchainConstant.contract.getPrikeyAdmin(),newAbi,newBin,true, HyperchainConstant.contract.getPwdAdmin());
                    log.info("合约升级成功");
                }
                log.info("合约已经部署，不需要重新部署");
                return;
            }


            if (contractAddr == null) {
                log.info("创建公私钥");
                List<String> keys = HyperchainUtils.getAddrAndPriKey(pwdAdmin);
                log.info("部署合约");
                String abi = ESDKUtil.getContractAbi(contractName);
                String bin = ESDKUtil.getContractBin(contractName);
                Transaction transaction = ESDKUtil.getTransaction(keys.get(0), bin, abi, null, contractName, false);
                Map<String, String> map = ESDKConnection.deployContract(transaction, keys.get(1), pwdAdmin, true);
                log.info("合约地址为 ： " + map.get("0x" + keys.get(0)));
                log.info("合约地址为合约地址入库");

                String priKeyencryptSM4 = keys.get(1);
//                String priKeyencryptSM4 = DesUtils.encryptSM4(keys.get(1));
                Contract contract = new Contract(UUID.randomUUID().toString().replaceAll("-", ""), map.get("0x" + keys.get(0)), pwdAdmin, keys.get(0), priKeyencryptSM4);
                contract.insert();
            }
        } catch (Exception e) {
            log.error("部署合约失败" + e);
        }
    }
}
