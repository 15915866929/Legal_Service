package com.module.protocol.lawFirm;

import lombok.Getter;
import lombok.Setter;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class AddLawFirmReqData {

	private String name;
	private String street_Id;
	private String address;
	private String email;
	private String phone;
	private Double longitude;
	private Double latitude;
	//事务所概况
	private String profile;
	//律师团队
	private String team;
	//专业领域
	private String field;

}