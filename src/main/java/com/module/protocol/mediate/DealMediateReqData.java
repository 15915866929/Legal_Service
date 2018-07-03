package com.module.protocol.mediate;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class DealMediateReqData {

	private String id;
	private Integer status;
	private String acceptNote;               //接受处理备注
	private String dealNote;               //完成处理备注
	private Date acceptTime; //接受处理时间
	private Date dealTime; //完成处理时间
}