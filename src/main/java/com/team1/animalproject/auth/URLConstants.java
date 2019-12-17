package com.team1.animalproject.auth;

public final class URLConstants {

	// @formatter:off

	// ANT MATCHERS START //
	public static final String ANT_MATCHERS_REPORT_API = "/r-api/**";
	public static final String ANT_MATCHERS_M2M_API = "/m2m-api/**";
	public static final String ANT_MATCHERS_JWT_SECURE_API = "/api/**"; //TODO: will change to /o-api talipk
	public static final String ANT_MATCHERS_ACTUATOR = "/actuator/**";
	public static final String SOAP_API = "/soap";
	public static final String ANT_MATCHERS_SOAP_API = SOAP_API + "/**";
	// ANT MATCHERS END //

	// MAIN API URLS START //
	public static final String URL_API_V1 = "/api/v1";
	public static final String URL_M2M_API_V1 = "/m2m-api/v1";
	public static final String URL_RAPOR_API_V1 = "/r-api/v1";
	// MAIN API URLS END //


	public static final String URL_VERSION = URL_API_V1 + "/version";
	public static final String URL_VERSION_K8S = URL_VERSION + "/k8s";

	// PATH PARAMETERS
	public static final String PATH_PARAMETER_KULLANICI_ID = "kullaniciId";
	public static final String URL_JWT_KULLANICI = URL_API_V1 + "/kullanici";

		private URLConstants() {
		throw new IllegalStateException("URLConstants not initable.");
	}
	// @formatter:on
}
