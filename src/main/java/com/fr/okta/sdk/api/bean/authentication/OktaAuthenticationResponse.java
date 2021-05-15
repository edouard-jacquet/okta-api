package com.fr.okta.sdk.api.bean.authentication;

import com.fr.okta.sdk.api.bean.json.OktaEmbedded;
import com.google.gson.annotations.SerializedName;

public class OktaAuthenticationResponse {

	@SerializedName("status")
	private String status;
	@SerializedName("_embedded")
	private OktaEmbedded embedded;
	@SerializedName("errorCode")
	private String errorCode;

	public String getStatus() {
		return this.status;
	}

	public OktaEmbedded getEmbedded() {
		return this.embedded;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

}
