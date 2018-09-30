package com.wch.contract.service.impl;

import cn.hyperchain.sdk.rpc.HyperchainAPI;
import cn.hyperchain.sdk.rpc.Transaction.Transaction;
import cn.hyperchain.sdk.rpc.base.VMType;
import cn.hyperchain.sdk.rpc.returns.ReceiptReturn;
import com.hyperchain.ESDKConnection;
import com.hyperchain.ESDKUtil;
import com.hyperchain.exception.ContractInvokeFailException;
import com.hyperchain.exception.ESDKException;
import com.hyperchain.exception.ReadFileException;
import com.wch.contract.constant.HyperchainConstant;
import com.wch.contract.entity.Contract;

import com.wch.contract.entity.Receivable;
import com.wch.contract.entity.User;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;


@SpringBootTest
public class ContractInvokeImplTest {
    String contractAddr = "0x872f454f62ceb3631886087e0a32abc80f1bb951";


    String pwd = "123";
    String superAddr = "AD83C6F60177CCFED19776F25960330E19B05DD5";
    String superPriKey = "{\"address\":\"AD83C6F60177CCFED19776F25960330E19B05DD5\",\"publicKey\":\"04064869DE51B449003357DC02F461B3A37A6214A1494B5949A40C70F0195937951B22DD465E5D62669BDA5893C907A13A31B6ABE5F1C102FEC265942658CD9BA9\",\"privateKey\":\"380130F49E21417FD2BE521674BBBCA8793E2DB165B6C140593768EC91BBB429C6F431EA0E0E22AC\",\"privateKeyEncrypted\":true}";

    //    阿里巴巴
    String warehouseAddr = "EED5EA26D704AF2B21FCCEAA1B8140164CDB017D";
    String warehousePriKey = "{\"address\":\"EED5EA26D704AF2B21FCCEAA1B8140164CDB017D\",\"publicKey\":\"0498B836EAF11E70E85D4559994206E563D6BBF9C79EF82F6A373ADC1A0BBBFFABBFBD36533B951EE29C7B41DAA4632BE85113AD762CA62C310C0B68E75C0EE119\",\"privateKey\":\"8CA60492CEBE198F9841CFEEC36C5B341053F73815D542C8ABF25DB443E61576A9E65C26BA8A8A35\",\"privateKeyEncrypted\":true}";


    //    中国银行
    String addr1 = "1287297371DB4CF64212737926BAC8D25DBDF776";
    String priKey1 = "{\"address\":\"1287297371DB4CF64212737926BAC8D25DBDF776\",\"publicKey\":\"04C9018E1A713E1BBCF4A973F504B5D837CFA963ABE594657E0E4A59790FFA7ADFAA3F6CA4F752449A100ACAEEB00D4307C46D54E39B11DDCAFC96DE43E344E1A9\",\"privateKey\":\"17683D890CE92A3E3426D936E077152E492F9937FB069C4E8F1E3531CD1B14ADA63757396943867C\",\"privateKeyEncrypted\":true}";
    String pwd1 = "35000104";


    //    平安商贸
    String addr2 = "AB833A2B727266D5E41EB200ECE17CF6BB1938A6";
    String priKey2 = "{\"address\":\"AB833A2B727266D5E41EB200ECE17CF6BB1938A6\",\"publicKey\":\"045775CCA0EF327830B63C1204D3DDC7001437A3287B47BBC3D76496012EF0DB57F48C086EF45B8B4C2791F811E3312E3E49BFF4919B8C52A619AD18DF72214333\",\"privateKey\":\"B326A96001B6A965818B2A9E27D2A958B8B11292E6BC79D3F7B24B1B17509F6B4163669CD357E137\",\"privateKeyEncrypted\":true}";
    String pwd2 = "12000203";


    String addr3 = "BD9CBEDB7FC007F176575D9A190F389910B3605F";
    String priKey3 = "{\"address\":\"BD9CBEDB7FC007F176575D9A190F389910B3605F\",\"publicKey\":\"047E4A824CFBDF59BBF2D8D14EA8A0BA682B510CD8CF2A9892E770C47A257CEDF898E8F7EB3935243BB91DD9202B6D9D2BFCDD2DF94F66C3705B258CC083608E15\",\"privateKey\":\"4873275BD727C6D749FCA9C65AC21C090BF0D81D9C1857CD0578BD28F48AF4D7D57DB87FF7849640\",\"privateKeyEncrypted\":true}";
    String pwd3 = "12000203";

    private String contractName = "Receivable";


