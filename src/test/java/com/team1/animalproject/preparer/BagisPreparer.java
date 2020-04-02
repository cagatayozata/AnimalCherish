package com.team1.animalproject.preparer;

import com.team1.animalproject.model.Bagis;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class BagisPreparer {

	public static Bagis olustur() {
		Bagis bagis = Bagis.builder()
				.name(RandomStringUtils.randomAlphabetic(30))
				.id(UUID.randomUUID().toString())
				.description(RandomStringUtils.randomAlphabetic(10))
				.endDate(DateUtil.nowAsDate())
				.startDate(DateUtil.nowAsDate())
				.iban(RandomStringUtils.randomAlphabetic(10))
				.build();
		bagis.setOlusmaTarihi(DateUtil.nowAsDate());
		bagis.setOlusturanKullanici(UUID.randomUUID().toString());
		return bagis;
	}

}
