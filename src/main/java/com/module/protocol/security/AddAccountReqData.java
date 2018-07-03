package com.module.protocol.security;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class AddAccountReqData {

	private String account;
	private String role_Id;
	private String uname;
	private String contactPhone;
	private String note;
	/**
	 * 电话号码
	 */
	private String telephone;

	/**
	 * 村委会
	 */
	private String abbreviation;
}