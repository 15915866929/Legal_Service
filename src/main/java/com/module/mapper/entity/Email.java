package com.module.mapper.entity;

import com.module.entity.EmailRecipient;
import com.module.entity.EmailSender;
import lombok.Data;

import java.util.List;

/**
 * @author hsj
 * @date 2017/11/14
 */

@Data
public class Email {

    /**
     * 发件人
     */
    private EmailSender sender;
    /**
     * 收件人(数组)
     */
    private List<EmailRecipient> recipients;
    /**
     * 文本信息
     */
    private EmailContent content;
    /**
     * 发送状态 0没发送 1发送成功 2发送失败
     */
    private Integer status = 0;
    /**
     * 发送失败理由
     */
    private String reason;
    /**
     * 当前操作人(userInfo_Id)
     */
    private String operatorId;
    /**
     * 发送时间
     */
    private String ctime;
}
