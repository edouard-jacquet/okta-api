package com.fr.okta.sdk.api.exception.server;

import com.fr.okta.sdk.api.exception.OktaException;

public class OktaBadIssuerException extends OktaException {

	private static final long serialVersionUID = -8937366033407765828L;

	public OktaBadIssuerException() {
		super();
	}

	public OktaBadIssuerException(String message) {
		super(message);
	}

	public OktaBadIssuerException(Throwable exception) {
		super(exception);
	}

	public OktaBadIssuerException(String message, Throwable exception) {
		super(message, exception);
	}
}
