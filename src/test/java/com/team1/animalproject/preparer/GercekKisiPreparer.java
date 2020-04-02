package com.team1.animalproject.preparer;

import com.team1.animalproject.model.GercekKisi;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class GercekKisiPreparer {

	public static GercekKisi olustur() {
		GercekKisi gercekKisi = GercekKisi.builder()
				.ad(RandomStringUtils.randomAlphabetic(30))
				.id(UUID.randomUUID().toString())
				.adresi(RandomStringUtils.randomAlphabetic(10))
				.dogumTarihi(DateUtil.nowAsDate())
				.kimlikNo(RandomStringUtils.randomNumeric(10))
				.telefon(RandomStringUtils.randomAlphabetic(10))
				.build();

		gercekKisi.setOlusmaTarihi(DateUtil.nowAsDate());
		gercekKisi.setOlusturanKullanici(UUID.randomUUID().toString());
		return gercekKisi;
	}

}
