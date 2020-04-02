package com.team1.animalproject.preparer;

import com.team1.animalproject.model.Ilac;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class IlacPreparer {

	public static Ilac olustur() {
		Ilac ilac = Ilac.builder().name(RandomStringUtils.randomAlphabetic(30)).id(UUID.randomUUID().toString()).description(RandomStringUtils.randomAlphabetic(10)).durum(true).build();
		ilac.setOlusmaTarihi(DateUtil.nowAsDate());
		ilac.setOlusturanKullanici(UUID.randomUUID().toString());
		return ilac;
	}

}
