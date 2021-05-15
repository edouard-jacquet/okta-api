package com.fr.okta.sdk.api.exception;

public abstract class OktaException extends Exception {

	private static final long serialVersionUID = 7715464079364928735L;

	public OktaException() {
		super();
	}

	public OktaException(String message) {
		super(message);
	}

	public OktaException(Throwable exception) {
		super(exception);
	}

	public OktaException(String message, Throwable exception) {
		super(message, exception);
	}

}
