package com.fr.okta.sdk.api.exception.jwt;

import com.fr.okta.sdk.api.exception.OktaException;

public class OktaJWTExpiredException extends OktaException {

	private static final long serialVersionUID = -4209753448139583874L;

	public OktaJWTExpiredException() {
		super();
	}

	public OktaJWTExpiredException(String message) {
		super(message);
	}

	public OktaJWTExpiredException(Throwable exception) {
		super(exception);
	}

	public OktaJWTExpiredException(String message, Throwable exception) {
		super(message, exception);
	}

}
