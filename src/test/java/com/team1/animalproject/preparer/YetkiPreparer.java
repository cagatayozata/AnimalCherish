package com.team1.animalproject.preparer;

import com.team1.animalproject.model.Yetki;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class YetkiPreparer {

	public static Yetki olustur() {
		Yetki yetki = Yetki.builder().id(UUID.randomUUID().toString()).kod(RandomStringUtils.randomAlphabetic(10)).name(RandomStringUtils.randomAlphabetic(10)).build();
		yetki.setOlusmaTarihi(DateUtil.nowAsDate());
		yetki.setOlusturanKullanici(UUID.randomUUID().toString());
		return yetki;
	}
}
