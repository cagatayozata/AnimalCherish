package com.team1.animalproject.view.utils;

import lombok.Getter;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Optional;

public enum DayEnum {

	PAZARTESI(1, "Pazartesi"),
	SALI(2, "Salı"),
	CARSAMBA(3, "Çarşamba"),
	PERSEMBE(4, "Perşembe"),
	CUMA(5, "Cuma"),
	CUMARTESI(6, "Cumartesi"),
	PAZAR(7, "Pazar");

	@Getter
	private Integer id;

	@Getter
	private String textMessageKey;

	DayEnum(Integer id, String textMessageKey) {
		this.id = id;
		this.textMessageKey = textMessageKey;
	}

	public static Optional<DayEnum> getById(Integer id) {
		if(ObjectUtils.isEmpty(id)){
			return Optional.empty();
		}
		return Arrays.stream(values()).filter(v -> v.getId().equals(id)).findAny();
	}

}