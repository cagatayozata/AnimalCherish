package com.team1.animalproject.auth;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

public final class CookieHelper {

	private CookieHelper() {

	}

	public static void setCookie(Cookie cookie) {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		cookie.setPath(request.getContextPath());
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addCookie(cookie);
	}

	public static void cookieDegeriniArttir(String name) {
		Optional<Cookie> optCookie = getCookie(name);

		Cookie cookie = optCookie.orElse(new Cookie(name, String.valueOf(CookieConstant.SIFIR)));
		cookie.setValue(CommonUtils.sayisalStringDegeriArttir(cookie.getValue(), CookieConstant.BIR).orElse(String.valueOf(CookieConstant.BIR)));
		cookie.setMaxAge(CookieConstant.COOKIE_EXPIRY);

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		cookie.setPath(request.getContextPath());

		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addCookie(cookie);
	}

	public static void setCookie(String name, String value, int expiry) {

		Optional<Cookie> optCookie = getCookie(name);

		Cookie cookie = optCookie.orElse(new Cookie(name, value));
		cookie.setValue(value);
		cookie.setMaxAge(expiry);

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		cookie.setPath(request.getContextPath());

		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addCookie(cookie);
	}

	public static Optional<Cookie> getCookie(String name) {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

		Cookie[] userCookies = request.getCookies();
		if(userCookies.length != 0 && userCookies != null){
			return Arrays.stream(userCookies).filter(c -> c.getName().equals(name)).findFirst();
		}
		return Optional.empty();
	}
}
