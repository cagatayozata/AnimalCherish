package com.team1.animalproject.view;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;

@ManagedBean(name = "language")
@SessionScoped
@Controller
public class Language {

	@Autowired
	private MessageSource messageSource;
	private Locale locale;

	Map<String, Locale> languages = new HashMap<String, Locale>();

	private String localeCode;

	public Language() {
		super();
		languages.put("en", Locale.ENGLISH);
		this.localeCode = "en";
		this.locale = new Locale("en");
	}

	public String getMessage(String property) {
		String message = messageSource.getMessage(property, null, this.locale);
		return message;
	}

	public Map<String, Locale> getLanguages() {
		return languages;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

}