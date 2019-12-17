package com.team1.animalproject.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Service
public class CustomAuthenticationFailureHandler extends ExceptionMappingAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		if(exception instanceof LockedException){
			setDefaultFailureUrl(Constants.LOGIN_LOCKED_USER_URL);
		} else {
			setDefaultFailureUrl(Constants.LOGIN_FAILURE_URL);
		}

		CookieHelper.cookieDegeriniArttir(CookieConstant.HATALI_GIRIS);
		super.onAuthenticationFailure(request, response, exception);
	}

}
