package com.module.service;

import com.aliyuncs.exceptions.ClientException;
import com.core.cache.CacheService;
import com.core.exception.ResultException;
import com.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author chc
 * @create 2017-11-18 9:51
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class SmsService {

    /**
     * 电话验证码缓存时间
     **/
    private final static long PHONE_CACHE_TIME = 10L * 60;

    @Autowired
    private SmsAliyunService smsAliyunService;
    @Autowired
    private CacheService cacheService;

    public void sendVerificationCode(String phone,String openId) {
        String SmsTempateCode= "SMS_66805166";
        Map<String,String> smsParam = new HashMap<>();
        String verificationCode = getVerificationCode();
        smsParam.put("code",verificationCode);
        smsParam.put("product","法律援助");
        //发送短信验证码
        try {
            smsAliyunService.sendSms(SmsTempateCode,smsParam,phone);
        } catch (ClientException e) {
            e.getStackTrace();
            throw new ResultException("发送短信失败");
        }
        //记录短信验证码信息
        cacheService.put(phone, verificationCode, PHONE_CACHE_TIME);
//        daoFactory.getSmsCodeDao().saveSmsCode(verificationCode,openId,phone, DateUtil.getSecondLongTime());
    }

    public void sendNoticeSms(String[] phone,String userName) {
        String SmsTempateCode= "SMS_112930012";
        Map<String,String> smsParam = new HashMap<>();
        smsParam.put("name","");
        smsParam.put("username",userName);
        try {
            smsAliyunService.sendSms(SmsTempateCode,smsParam,phone);
        } catch (ClientException e) {
            e.getStackTrace();
            throw new ResultException("发送短信失败");
        }

    }

    /**
     * 校验电话验证码是否正确
     * @param phone
     * @param verificationCode
     * @return
     */
    public boolean checkSmsCode(String phone,String verificationCode){
        String code = (String) cacheService.get(phone);
        if(StringUtils.isEmpty(code)){
            return false;
        }
        if(!code.equals(verificationCode)){
            return false;
        }
        return true;
    }

    /**
     * 获取随机验证码
     *
     * @return
     */
    private String getVerificationCode(){
        int max = 999999;
        int min = 100000;
        int code = new Random().nextInt(max - min) + min;
        return String.valueOf(code);
    }




//    public static void main(String[] args) {
//        int max = 999999;
//        int min = 100000;
//        for(int i=0;i<30;i++){
//            System.out.println(new Random().nextInt(max - min) + min);
//        }
//
//    }
}
