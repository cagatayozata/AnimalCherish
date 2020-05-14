package com.team1.animalproject.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum BaseExceptionType {

	// API CODES

	BASE_EXCEPTION("001", "Bir Hata Oluştu"),
	KULLANICI_ADI_MAIL_PHONE_KULLANILIYOR("002", "Kullanıcı adı, email veya telefon numaralarından birisi kullanılmaktadır"),
	KULLANICI_ZATEN_GOREVLI("003", "Eklemek istediğiniz kullanıcılardan birisi zaten başka bir yerde zaten görevlidir. Lütfen kontrol ediniz!"),

	VIEW_EXCEPTION("300", "validator.generic.view.exception");

	@Getter
	private final String code;

	@Getter
	private final String validationMessage;

	BaseExceptionType(String code, String validationMessage) {
		this.code = code;
		this.validationMessage = validationMessage;
		//		this.params = new ArrayList<>();
	}

	@JsonCreator
	public static BaseExceptionType forCode(String value) {
		for(BaseExceptionType exceptionType : values()){
			if(exceptionType.code.equalsIgnoreCase(value)){
				return exceptionType;
			}
		}
		return null;
	}

	public static BaseExceptionType forMessage(String validationMessage) {
		if(validationMessage == null){
			return null;
		}
		for(BaseExceptionType exceptionType : values()){
			if(exceptionType.validationMessage.equalsIgnoreCase(validationMessage)){
				return exceptionType;
			}
		}
		return null;
	}

	@JsonValue
	public String toValue() {
		return code;
	}

}
