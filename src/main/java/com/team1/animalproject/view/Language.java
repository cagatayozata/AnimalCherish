package com.team1.animalproject.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings ("ALL")
@ManagedBean (name = "language")
@SessionScoped
@Controller
public class Language {

	final Map<String, Locale> languages = new HashMap<>();
	private final Locale locale;
	@Autowired
	private MessageSource messageSource;
	private String localeCode;

	public Language() {
		super();
		languages.put("en", Locale.ENGLISH);
		this.localeCode = "en";
		this.locale = new Locale("en");
	}

	public String getMessage(String property) {
		return messageSource.getMessage(property, null, this.locale);
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