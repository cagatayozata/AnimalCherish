package com.team1.animalproject.helpers.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticationService {

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

}
