package com.module.protocol.menu;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class UpdateMenusOrderReqData {

	private List<String> children;

}