package com.module.protocol.legalAid;

import lombok.Getter;
import lombok.Setter;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class UpdateLegalAidGuideReqData {

	private String legalAidGuide_Id;
	private String materialHtml;
	private String processHtml;
	private String chargeHtml;
	private String fileIds;

}