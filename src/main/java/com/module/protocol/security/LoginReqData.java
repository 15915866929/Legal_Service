package com.module.protocol.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 登陆接口协议对象
*/
@Getter
@Setter
public class LoginReqData {

	@JsonProperty(value = "USER_NO")
	private String USER_NO;
	@JsonProperty(value = "USER_PASSWORD")
	private String USER_PASSWORD;

}