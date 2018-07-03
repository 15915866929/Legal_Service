package com.module.mapper.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.core.base.entity.BaseModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 微信用户信息
 * @author chc
 * @create 2017-10-27 15:29
 **/
@Getter
@Setter
@TableName("wechat_user_info")
public class WechatUserInfo extends BaseModel {

    private Boolean subscribe;
    private String openId;
    private String nickname;
    private String sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headImgUrl;
    private Long subscribeTime;
    private String unionId;
    private Integer sexId;
    private String remark;
    private Integer groupId;
    private String tagIds;
}
