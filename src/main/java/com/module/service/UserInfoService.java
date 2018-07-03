package com.module.service;


import com.core.base.service.BaseModelService;
import com.module.entity.Account;
import com.module.entity.UserInfo;
import com.module.mapper.AccountMapper;
import com.module.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserInfoService extends BaseModelService<UserInfoMapper,UserInfo> {

}
