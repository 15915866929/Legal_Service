package com.module.protocol.legalAid;

import lombok.Getter;
import lombok.Setter;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class FindLegalAidListReqData {

	private Integer page;
	private Integer pageSize;
	private String sort;
	private Integer order;
	private String fieldName;
	private String value;
}