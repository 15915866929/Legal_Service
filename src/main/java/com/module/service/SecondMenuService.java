package com.module.service;


import com.core.base.service.BaseModelService;
import com.module.entity.SecondMenu;
import com.module.mapper.SecondMenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class SecondMenuService extends BaseModelService<SecondMenuMapper,SecondMenu> {

}