    //@Autowired
    //private ContractInvokeImpl contractInvoke;
    @Test
    public void createAccountInvoke() throws Exception {
        //ContractInvokeImpl contractInvoke =new ContractInvokeImpl();
//        String companyName = "";
//        String companySocialcreditCode = "";
//        String companyContactAddress = "";
//        String accountAddress = "";
//        int accountType = 0;
//        ContractInvokeImpl.createAccountInvoke(companyName,companySocialcreditCode, companyContactAddress,accountAddress, accountType);
        HyperchainConstant.contract = new Contract();
        HyperchainConstant.contract.setAddressAdmin(superAddr);
        HyperchainConstant.contract.setPrikeyAdmin(superPriKey);
        HyperchainConstant.contract.setContractAddr(contractAddr);
        HyperchainConstant.contract.setPwdAdmin(pwd);

            List<Object> list = ReceivableContractInvoke.createAccountInvoke("中国银行", "91330110MA2B1A986", "杭州", addr1, 1);
            for (Object o : list) {
                System.out.println(o);
            }

            list = ReceivableContractInvoke.createAccountInvoke("平安商贸", "91330110MA2B1A986J", "杭州", addr2, 1);
            for (Object o : list) {
                System.out.println(o);
            }


        list = ReceivableContractInvoke.createAccountInvoke("趣链科技", "91330110MA2B1A934", "杭州", addr3, 1);
        for (Object o : list) {
            System.out.println(o);
        }

    }

    private Transaction createToChainTx(String data, String addr, String priKey, String pwd) throws Exception {
        Transaction transaction = new Transaction(addr, addr, 0, false, VMType.EVM, data);
        transaction.signWithSM2(priKey, pwd);
        return transaction;
    }

    private String dataToChain() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            stringBuilder.append(UUID.randomUUID().toString());
        }
        return stringBuilder.toString();
    }

    @Test
    public void createContractInvoke() throws Exception {
        HyperchainConstant.contract = new Contract();
        HyperchainConstant.contract.setAddressAdmin(superAddr);
        HyperchainConstant.contract.setPrikeyAdmin(superPriKey);
        HyperchainConstant.contract.setContractAddr(contractAddr);
        HyperchainConstant.contract.setPwdAdmin(pwd);

        HyperchainAPI hyperchainAPI = new HyperchainAPI();
        ReceiptReturn receivableReturn = hyperchainAPI.sendTx(createToChainTx(dataToChain(), addr1, priKey1, pwd1));
        String receiptToChainHash = receivableReturn.getTxHash();
        receivableReturn = hyperchainAPI.sendTx(createToChainTx(dataToChain(), addr1, priKey1, pwd1));
        //String stockInToChainHash = receivableReturn.getTxHash();

        List<Object> list = ReceivableContractInvoke.createReceivableInvoke(warehouseAddr,addr1,"777","0NNCD2018062934003", 20180521,receiptToChainHash, contractAddr, warehousePriKey, pwd);
        for (Object o : list) {
            System.out.println(o);
        }

//        list = ReceivableContractInvoke.createReceivableInvoke(warehouseAddr,addr1,"123","1NNCD2018062934003", 20190521,receiptToChainHash, contractAddr, warehousePriKey, pwd);
//        for (Object o : list) {
//            System.out.println(o);
//        }
    }

    @Test
    public void transferContractInvoke() throws Exception {

        HyperchainAPI hyperchainAPI = new HyperchainAPI();
        ReceiptReturn receivableReturn = hyperchainAPI.sendTx(createToChainTx(dataToChain(), addr1, priKey1, pwd1));
        String receiptToChainHash = receivableReturn.getTxHash();
        receivableReturn = hyperchainAPI.sendTx(createToChainTx(dataToChain(), addr1, priKey1, pwd1));
        //String stockInToChainHash = receivableReturn.getTxHash();

        List<Object> list = ReceivableContractInvoke.transferReceivableInvoke(warehouseAddr, addr1, "0NNCD2018062934003", 20180520, receiptToChainHash, contractAddr, warehousePriKey, pwd);
        for (Object o : list) {
            System.out.println(o);
        }
    }

    @Test
    public void createReceivable() throws ReadFileException, ESDKException, UnsupportedEncodingException, ContractInvokeFailException {
        //List<String> result = ESDKConnection.invokeContractMethod(transaction, true, pwd, accountJson);
        String contractMethodName = "createReceivable";
        String abi = ESDKUtil.getContractAbi(contractName);

        String s = "0x000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000063030303030300000000000000000000000000000000000000000000000000000";
        List<Object> list = ESDKUtil.retDecodeAbi(contractMethodName, contractName, s, abi);
        System.out.println(list.get(0));


    }



    @Test
    public void financeReceiptBreakInvoke() {
    }

    @Test
    public void receiptCancelInvoke() {
    }

    @Test
    public void getAccountInfoInvoke() {
    }

    @Test
    public void getReceiptTxInfoByNumInvoke() {
    }

    @Test
    public void getTxInfoInvoke() {
    }

    @Test
    public void getReceiptInfoInvoke() {
    }

    @Test
    public void getStockInInfoInvoke() {
    }

    @Test
    public void getReceiptCreateCtrIvkTxHashInvoke() {
    }

    @Test
    public void dataToBlockchain() {
    }
}