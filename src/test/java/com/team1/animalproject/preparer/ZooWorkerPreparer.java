package com.team1.animalproject.preparer;

import com.team1.animalproject.model.ZooAnimal;
import com.team1.animalproject.model.ZooWorker;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class ZooWorkerPreparer {

	public static ZooWorker olustur() {
		ZooWorker zooWorker = ZooWorker.builder().id(UUID.randomUUID().toString()).workerId(RandomStringUtils.randomAlphabetic(10)).zooId(UUID.randomUUID().toString()).build();
		zooWorker.setOlusmaTarihi(DateUtil.nowAsDate());
		zooWorker.setOlusturanKullanici(UUID.randomUUID().toString());
		return zooWorker;
	}

}
