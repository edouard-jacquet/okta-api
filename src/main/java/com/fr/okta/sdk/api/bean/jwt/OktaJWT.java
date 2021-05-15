package com.fr.okta.sdk.api.bean.jwt;

import java.util.Date;

import org.apache.commons.codec.binary.Base64;

import com.fr.okta.sdk.api.converter.DateJsonDeserializer;
import com.fr.okta.sdk.api.exception.OktaException;
import com.fr.okta.sdk.api.exception.jwt.OktaInvalidJWTException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class OktaJWT {

	private String jwt;
	private OktaJWTHeader header;
	private OktaJWTPayload payload;
	private byte[] data;
	private byte[] signature;

	public OktaJWT(String jwt) throws OktaException {
		if (jwt == null || jwt.isEmpty()) {
			throw new OktaInvalidJWTException();
		}

		this.jwt = jwt;

		this.deserialize();
	}

	private void deserialize() throws OktaException {
		String[] parts = this.jwt.split("\\.");

		if (parts.length != 3) {
			throw new OktaInvalidJWTException();
		}

		try {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Date.class, new DateJsonDeserializer());
			Gson gson = gsonBuilder.create();

			String jsonHeader = new String(Base64.decodeBase64(parts[0]));
			this.header = gson.fromJson(jsonHeader, OktaJWTHeader.class);

			String jsonPayload = new String(Base64.decodeBase64(parts[1]));
			this.payload = gson.fromJson(jsonPayload, OktaJWTPayload.class);

			this.data = (parts[0] + "." + parts[1]).getBytes();
			this.signature = Base64.decodeBase64(parts[2]);
		} catch (JsonSyntaxException exception) {
			throw new OktaInvalidJWTException();
		}
	}

	public OktaJWTHeader getHeader() {
		return this.header;
	}

	public OktaJWTPayload getPayload() {
		return this.payload;
	}

	public byte[] getData() {
		return this.data;
	}

	public byte[] getSignature() {
		return this.signature;
	}

}
