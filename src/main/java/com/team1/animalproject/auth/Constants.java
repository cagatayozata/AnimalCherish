package com.team1.animalproject.auth;

public class Constants {

	public static final String HOME_PAGE_URL = "/";
	public static final String LOGIN_PAGE_URL = "/login.jsf";
	public static final String LOGIN_FAILURE_URL = LOGIN_PAGE_URL + "?error=true";
	public static final String LOGIN_LOCKED_USER_URL = LOGIN_FAILURE_URL + "&locked=true";
	public static final String LOGIN_PROCESSING_URL = "/loginprocess";
	public static final String LOGIN_USERNAME_PARAMETER = "j_username";
	public static final String LOGIN_PASSWORD_PARAMETER = "j_password";
	public static final String LOGOUT_PARAMETER = "/perform_logout";
	public static final String LANDING_PAGE_URL = "/landing.jsf";
	public static final String REGISTER_URL = "/register.jsf";
	public static final String LOGOUT_PAGE_URL = "/logout.jsf";
	public static final String RESOURCE_URL = "/javax.faces.resource/**";
	public static final String RESOURCES = "/resources/**";
	public static final String SIFRE_OLUSTUR_PAGE_URL = "/sifre_olustur.jsf*";
	public static final String SIFRE_SIFIRLA_PAGE_URL = "/sifre_sifirla.jsf";
	public static final String DOSYA = "/login.jsf";
	public static final String SIFRE_SIFIRLA_URL = "/sifirla/**";
	public static final String FILES = "/files/**";
	public static final String MAIN_URL = "localhost:8081";
	public static final String FILE_PATH = "staticFiles/";
	public static final String AVATAR_PATH = "staticFiles/avatars/";
	public static final String AVATAR_PATH_URL = "/staticFiles/avatars/**";
	public static final String IMAGES_URL = "/image/**";


}
