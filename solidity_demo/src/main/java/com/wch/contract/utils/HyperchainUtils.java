package com.wch.contract.utils;

import cn.hyperchain.sdk.exception.PwdException;
import cn.hyperchain.sdk.rpc.HyperchainAPI;
import cn.hyperchain.sdk.rpc.utils.Utils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wch.contract.constant.HyperchainConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

//import static org.picketbox.plugins.vault.PicketBoxSecurityVault.SALT;

public class HyperchainUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyperchainUtils.class);
    private static final String SALT = "1234567890101112";

    //创建公私钥
    public static List<String> getAddrAndPriKey(String pwd) throws GeneralSecurityException, PwdException, UnsupportedEncodingException {
        String accountJson = HyperchainAPI.newAccountSM2(pwd);
        JSONObject jsonObject = JSON.parseObject(accountJson);
        List<String> list = new ArrayList<>(2);
        list.add(jsonObject.getString("address"));
//      对已经加密的私钥用国密4再次加密
        list.add(accountJson);
        return list;
    }

    //创建hyperchain连接
    public static HyperchainAPI getHyperchainAPI() throws Exception {
        if (null == HyperchainConsts.HYPERCHAIN_API) {
            synchronized (HyperchainUtils.class) {
                if (null == HyperchainConsts.HYPERCHAIN_API) {
                    HyperchainConsts.HYPERCHAIN_API = new HyperchainAPI();
                }
            }
        }
        return HyperchainConsts.HYPERCHAIN_API;
    }

    /**
     * 获取基于国密算法的公私钥，私钥加密
     * @param pwd
     * @return
     * @throws GeneralSecurityException
     * @throws PwdException
     * @throws UnsupportedEncodingException
     */
    public static List<String> getAddrAndPriKeyEncryptSM4(String pwd) throws GeneralSecurityException, PwdException, UnsupportedEncodingException {
        if(pwd.length() > 8){
            pwd = pwd.substring(0,8);
        }
        String accountJson = HyperchainAPI.newAccountSM2(pwd);
        JSONObject jsonObject = JSON.parseObject(accountJson);
        List<String> list = new ArrayList<>(2);
        list.add(jsonObject.getString("address"));
//      对已经加密的私钥用国密4再次加密
        String encryptStr = Utils.encryptSM4(accountJson.getBytes("utf-8"), SALT);
        list.add(encryptStr);
        return list;
    }
//    public static void rewriteVesion(String targetVersion) throws WriteFileException, IOException {
//        Properties properties = new Properties();
//
//        FileInputStream fileInputStream = null;
//        FileOutputStream fileOutputStream = null;
//        try {
//            URL url = HyperchainUtils.class.getClassLoader().getResource("");
//            File accountFile = new File(url.getPath() + "hpc.properties");
//            if (!accountFile.exists()) {
//                LogUtil.info("hpc.properties文件不存在，新建一个");
//                accountFile.createNewFile();
//            }
//            fileInputStream = new FileInputStream(accountFile);
//            properties.load(fileInputStream);
//
//            fileOutputStream = new FileOutputStream(accountFile);
//            properties.setProperty("current_version", targetVersion);
//            properties.store(fileOutputStream, "curent_version");
//        } catch (IOException e) {
//            LogUtil.error("写入合约版本号失败");
//            throw new WriteFileException("写入合约版本号失败", e);
//        } finally {
//            if (fileInputStream != null) {
//                fileInputStream.close();
//            }
//            if (fileOutputStream != null) {
//                fileOutputStream.close();
//            }
//        }
//    }

    /**
     * 去读升级用的bin文件
     *
     * @param contractName
     * @return
     * @throws IOException
     */
    public static String getTargetBin(String contractName) throws IOException {

        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("contractBin/" + contractName);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        try {
            String bin;
            while (null != (bin = br.readLine())) {
                sb.append(bin);
            }
            return sb.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
            if (in != null) {
                in.close();
            }
        }
        return "";
    }
}
