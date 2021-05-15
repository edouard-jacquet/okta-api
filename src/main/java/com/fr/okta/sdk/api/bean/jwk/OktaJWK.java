package com.fr.okta.sdk.api.bean.jwk;

import java.math.BigInteger;

import com.google.gson.annotations.SerializedName;

public class OktaJWK {

	@SerializedName("use")
	private String use;
	@SerializedName("kid")
	private String identifier;
	@SerializedName("kty")
	private String type;
	@SerializedName("alg")
	private String algorithm;
	@SerializedName("n")
	private BigInteger modulus;
	@SerializedName("e")
	private BigInteger exponent;

	public String getUse() {
		return this.use;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public String getType() {
		return this.type;
	}

	public String getAlgorithm() {
		return this.algorithm;
	}

	public BigInteger getModulus() {
		return this.modulus;
	}

	public BigInteger getExponent() {
		return this.exponent;
	}

}
