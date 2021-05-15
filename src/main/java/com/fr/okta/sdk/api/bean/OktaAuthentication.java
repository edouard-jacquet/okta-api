package com.fr.okta.sdk.api.bean;

public class OktaAuthentication {

	private String username;

	public OktaAuthentication(String username) {
		Integer index = username.indexOf("@");

		if (index != -1) {
			this.username = username.substring(0, index);
		} else {
			this.username = username;
		}
	}

	public String getUsername() {
		return this.username;
	}

}
