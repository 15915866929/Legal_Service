package com.module.protocol.lawFirm;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class AddLawMajorReqData {

	private List<String> lawMajors;

}