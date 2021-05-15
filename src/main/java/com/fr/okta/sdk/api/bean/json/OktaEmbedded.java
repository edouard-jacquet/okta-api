package com.fr.okta.sdk.api.bean.json;

import com.google.gson.annotations.SerializedName;

public class OktaEmbedded {

	@SerializedName("user")
	private OktaUser user;

	public OktaUser getUser() {
		return this.user;
	}

}
