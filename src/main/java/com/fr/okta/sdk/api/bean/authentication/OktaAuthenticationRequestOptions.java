package com.fr.okta.sdk.api.bean.authentication;

import com.google.gson.annotations.SerializedName;

public class OktaAuthenticationRequestOptions {

	@SerializedName("multiOptionalFactorEnroll")
	private boolean multiOptionalFactorEnroll = false;
	@SerializedName("warnBeforePasswordExpired")
	private boolean warningBeforePasswordExpired = false;

}
