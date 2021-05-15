package com.fr.okta.sdk.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.RSAPublicKeySpec;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fr.okta.sdk.api.bean.OktaAuthentication;
import com.fr.okta.sdk.api.bean.OktaConfiguration;
import com.fr.okta.sdk.api.bean.OktaConfigurationProxy;
import com.fr.okta.sdk.api.bean.authentication.OktaAuthenticationRequest;
import com.fr.okta.sdk.api.bean.authentication.OktaAuthenticationResponse;
import com.fr.okta.sdk.api.bean.json.OktaEmbedded;
import com.fr.okta.sdk.api.bean.json.OktaProfile;
import com.fr.okta.sdk.api.bean.json.OktaUser;
import com.fr.okta.sdk.api.bean.jwk.OktaJWK;
import com.fr.okta.sdk.api.bean.jwt.OktaJWT;
import com.fr.okta.sdk.api.bean.jwt.OktaJWTHeader;
import com.fr.okta.sdk.api.bean.jwt.OktaJWTPayload;
import com.fr.okta.sdk.api.bean.token.OktaUsernamePasswordToken;
import com.fr.okta.sdk.api.converter.RSAJsonDeserializer;
import com.fr.okta.sdk.api.exception.OktaException;
import com.fr.okta.sdk.api.exception.authentication.OktaAuthenticationFailedException;
import com.fr.okta.sdk.api.exception.jwk.OktaNotFoundJWKException;
import com.fr.okta.sdk.api.exception.jwt.OktaJWTCorruptedException;
import com.fr.okta.sdk.api.exception.jwt.OktaJWTExpiredException;
import com.fr.okta.sdk.api.exception.server.OktaBadIssuerException;
import com.fr.okta.sdk.api.exception.server.OktaServerException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class OktaAPI {

	private OktaConfiguration configuration;
	private OktaJWK jwk;

	/**
	 * Initialise une API pour intéragir avec les services d'OKTA.
	 *
	 * @param configuration La configuration associée.
	 */
	public OktaAPI(OktaConfiguration configuration) {
		this.configuration = configuration;
	}

	/**
	 * Permet l'authentification à OKTA à partir d'un username & password.
	 *
	 * @param token Le token associé.
	 * @return l'authentification associé, si elle réussie.
	 * @throws OktaException
	 */
	public OktaAuthentication authenticate(OktaUsernamePasswordToken token) throws OktaException {
		OktaEmbedded embedded = this.validateUsernamePasswordToken(token);

		OktaUser user = embedded.getUser();
		OktaProfile profile = user.getProfile();

		OktaAuthentication authentication = new OktaAuthentication(profile.getLogin());

		return authentication;
	}

	/**
	 * Permet l'authentification à OKTA à partir d'un JWT.
	 *
	 * @param jwt Le token associé.
	 * @return l'authentification associé, si elle réussie.
	 * @throws OktaException
	 */
	public OktaAuthentication authenticate(OktaJWT jwt) throws OktaException {
		this.validateJWT(jwt);
		this.validateJWTIntegrity(jwt);

		OktaJWTPayload payload = jwt.getPayload();

		OktaAuthentication authentication = new OktaAuthentication(payload.getPreferredUsername());

		return authentication;
	}

	/**
	 * Détermine si le token est valide.
	 *
	 * @param token Le token à tester.
	 * @return les informations relatives à l'utilisateur.
	 * @throws OktaException
	 */
	private OktaEmbedded validateUsernamePasswordToken(OktaUsernamePasswordToken token) throws OktaException {
		try {
			Gson gson = new Gson();
			OktaAuthenticationRequest oktaRequest = new OktaAuthenticationRequest(token);
			String jsonData = gson.toJson(oktaRequest);

			StringEntity httpEntity = new StringEntity(jsonData);

			HttpPost httpRequest = new HttpPost(this.configuration.getServer() + "/api/v1/authn");
			httpRequest.addHeader("Accept", "application/json");
			httpRequest.addHeader("Content-Type", "application/json");
			httpRequest.setEntity(httpEntity);

			JsonObject jsonResponse = this.executeHttpRequest(httpRequest);
			OktaAuthenticationResponse response = gson.fromJson(jsonResponse, OktaAuthenticationResponse.class);

			if (response.getErrorCode() != null || !response.getStatus().equals("SUCCESS")
					|| response.getEmbedded() == null) {
				throw new OktaAuthenticationFailedException();
			}

			return response.getEmbedded();
		} catch (IOException exception) {
			throw new OktaServerException(exception);
		} catch (JsonSyntaxException exception) {
			throw new OktaAuthenticationFailedException();
		}
	}

	/**
	 * Détermine si le JWT est valide.
	 *
	 * @param jwt Le token à tester.
	 * @throws OktaException
	 */
	private void validateJWT(OktaJWT jwt) throws OktaException {
		OktaJWTPayload payload = jwt.getPayload();
		String issuer = payload.getIssuer();

		if (!issuer.equals(this.configuration.getServer())) {
			throw new OktaBadIssuerException();
		}

		Date current = new Date();
		Date expiration = payload.getExpiration();
		Date issuerAt = payload.getIssuedAt();

		if (current.before(issuerAt) || current.after(expiration)) {
			throw new OktaJWTExpiredException();
		}
	}

	/**
	 * Permet de vérifier si le JWT, envoyé a été corrompu (correspond ou pas à
	 * celui envoyé par le serveur OKTA).
	 *
	 * @param jwt Le token à vérifier.
	 * @throws OktaException
	 */
	private void validateJWTIntegrity(OktaJWT jwt) throws OktaException {
		try {
			OktaJWK jwk = this.getJWK(jwt);
			RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(jwk.getModulus(), jwk.getExponent());
			PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);

			Signature signature = Signature.getInstance("SHA256withRSA");
			signature.initVerify(publicKey);
			signature.update(jwt.getData());

			if (!signature.verify(jwt.getSignature())) {
				throw new OktaJWTCorruptedException();
			}
		} catch (KeyException exception) {
			throw new OktaJWTCorruptedException();
		} catch (GeneralSecurityException exception) {
			throw new OktaJWTCorruptedException();
		}
	}

	/**
	 * Récupère la JWK (JSON Web Key)
	 *
	 * @param jwt Le token.
	 * @return la clé de l'api.
	 * @throws OktaException
	 */
	private OktaJWK getJWK(OktaJWT jwt) throws OktaException {
		if (this.jwk == null) {
			OktaJWK candidate = this.fetchJWK(jwt);

			if (candidate == null) {
				throw new OktaNotFoundJWKException();
			}

			this.jwk = candidate;
		}

		return this.jwk;
	}

	/**
	 * Cherche une JWK compatible avec le JWT.
	 *
	 * @param jwt Le token.
	 * @return la clé, si trouvée.
	 */
	private OktaJWK fetchJWK(OktaJWT jwt) throws OktaException {
		try {
			HttpGet httpRequest = new HttpGet(this.configuration.getServer() + "/oauth2/v1/keys");

			JsonObject response = this.executeHttpRequest(httpRequest);
			JsonArray keys = response.getAsJsonArray("keys");

			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(BigInteger.class, new RSAJsonDeserializer());
			Gson gson = gsonBuilder.create();

			List<OktaJWK> jwkCandidates = gson.fromJson(keys, new TypeToken<List<OktaJWK>>() {
			}.getType());

			OktaJWTHeader jwtHeader = jwt.getHeader();

			for (OktaJWK jwk : jwkCandidates) {
				if (jwk.getUse().equals("sig") && jwk.getType().equals("RSA")
						&& jwk.getIdentifier().equals(jwtHeader.getKeyIdentifier())
						&& jwk.getAlgorithm().equals(jwtHeader.getAlgorithm())) {
					return jwk;
				}
			}

			throw new OktaNotFoundJWKException();
		} catch (IOException exception) {
			throw new OktaServerException(exception);
		} catch (JsonSyntaxException exception) {
			throw new OktaNotFoundJWKException();
		}
	}

	/**
	 * Exécute une requête HTTP, et renvoi la réponse.
	 *
	 * @param request La requête à exécuter.
	 * @return la réponse formatée en JSON.
	 * @throws IOException
	 */
	private JsonObject executeHttpRequest(HttpRequestBase request) throws IOException {
		if (this.configuration.getProxy() != null) {
			OktaConfigurationProxy proxy = this.configuration.getProxy();
			HttpHost httpProxy = new HttpHost(proxy.getHost(), proxy.getPort());
			RequestConfig requestConfig = RequestConfig.custom().setProxy(httpProxy).build();

			request.setConfig(requestConfig);
		}

		HttpClient client = HttpClientBuilder.create().build();

		HttpResponse response = client.execute(request);

		InputStream inputStream = response.getEntity().getContent();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		StringBuffer stringBuffer = new StringBuffer();

		String line;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuffer.append(line);
		}

		return JsonParser.parseString(stringBuffer.toString()).getAsJsonObject();
	}

}
