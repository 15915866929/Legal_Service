package com.module.protocol.security;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class FindDepartmentListReqData {

	private Integer page;
	private Integer pageSize;
}