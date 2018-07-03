package com.module.service;


import com.core.base.service.BaseModelService;
import com.module.entity.Account;
import com.module.mapper.AccountMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccountService extends BaseModelService<AccountMapper,Account> {

}
