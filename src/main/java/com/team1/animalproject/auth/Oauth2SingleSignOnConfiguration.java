package com.team1.animalproject.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@EnableWebSecurity
@Order (1)
public class Oauth2SingleSignOnConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	@Autowired
	private CustomLogoutSuccessHandler customLogoutSuccessHandler;
	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//@formatter:off
		http
				.headers()
				.frameOptions().disable()
				.and()
				.regexMatcher("(?!.*?actuator).*") // anything else that does not contain actuator
			.formLogin()
					.loginPage(Constants.LOGIN_PAGE_URL)
					.loginProcessingUrl(Constants.LOGIN_PROCESSING_URL)
					.usernameParameter(Constants.LOGIN_USERNAME_PARAMETER)
					.passwordParameter(Constants.LOGIN_PASSWORD_PARAMETER)
					.failureHandler(customAuthenticationFailureHandler)
					.successHandler(customAuthenticationSuccessHandler)
			.and()
			.authorizeRequests()
				.antMatchers(URLConstants.URL_VERSION).permitAll()
				.antMatchers(URLConstants.URL_VERSION_K8S).permitAll()
				.antMatchers(Constants.LOGIN_PAGE_URL.concat("**")).permitAll()
				.antMatchers(Constants.RESOURCE_URL).permitAll()
				.antMatchers("/favicon.ico").permitAll()
				.antMatchers(Constants.LANDING_PAGE_URL).permitAll()
				.antMatchers(Constants.SIFRE_OLUSTUR_PAGE_URL).permitAll()
				.antMatchers(Constants.SIFRE_SIFIRLA_PAGE_URL.concat("*")).permitAll()
				.antMatchers(Constants.DOSYA).permitAll()
				.antMatchers(Constants.SIFRE_SIFIRLA_URL).permitAll()
				.antMatchers(Constants.FILES).permitAll()
				.antMatchers(Constants.AVATAR_PATH_URL).permitAll()
				.antMatchers(Constants.IMAGES_URL).permitAll()
				.antMatchers(Constants.REGISTER_URL).permitAll()
				.antMatchers(Constants.LOGOUT_PAGE_URL).permitAll()
				.antMatchers("/api/v1/**").permitAll()
				.antMatchers(Constants.RESOURCES).permitAll()
				.antMatchers(Constants.HOME_PAGE_URL).permitAll()
				.antMatchers(Constants.HOME_PAGE_URL.concat("**")).hasRole(RoleConstants.USER)
				.anyRequest().authenticated()
			.and()
			.csrf().disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.enableSessionUrlRewriting(false)
				.maximumSessions(1)
				.expiredUrl(Constants.LOGIN_PAGE_URL + "?error=ese")
			.and()
			.sessionFixation().changeSessionId()
			.and()
			.httpBasic()
			.and()
			.logout()
				.invalidateHttpSession(true)
				.logoutUrl(Constants.LOGOUT_PARAMETER)
				.logoutSuccessHandler(customLogoutSuccessHandler);
		//@formatter:on

		http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {

		auth.authenticationProvider(customAuthenticationProvider);

	}


	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

}