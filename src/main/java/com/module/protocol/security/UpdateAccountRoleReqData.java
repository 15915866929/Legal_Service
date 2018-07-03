package com.module.protocol.security;

import lombok.Getter;
import lombok.Setter;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class UpdateAccountRoleReqData {

	private String userInfo_Id;
	private String role_Id;
	private String department_Id;
}