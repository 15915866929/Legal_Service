package com.module.returnModel;

import com.module.entity.Department;
import com.module.entity.UserInfo;
import lombok.Data;

/**
 * @author hsj
 * @date 2017/11/06
 */
@Data
public class ReturnDepartment extends Department{

    /**
     * 管理员名字(链表查询)
     */
    private String uname;
    /**
     * 管理员账号(链表查询)
     */
    private String account;
    private String remarks;

}
