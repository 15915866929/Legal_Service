package com.module.protocol.legalAid;

import lombok.Getter;
import lombok.Setter;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class UpdateReceiveStatusReqData {

	private String userInfo_Id;
	private Integer can_receive_SMS;

}