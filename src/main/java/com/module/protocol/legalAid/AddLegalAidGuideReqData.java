package com.module.protocol.legalAid;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class AddLegalAidGuideReqData {

	private String materialHtml;
	private String processHtml;
	private String chargeHtml;
	private List<String> fileIds;
	private List<String> keepFileIds;

}