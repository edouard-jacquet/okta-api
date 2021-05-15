package com.fr.okta.sdk.api.exception.jwk;

import com.fr.okta.sdk.api.exception.OktaException;

public class OktaNotFoundJWKException extends OktaException {

	private static final long serialVersionUID = 1828495404719830654L;

	public OktaNotFoundJWKException() {
		super();
	}

	public OktaNotFoundJWKException(String message) {
		super(message);
	}

	public OktaNotFoundJWKException(Throwable exception) {
		super(exception);
	}

	public OktaNotFoundJWKException(String message, Throwable exception) {
		super(message, exception);
	}

}
