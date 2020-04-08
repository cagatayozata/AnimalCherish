package com.team1.animalproject.preparer;

import com.team1.animalproject.model.Vet;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class VetPreparer {

	public static Vet olustur() {
		Vet vet = Vet.builder()
				.id(UUID.randomUUID().toString())
				.name(RandomStringUtils.randomAlphabetic(10))
				.birthdate(DateUtil.nowAsDate())
				.details(RandomStringUtils.random(10))
				.email(RandomStringUtils.random(10))
				.phone(RandomStringUtils.random(10))
				.workplace(RandomStringUtils.randomAlphabetic(10))
				.sicilNo(RandomStringUtils.randomAlphabetic(10))
				.city(RandomStringUtils.randomAlphabetic(10))
				.clinic(RandomStringUtils.randomAlphabetic(10))
				.diplomaNo(RandomStringUtils.randomAlphabetic(10))
				.education(RandomStringUtils.randomAlphabetic(10))
				.ilce(RandomStringUtils.randomAlphabetic(10))
				.build();
		vet.setOlusmaTarihi(DateUtil.nowAsDate());
		vet.setOlusturanKullanici(UUID.randomUUID().toString());
		return vet;
	}

}
