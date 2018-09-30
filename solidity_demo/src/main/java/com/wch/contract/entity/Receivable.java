package com.wch.contract.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 应收款
 * @author wangchenhao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receivable extends Model<Receivable> {

    private String id;

    /**
     * 应收款编号
     */
    private String receivableNum;

    /**
     * 面值
     */
    private String parValue;

    /**
     * 当前持有人id
     */
    private String ownerId;

    /**
     * 上一手持有人id
     */
    private String lastOwnerId;

    /**
     * 有效期
     */
    private int validUntil;

    @Override
    protected Serializable pkVal() {
        return  this.id;
    }
}
