package com.wch.contract.solidity_demo;

import com.wch.contract.entity.Receivable;
import com.wch.contract.entity.User;
import com.wch.contract.mapper.ReceivableMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContractApplicationTests {
    @Autowired
    private ReceivableMapper receivableMapper;
    @Test
    public void contextLoads() {
    }

    @Test
    public void insertTest() {
        User user = new User();
        user.setCompanyName("123");
        user.insert();
    }

    @Test
    public void insertTest2() {
        Receivable receivable = new Receivable();
        receivable.setParValue("75");

        receivable.setId("8210fd3b84724563a44e6357b9cf3ab2");
        receivable.updateById();
        receivable.setParValue("754");
        //receivable.setLastOwnerId(userId);
        receivableMapper.updateById(receivable);
    }


}
