package com.fr.okta.sdk.api.bean.json;

import com.google.gson.annotations.SerializedName;

public class OktaUser {

	@SerializedName("id")
	private String id;
	@SerializedName("profile")
	private OktaProfile profile;

	public String getId() {
		return this.id;
	}

	public OktaProfile getProfile() {
		return this.profile;
	}

}
