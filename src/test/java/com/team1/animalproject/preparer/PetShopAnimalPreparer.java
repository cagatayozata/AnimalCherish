package com.team1.animalproject.preparer;

import com.team1.animalproject.model.PetShopAnimal;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class PetShopAnimalPreparer {

	public static PetShopAnimal olustur() {
		PetShopAnimal petShopAnimal = PetShopAnimal.builder().id(UUID.randomUUID().toString()).animalId(RandomStringUtils.randomAlphabetic(10)).petshopId(UUID.randomUUID().toString()).build();
		petShopAnimal.setOlusmaTarihi(DateUtil.nowAsDate());
		petShopAnimal.setOlusturanKullanici(UUID.randomUUID().toString());
		return petShopAnimal;
	}

}
