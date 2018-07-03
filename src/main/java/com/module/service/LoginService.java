package com.module.service;


import com.core.base.support.Condition;
import com.core.base.support.Expression;
import com.core.cache.CacheService;
import com.core.exception.ResultException;
import com.core.util.IdGenerator;
import com.module.entity.Account;
import com.module.entity.Role;
import com.module.entity.UserInfo;
import com.module.returnModel.ReturnUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 登陆Service
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    /**
     * 用户信息缓存时间
     **/
    private final static long USER_CACHE_TIME = 30L * 60;

    @Autowired
    private CacheService cacheService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RoleService roleService;


    public HashMap login(String userNo, String userPassoword) {
        HashMap<String, Object> hashMap = new HashMap<>(16);
        Condition condition = Condition.create();
        condition.addExpression(Expression.eq("uaccount",userNo));
        condition.addExpression(Expression.eq("upassword",userPassoword));
        condition.addExpression(Expression.eq("generalAccount",1));
        List<Account> accountList = this.accountService.selectAll(condition);
        if(accountList==null || accountList.size()==0){
            throw new ResultException("用户名或密码错误");
        }else {
            Account account = accountList.get(0);
            if (account.getStatus() == 1) {
                UserInfo userInfo = this.userInfoService.select(account.getUserInfo_Id());
                userInfo.setLastLoginTime(new Date());
                this.userInfoService.updateById(userInfo);
                condition = Condition.create();
                condition.addExpression(Expression.eq("role_Id",userInfo.getRole_Id()));
                condition.addExpression(Expression.eq("status",1));
                List<Role> role = this.roleService.selectAll(condition);
                if(role==null || role.size()==0){
                    throw new ResultException("此用户没用分配权限");
                }
                //登录成功缓存用户信息
                String token = IdGenerator.generate();
                cacheService.put(token, userInfo, USER_CACHE_TIME);
                ReturnUserInfo returnUserInfo = new ReturnUserInfo(userInfo);
                hashMap.put("userInfo", returnUserInfo);
                hashMap.put("token", token);

            }else {
                throw new ResultException("该用户已被禁用");
            }
        }
        return hashMap;
    }

    /**
     * 刷新用户token
     * @param userInfo 用户
     * @return
     */
    public String refreshToken(UserInfo userInfo) {
        String nToken = IdGenerator.generate();
        if(userInfo==null){
            throw new ResultException("刷新缓存失败");
        }
        userInfo = this.userInfoService.select(userInfo.getId());
        if(userInfo==null){
            throw new ResultException("刷新缓存失败");
        }
        //登录成功缓存用户信息
        cacheService.put(nToken, userInfo, USER_CACHE_TIME);
        logger.info("用户{} 刷新token:{}", userInfo.getUname(), nToken);
        return nToken;
    }

}
