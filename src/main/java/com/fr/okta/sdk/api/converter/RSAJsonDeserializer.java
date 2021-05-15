package com.fr.okta.sdk.api.converter;

import java.lang.reflect.Type;
import java.math.BigInteger;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class RSAJsonDeserializer implements JsonDeserializer<BigInteger> {

	@Override
	public BigInteger deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		return new BigInteger(1, Base64.decodeBase64(json.getAsString()));
	}

}
