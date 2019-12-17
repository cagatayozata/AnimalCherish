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
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@EnableWebSecurity
@Order(1)
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

	//	@Bean
	//	public CookieSerializer cookieSerializer() {
	//		DefaultCookieSerializer serializer = new DefaultCookieSerializer();
	//		serializer.setCookieName("ss-gbs-uaa-sid");
	//		//		serializer.setCookiePath("/");
	//		//		serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
	//		return serializer;
	//	}

	@Override
	public void configure(WebSecurity web) throws Exception {

		web.httpFirewall(allowUrlEncodedSlashHttpFirewall());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//@formatter:off
		http.regexMatcher("(?!.*?actuator).*") // anything else that does not contain actuator
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
				.antMatchers(Constants.REGISTER_URL).permitAll()
				.antMatchers(Constants.LOGOUT_PAGE_URL).permitAll()
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

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.authenticationProvider(customAuthenticationProvider);

	}

	// added for the exception below
	// The request was rejected because the URL contained a potentially malicious String ";"
	// couldnt search the request but can be removed after investigation -<< talip
	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
		StrictHttpFirewall firewall = new StrictHttpFirewall();
		firewall.setAllowUrlEncodedSlash(true);
		firewall.setAllowSemicolon(true);
		return firewall;
	}

}