package com.wch.contract.entity;

import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author apollo123
 * @since 2018-09-04
 */
public class Contract extends Model<Contract> {

    private static final long serialVersionUID = 1L;

    /**
     * 表id
     */
    private String id;
    /**
     * 合约地址
     */
    private String contractAddr;
    /**
     * 部署合约私钥加密密码
     */
    private String pwdAdmin;
    /**
     * 地址
     */
    private String addressAdmin;
    /**
     * 私钥
     */
    private String prikeyAdmin;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContractAddr() {
        return contractAddr;
    }

    public void setContractAddr(String contractAddr) {
        this.contractAddr = contractAddr;
    }

    public String getPwdAdmin() {
        return pwdAdmin;
    }

    public void setPwdAdmin(String pwdAdmin) {
        this.pwdAdmin = pwdAdmin;
    }

    public String getAddressAdmin() {
        return addressAdmin;
    }

    public void setAddressAdmin(String addressAdmin) {
        this.addressAdmin = addressAdmin;
    }

    public String getPrikeyAdmin() {
        return prikeyAdmin;
    }

    public void setPrikeyAdmin(String prikeyAdmin) {
        this.prikeyAdmin = prikeyAdmin;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    public Contract() {
    }

    public Contract(String id, String contractAddr, String pwdAdmin, String addressAdmin, String prikeyAdmin) {
        this.id = id;
        this.contractAddr = contractAddr;
        this.pwdAdmin = pwdAdmin;
        this.addressAdmin = addressAdmin;
        this.prikeyAdmin = prikeyAdmin;
    }

    @Override
    public String toString() {
        return "Contract{" +
        "id=" + id +
        ", contractAddr=" + contractAddr +
        ", pwdAdmin=" + pwdAdmin +
        ", addressAdmin=" + addressAdmin +
        ", prikeyAdmin=" + prikeyAdmin +
        "}";
    }
}
