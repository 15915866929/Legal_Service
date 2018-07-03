package com.module.protocol.security;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class AddRoleReqData {

	private String department_Id;
	private String roleName;
	private String note;
	private List<String> menus;
	private Integer type;
}