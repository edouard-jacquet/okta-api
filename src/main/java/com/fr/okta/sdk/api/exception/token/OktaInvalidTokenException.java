package com.fr.okta.sdk.api.exception.token;

import com.fr.okta.sdk.api.exception.OktaException;

public class OktaInvalidTokenException extends OktaException {

	private static final long serialVersionUID = 7901018899706666653L;

	public OktaInvalidTokenException() {
		super();
	}

	public OktaInvalidTokenException(String message) {
		super(message);
	}

	public OktaInvalidTokenException(Throwable exception) {
		super(exception);
	}

	public OktaInvalidTokenException(String message, Throwable exception) {
		super(message, exception);
	}

}
