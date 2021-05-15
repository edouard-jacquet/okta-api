package com.fr.okta.sdk.api.bean.jwt;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class OktaJWTPayload {

	@SerializedName("iss")
	String issuer;
	@SerializedName("exp")
	Date expiration;
	@SerializedName("iat")
	Date issuedAt;
	@SerializedName("preferred_username")
	String preferredUsername;

	public String getIssuer() {
		return this.issuer;
	}

	public Date getExpiration() {
		return this.expiration;
	}

	public Date getIssuedAt() {
		return this.issuedAt;
	}

	public String getPreferredUsername() {
		return this.preferredUsername;
	}

}
