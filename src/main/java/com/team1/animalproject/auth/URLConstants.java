package com.team1.animalproject.auth;

public final class URLConstants {

	// @formatter:off
	// MAIN API URLS START //
	public static final String URL_API_V1 = "/api/v1";
	// MAIN API URLS END //

	public static final String URL_VERSION = URL_API_V1 + "/version";
	public static final String URL_VERSION_K8S = URL_VERSION + "/k8s";

		private URLConstants() {
		throw new IllegalStateException("URLConstants not initable.");
	}
	// @formatter:on
}
