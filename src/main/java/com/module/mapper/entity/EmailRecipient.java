package com.module.mapper.entity;

import lombok.Data;

/**
 * @author hsj
 * @date 2017/11/13
 * 邮件收件人
 */

@Data
public class EmailRecipient  {

    private String emailRecipient_Id;
    /**
     * 发件人邮箱
     */
    private String email;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 发送类型 1发送 2抄送 3密送
     */
    private Integer type;
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
