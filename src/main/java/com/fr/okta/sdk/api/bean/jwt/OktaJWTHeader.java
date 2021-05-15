package com.fr.okta.sdk.api.bean.jwt;

import com.google.gson.annotations.SerializedName;

public class OktaJWTHeader {

	@SerializedName("kid")
	private String keyIdentifier;
	@SerializedName("alg")
	private String algorithm;

	public String getKeyIdentifier() {
		return this.keyIdentifier;
	}

	public String getAlgorithm() {
		return this.algorithm;
	}

}
