package com.module.protocol.security;

import lombok.Getter;
import lombok.Setter;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class FindMediatorListReqData {

	private Integer page;
	private Integer pageSize;
}