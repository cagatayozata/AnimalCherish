package com.team1.animalproject.preparer;

import com.team1.animalproject.model.PetShop;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class PetShopPreparer {

	public static PetShop olustur() {
		PetShop petShop = PetShop.builder()
				.id(UUID.randomUUID().toString())
				.name(RandomStringUtils.randomAlphabetic(10))
				.address(RandomStringUtils.randomAlphabetic(10))
				.birthdate(DateUtil.nowAsDate())
				.details(RandomStringUtils.random(10))
				.email(RandomStringUtils.random(10))
				.phone(RandomStringUtils.random(10))
				.workerCount(1)
				.build();
		petShop.setOlusmaTarihi(DateUtil.nowAsDate());
		petShop.setOlusturanKullanici(UUID.randomUUID().toString());
		return petShop;
	}

}
