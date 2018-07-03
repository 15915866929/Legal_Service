package com.module.returnModel;

import com.module.entity.UserInfo;
import lombok.Data;

/**
 * @author hsj
 * @date 2017/11/06
 */
@Data
public class ReturnUserInfo {

    private String userId;
    private String name;
    private Integer type;      //1表示学校 2表示教育局

    public ReturnUserInfo(UserInfo userInfo){
        this.setUserId(userInfo.getUserInfo_Id());
        this.setName(userInfo.getUname());
        this.setType(userInfo.getType());
    }
}
