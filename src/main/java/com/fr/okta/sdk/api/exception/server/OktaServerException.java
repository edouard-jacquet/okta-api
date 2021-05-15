package com.fr.okta.sdk.api.exception.server;

import com.fr.okta.sdk.api.exception.OktaException;

public class OktaServerException extends OktaException {

	private static final long serialVersionUID = 3169500315378822752L;

	public OktaServerException() {
		super();
	}

	public OktaServerException(String message) {
		super(message);
	}

	public OktaServerException(Throwable exception) {
		super(exception);
	}

	public OktaServerException(String message, Throwable exception) {
		super(message, exception);
	}

}
