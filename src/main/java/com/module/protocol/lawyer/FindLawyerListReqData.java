package com.module.protocol.lawyer;

import lombok.Getter;
import lombok.Setter;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class FindLawyerListReqData {

	private Integer page;
	private Integer pageSize;
	private String sort;
	private Integer order;
	private String name;
	private String lawFirm_Id;
	private String street_Id;

}