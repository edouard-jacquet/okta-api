package com.fr.okta.sdk.api.exception.authentication;

import com.fr.okta.sdk.api.exception.OktaException;

public class OktaAuthenticationFailedException extends OktaException {

	private static final long serialVersionUID = -5285413054912145131L;

	public OktaAuthenticationFailedException() {
		super();
	}

	public OktaAuthenticationFailedException(String message) {
		super(message);
	}

	public OktaAuthenticationFailedException(Throwable exception) {
		super(exception);
	}

	public OktaAuthenticationFailedException(String message, Throwable exception) {
		super(message, exception);
	}

}
