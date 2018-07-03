package com.module.protocol.notarize;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class DealNotarizeReqData {

	private String id;
	private Integer status;
	private String acceptNote;               //接受处理备注
	private String dealNote;               //处理备注
	private Date acceptTime; //处理时间
	private Date dealTime; //处理时间
}