package com.team1.animalproject;

import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SpringPrimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPrimeApplication.class, args);
	}

	@Bean
	public static CustomScopeConfigurer customScopeConfigurer() {
		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		Map<String, Object> scopes = new HashMap<>();
		scopes.put("view", new ViewScope());
		configurer.setScopes(scopes);
		return configurer;
	}

	@Bean
	public ServletRegistrationBean facesServlet() {
		FacesServlet servlet = new FacesServlet();
		ServletRegistrationBean registration = new ServletRegistrationBean(servlet, "*.jsf");
		registration.setName("FacesServlet");
		registration.setLoadOnStartup(1);
		return registration;
	}

	@Bean
	public FilterRegistrationBean facesUploadFilterRegistration() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean(new FileUploadFilter(), facesServlet());
		registrationBean.setName("PrimeFaces FileUpload Filter");
		registrationBean.addUrlPatterns("/*");
		registrationBean.setDispatcherTypes(DispatcherType.FORWARD, DispatcherType.REQUEST);
		return registrationBean;
	}

	@Bean
	public ServletContextInitializer servletContextInitializer() {
		return servletContext -> {
			servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
			servletContext.setInitParameter("primefaces.FONT_AWESOME", "true");
			servletContext.setInitParameter("primefaces.THEME", "bootstrap");
			servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", Boolean.TRUE.toString());
			servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", Boolean.TRUE.toString());
			servletContext.setInitParameter("primefaces.UPLOADER", "commons");
			servletContext.setInitParameter("com.ocpsoft.pretty.BASE_PACKAGES", "none");
			servletContext.setInitParameter("primefaces.PUBLIC_CAPTCHA_KEY", "6LeIxAcTAAAAAJcZVRqyHh71UMIEGNQ_MXjiZKhI");
			servletContext.setInitParameter("primefaces.PRIVATE_CAPTCHA_KEY", "6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe");
		};
	}
}
