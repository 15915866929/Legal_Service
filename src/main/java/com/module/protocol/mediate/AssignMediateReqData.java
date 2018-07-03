package com.module.protocol.mediate;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class AssignMediateReqData {

	private String id;
	private String mediator;
}