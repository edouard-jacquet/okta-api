package com.fr.okta.sdk.api.exception.jwt;

import com.fr.okta.sdk.api.exception.OktaException;

public class OktaJWTCorruptedException extends OktaException {

	private static final long serialVersionUID = -3647319271313416773L;

	public OktaJWTCorruptedException() {
		super();
	}

	public OktaJWTCorruptedException(String message) {
		super(message);
	}

	public OktaJWTCorruptedException(Throwable exception) {
		super(exception);
	}

	public OktaJWTCorruptedException(String message, Throwable exception) {
		super(message, exception);
	}

}
