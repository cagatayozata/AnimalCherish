package com.team1.animalproject.preparer;

import com.team1.animalproject.model.PetShopWorker;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class PetShopWorkerPreparer {

	public static PetShopWorker olustur() {
		PetShopWorker petShopWorker = PetShopWorker.builder().id(UUID.randomUUID().toString()).petShopId(RandomStringUtils.randomAlphabetic(10)).workerId(UUID.randomUUID().toString()).build();
		petShopWorker.setOlusmaTarihi(DateUtil.nowAsDate());
		petShopWorker.setOlusturanKullanici(UUID.randomUUID().toString());
		return petShopWorker;
	}

}
