package com.wch.contract.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wch.contract.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}

