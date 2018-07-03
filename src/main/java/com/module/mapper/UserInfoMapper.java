package com.module.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.module.entity.Account;
import com.module.entity.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * 用户Mapper
 */
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}
