package com.fr.okta.sdk.api.bean.json;

import com.google.gson.annotations.SerializedName;

public class OktaProfile {

	@SerializedName("login")
	private String login;
	@SerializedName("lastName")
	private String lastName;
	@SerializedName("firstName")
	private String firstName;

	public String getLogin() {
		return this.login;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getFirstName() {
		return this.firstName;
	}

}
