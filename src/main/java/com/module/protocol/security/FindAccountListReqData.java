package com.module.protocol.security;

import lombok.Getter;
import lombok.Setter;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class FindAccountListReqData {

	private Integer page;
	private Integer pageSize;
}