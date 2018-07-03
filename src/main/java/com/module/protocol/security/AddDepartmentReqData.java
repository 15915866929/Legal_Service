package com.module.protocol.security;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class AddDepartmentReqData {

	private String name;
	private String note;
	private String account;
	private List<String> menus;
}