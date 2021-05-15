package com.fr.okta.sdk.api.bean;

public class OktaConfiguration {

	private String server;
	private OktaConfigurationProxy proxy;

	public OktaConfiguration(String server) {
		this.server = server;
	}

	public String getServer() {
		return this.server;
	}

	public OktaConfigurationProxy getProxy() {
		return this.proxy;
	}

	public void setProxy(OktaConfigurationProxy proxy) {
		this.proxy = proxy;
	}

}
