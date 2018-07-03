package com.module.interceptor;

import com.core.cache.CacheService;
import com.module.entity.UserInfo;
import com.module.interceptor.annotation.AccessRight;
import com.core.protocol.BaseResponse;
import com.core.protocol.ReturnCode;
import com.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 拦截器
 * @author 何健锋
 */
@Aspect
@Component
@Slf4j
public class LoginInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Resource
    private HttpServletRequest request;
    @Autowired
    private CacheService cacheService;

    @Around("execution(public * com.module.controller..*.*(..))")
    public Object around(ProceedingJoinPoint jp) throws Throwable{
        BaseResponse result = null;
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        Class controller = jp.getTarget().getClass();
        AccessRight accessRight = (AccessRight) controller.getAnnotation(AccessRight.class);
        Class<?>[] classes = method.getParameterTypes();
        Object[] arguments = jp.getArgs();
        String token = request.getHeader("x-access-token");
        String targetName = jp.getTarget().getClass().getName();
        UserInfo userInfo = null;
        if(accessRight!=null && "-1".equals(accessRight.rightsCode())){//当检测到注解时，且注解为-1，跳过登陆校验
            if(StringUtils.isNotEmpty(token)){
                userInfo = (UserInfo) cacheService.get(token);
            }
        }else if (StringUtils.isNotEmpty(token)) {//检测token
            userInfo = (UserInfo) cacheService.get(token);
            if (userInfo == null) {
                logger.info("token {} 已超时！", token);
                BaseResponse resp = new BaseResponse();
                resp.setErrorCode(ReturnCode.RECODE_UNLOGIN);
                resp.setMsg("登录超时，请重新登录！");
                return resp;
            }
            //检测权限
//            if (!checkRight(accessRight, user)) {
//                logger.warn("用户：{} 非法访问 {}", user.getName(),accessRight.rightsCode());
//                BaseResponse resp = new BaseResponse();
//                resp.setErrorCode(ReturnCode.RETCODE_UNDIFINE_ERR);
//                resp.setMsg("无权访问！");
//                return resp;
//            }
        } else {
            logger.warn("非法访问 {};没登录！", targetName.substring(targetName.lastIndexOf(".") + 1) + "." + method.getName());
            BaseResponse resp = new BaseResponse();
            resp.setErrorCode(ReturnCode.RECODE_UNLOGIN);
            resp.setMsg("没登录！");
            return resp;
        }
        //注入 user 参数并执行调用
        if(userInfo!=null){
            for (int i = 0; i < classes.length; i++) {
                Class<?> cls = classes[i];
                if (UserInfo.class.equals(cls)) {
                    arguments[i] = userInfo;
                }
            }
        }
        try {
            result = (BaseResponse)jp.proceed(arguments);
            if(result.getResult()!=null){//对data数据加密
//                    AesUtils.Encrypt()
            }
        } catch (Throwable throwable) {
            logger.error("", throwable);
        }
        return result;
//        this.printLog("已经记录下操作日志@Around 方法执行前");
//        pjp.proceed();
//        this.printLog("已经记录下操作日志@Around 方法执行后");
    }

//    @Before("execution(public * com.module.*.controller.*(..))")
//    public void before(ProceedingJoinPoint jp) {
//
//    }

//    @After("execution(public * com.module.*.controller.*(..))")
//    public void after() {
//
//    }

//    private boolean checkRight(AccessRight accessRight,User user){
//        boolean b = false;
//        if("-1".equals(user.getRoleId()) || "-2".equals(user.getRoleId())){//超级管理员拥有所有权限  或者客户不相知访问权限
//            return true;
//        }
//        List<Module> moduleList = user.getModuleList();
//        if(moduleList==null){
//            return b;
//        }
//        for (Module module : moduleList) {
//            if(accessRight.rightsCode().equals(module.getModuleCode())){
//                return true;
//            }
//        }
//        return b;
//    }
}
