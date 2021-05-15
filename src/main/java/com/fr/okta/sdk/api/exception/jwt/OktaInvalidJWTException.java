package com.fr.okta.sdk.api.exception.jwt;

import com.fr.okta.sdk.api.exception.OktaException;

public class OktaInvalidJWTException extends OktaException {

	private static final long serialVersionUID = 5584888966855227844L;

	public OktaInvalidJWTException() {
		super();
	}

	public OktaInvalidJWTException(String message) {
		super(message);
	}

	public OktaInvalidJWTException(Throwable exception) {
		super(exception);
	}

	public OktaInvalidJWTException(String message, Throwable exception) {
		super(message, exception);
	}

}
