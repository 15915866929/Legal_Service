package com.module.protocol.menu;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class AddMenusReqData {

	private String fatherId;
	private String name;
	private String url;
	private List<String> role_Ids;

}