package com.module.mapper.entity;

/**
 * Created by LJJ on 2017/7/27.
 * 自定义错误码
 */

public class ErrorCode {

    /**
     * token失效了
     */
    public static final String Token_200 = "200";
    /**
     * 没有此token
     */
    public static final String Token_201 = "201";
    /**
     * token 刷新失败
     */
    public static final String Token_202 = "202";

    /**
     * 很短时间内连续请求接口
     */
    public static final String frequently_400 = "301";
    /**
     * 文件上传失败
     */
    public static final String File_101 = "101";
    public static final String File_103="展示文件失败";
    /**
     * 邮件发送者不存在
     */
    public static final String Email_701="701";
    /**
     * 请求执行异常
     */
    public static final String wxMethod_6002="6002";

    /**
     * 短信发送失败
     */
    public static final String SendSms_Code="601";
    /**
     * 短信验证码失效
     */
    public static final String Check_SmsCode="602";

    /**
     * 自定义异常默认错误码
     */
    public static final String BaseException_6000 = "6000";
    /**
     * 未知错误异常
     */
    public static final String Exception_6001="6001";

}
