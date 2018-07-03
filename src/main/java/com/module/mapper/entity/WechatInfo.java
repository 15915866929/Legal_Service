package com.module.mapper.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.core.base.entity.BaseModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author chc
 * @create 2017-10-26 16:52
 **/
@Getter
@Setter
@TableName("wechat_info")
public class WechatInfo extends BaseModel {


    private String appId;
    private String wechat_access_token;
    private Long token_expire_time;
    private String wechat_jsapi_ticket;
    private Long jsapi_expire_time;
    private String wechat_cardapi_ticket;
    private Long cardapi_expire_time;
}
