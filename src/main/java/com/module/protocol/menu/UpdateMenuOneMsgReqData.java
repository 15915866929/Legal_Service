package com.module.protocol.menu;

import lombok.Getter;
import lombok.Setter;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class UpdateMenuOneMsgReqData {

	private String id;
	private String name;
	private String url;

}