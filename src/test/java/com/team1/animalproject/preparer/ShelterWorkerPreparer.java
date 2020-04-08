package com.team1.animalproject.preparer;

import com.team1.animalproject.model.ShelterWorker;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class ShelterWorkerPreparer {

	public static ShelterWorker olustur() {
		ShelterWorker shelterWorker = ShelterWorker.builder().id(UUID.randomUUID().toString()).shelterId(RandomStringUtils.randomAlphabetic(10)).workerId(UUID.randomUUID().toString()).build();
		shelterWorker.setOlusmaTarihi(DateUtil.nowAsDate());
		shelterWorker.setOlusturanKullanici(UUID.randomUUID().toString());
		return shelterWorker;
	}

}
