package com.fr.okta.sdk.api.bean;

public class OktaConfigurationProxy {

	private String host;
	private Integer port;

	public OktaConfigurationProxy(String proxy) {
		String[] parts = proxy.split(":");

		this.host = parts[0];
		this.port = Integer.parseInt(parts[1]);
	}

	public String getHost() {
		return this.host;
	}

	public Integer getPort() {
		return this.port;
	}

}
