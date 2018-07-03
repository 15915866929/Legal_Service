package com.module.protocol.street;

import lombok.Getter;
import lombok.Setter;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class UpdateStreetReqData {

	private String street_Id;
	private String name;

}