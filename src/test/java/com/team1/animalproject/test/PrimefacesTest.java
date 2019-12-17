package com.team1.animalproject.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.team1.animalproject.SpringPrimeApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringPrimeApplication.class)
public class PrimefacesTest {

	@Autowired
	SpringPrimeApplication springPrimeApplication;

	@Test
	public void testServletRegistrationBean() {
		ServletRegistrationBean servletRegistrationBean = springPrimeApplication.facesServletRegistraiton();
		assertEquals(servletRegistrationBean.getServletName(), "Faces Servlet");
		assertEquals(servletRegistrationBean.getUrlMappings().toArray()[0], "*.xhtml");
	}

	@Test
	public void testFilterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = springPrimeApplication.facesUploadFilterRegistration();
		assertTrue(filterRegistrationBean.getUrlPatterns().contains("/*"));
	}
	

}
