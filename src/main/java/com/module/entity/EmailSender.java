package com.module.entity;

import lombok.Data;

/**
 * @author hsj
 * @date 2017/11/13
 * 邮箱发件人表
 */
@Data
public class EmailSender {

    private String emailSender_Id;
    /**
     * 发件人邮箱
     */
    private String email;
    /**
     * 发件人的 邮箱 和 密码
     * PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）,
     *     对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
     */
    private String pwd;
    /**
     * 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
     * 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
     */
    private String smtpEmail;
    /**
     * 昵称
     * From: 昵称（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
     */
    private String nickName;
    /**
     * 备注
     */
    private String note;
    /**
     * 最后修改人(userInfo_Id)
     */
    private String lastUpdateOperator;
    private String lastUpdateName;
    /**
     * 最后修改时间
     */
    private String lastUpdateTime;
}
