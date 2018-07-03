package com.module.protocol.legalAid;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class AddLegalMechanismReqData {

	private String name;
	private String street_Id;
	private Double longitude;
	private Double latitude;
	private List<String> fileIds;
	private String address;
	private String contact;
	private List<String> phones;
	private String note;

}