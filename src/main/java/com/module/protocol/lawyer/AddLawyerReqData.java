package com.module.protocol.lawyer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class AddLawyerReqData {

	private String name;
	private String lawFirm_Id;
	private String street_Id;
	private String imageFileId;
	private String introduction;
	private String telephone;//固定电话
	private String phone;//手机好吗
	private String email;
	private String qq;
	private String abbreviation;//村委会
	private String lawMajors;//专业领域
	private String glory;//个人荣誉

}