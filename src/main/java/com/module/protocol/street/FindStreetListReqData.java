package com.module.protocol.street;

import lombok.Getter;
import lombok.Setter;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class FindStreetListReqData {

	private Integer page;
	private Integer pageSize;
	private String sort;
	private Integer order;
	private String name;
}