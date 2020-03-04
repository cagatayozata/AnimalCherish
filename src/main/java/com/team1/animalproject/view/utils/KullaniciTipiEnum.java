package com.team1.animalproject.view.utils;

import lombok.Getter;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Optional;

public enum KullaniciTipiEnum {

	ADMINISTRATOR(1, "Administrator"),
	NORMAL_USER(2, "Normal Kullanıcı"),
	VET(3, "Veteriner"),
	SHELTER(4, "Barınak Görevlisi"),
	PETSHOP(5, "Pet Shop Görevlisi"),
	ZOO(6, "Hayvanat Bahçesi Görevlisi"),
	NON_USER(7, "Kayıtsız");

	@Getter
	private Integer id;

	@Getter
	private String textMessageKey;

	KullaniciTipiEnum(Integer id, String textMessageKey) {
		this.id = id;
		this.textMessageKey = textMessageKey;
	}

	public static Optional<KullaniciTipiEnum> getById(Integer id) {
		if(ObjectUtils.isEmpty(id)){
			return Optional.empty();
		}
		return Arrays.stream(values()).filter(v -> v.getId().equals(id)).findAny();
	}

}