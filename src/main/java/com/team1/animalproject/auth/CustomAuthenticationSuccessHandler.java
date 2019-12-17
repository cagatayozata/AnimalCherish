package com.team1.animalproject.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Service
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

		log.info("login successful for" + authentication.getName());
		CookieHelper.setCookie(CookieConstant.HATALI_GIRIS, String.valueOf(CookieConstant.SIFIR), CookieConstant.SIFIR);
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
