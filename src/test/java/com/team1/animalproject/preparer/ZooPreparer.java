package com.team1.animalproject.preparer;

import com.team1.animalproject.model.Zoo;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class ZooPreparer {

	public static Zoo olustur() {
		Zoo zoo = Zoo.builder()
				.id(UUID.randomUUID().toString())
				.name(RandomStringUtils.randomAlphabetic(10))
				.address(RandomStringUtils.randomAlphabetic(10))
				.description(RandomStringUtils.randomAlphabetic(10))
				.email(RandomStringUtils.random(10))
				.establishDate(DateUtil.nowAsDate())
				.workerCount(1)
				.phone(RandomStringUtils.random(10))
				.workers(RandomStringUtils.randomAlphabetic(10))
				.build();
		zoo.setOlusmaTarihi(DateUtil.nowAsDate());
		zoo.setOlusturanKullanici(UUID.randomUUID().toString());
		return zoo;
	}

}
