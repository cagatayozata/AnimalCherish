package com.team1.animalproject.preparer;

import com.team1.animalproject.model.ShelterAnimal;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class ShelterAnimalPreparer {

	public static ShelterAnimal olustur() {
		ShelterAnimal shelterAnimal = ShelterAnimal.builder().id(UUID.randomUUID().toString()).animalId(RandomStringUtils.randomAlphabetic(10)).shelterId(UUID.randomUUID().toString()).build();
		shelterAnimal.setOlusmaTarihi(DateUtil.nowAsDate());
		shelterAnimal.setOlusturanKullanici(UUID.randomUUID().toString());
		return shelterAnimal;
	}

}
