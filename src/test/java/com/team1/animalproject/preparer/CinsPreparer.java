package com.team1.animalproject.preparer;

import com.team1.animalproject.model.Cins;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class CinsPreparer {

	public static Cins olustur(){
		Cins cins = Cins.builder().name(RandomStringUtils.randomAlphabetic(30))
				.id(UUID.randomUUID().toString()).durum(true)
				.description(RandomStringUtils.randomAlphabetic(30))
				.turId(TurPreparer.olustur(true).getId())
				.build();
		cins.setOlusmaTarihi(DateUtil.nowAsDate());
		cins.setOlusturanKullanici(UUID.randomUUID().toString());
		return cins;
	}

}
