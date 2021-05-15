package com.fr.okta.sdk.api.bean.authentication;

import com.fr.okta.sdk.api.bean.token.OktaUsernamePasswordToken;
import com.google.gson.annotations.SerializedName;

public class OktaAuthenticationRequest {

	@SerializedName("username")
	private String username;
	@SerializedName("password")
	private String password;
	@SerializedName("options")
	private OktaAuthenticationRequestOptions options;

	public OktaAuthenticationRequest(OktaUsernamePasswordToken token) {
		this.username = token.getUsername();
		this.password = token.getPassword();
	}

}
