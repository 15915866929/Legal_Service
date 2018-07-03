package com.module.protocol.lawFirm;

import lombok.Getter;
import lombok.Setter;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class FindLawFirmListReqData {

	private Integer page;
	private Integer pageSize;
	private String sort;
	private Integer order;
	private String name;

}