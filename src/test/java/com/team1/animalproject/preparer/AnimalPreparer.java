package com.team1.animalproject.preparer;

import com.team1.animalproject.model.Animal;
import com.team1.animalproject.model.Cins;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class AnimalPreparer {

	public static Animal olustur(){

		Animal animal = Animal.builder()
				.id(UUID.randomUUID().toString())
				.address(RandomStringUtils.randomAlphabetic(30))
				.birthdate(DateUtil.nowAsDate())
				.name(RandomStringUtils.randomAlphabetic(30))
				.build();

		Cins cins = CinsPreparer.olustur();
		animal.setTurId(cins.getTurId());
		animal.setCinsId(cins.getId());

		animal.setOlusmaTarihi(DateUtil.nowAsDate());
		animal.setOlusturanKullanici(UUID.randomUUID().toString());
		return animal;
	}

}
