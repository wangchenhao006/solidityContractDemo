package com.wch.contract.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends Model<User> {
    private String id;

    private String companyBcAddr;

    private String companyName;

    private String companySocialcreditCode;

    private String companyContactAddress;

    private String privateKey;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
