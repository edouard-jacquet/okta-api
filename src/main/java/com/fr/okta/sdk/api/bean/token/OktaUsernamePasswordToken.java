package com.fr.okta.sdk.api.bean.token;

import com.fr.okta.sdk.api.exception.OktaException;
import com.fr.okta.sdk.api.exception.token.OktaInvalidTokenException;

public class OktaUsernamePasswordToken {

	private String username;
	private String password;

	public OktaUsernamePasswordToken(String username, String password) throws OktaException {
		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			throw new OktaInvalidTokenException();
		}

		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

}
