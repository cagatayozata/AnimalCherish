package com.team1.animalproject.preparer;

import com.team1.animalproject.model.ZooAnimal;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class ZooAnimalPreparer {

	public static ZooAnimal olustur() {
		ZooAnimal zooAnimal = ZooAnimal.builder().id(UUID.randomUUID().toString()).animalId(RandomStringUtils.randomAlphabetic(10)).zooId(UUID.randomUUID().toString()).build();
		zooAnimal.setOlusmaTarihi(DateUtil.nowAsDate());
		zooAnimal.setOlusturanKullanici(UUID.randomUUID().toString());
		return zooAnimal;
	}

}
