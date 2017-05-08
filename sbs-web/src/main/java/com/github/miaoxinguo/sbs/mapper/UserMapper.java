package com.github.miaoxinguo.sbs.mapper;

import com.github.miaoxinguo.mybatis.plugin.mapper.PageableMapper;
import com.github.miaoxinguo.sbs.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends PageableMapper<User, Integer> {

}
