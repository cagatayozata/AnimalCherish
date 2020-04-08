package com.team1.animalproject.preparer;

import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class UserPreparer {

	public static Kullanici olustur() {
		Kullanici kullanici = Kullanici.builder()
				.id(UUID.randomUUID().toString())
				.name(RandomStringUtils.randomAlphabetic(10))
				.email(RandomStringUtils.random(10))
				.keyPair(RandomStringUtils.randomAlphabetic(10))
				.kullaniciTipi(1)
				.password(RandomStringUtils.randomAlphabetic(10))
				.phoneNumber(RandomStringUtils.randomAlphabetic(10))
				.surname(RandomStringUtils.randomAlphabetic(10))
				.userName(RandomStringUtils.randomAlphabetic(5))
				.build();
		kullanici.setOlusmaTarihi(DateUtil.nowAsDate());
		kullanici.setOlusturanKullanici(UUID.randomUUID().toString());
		return kullanici;
	}

}
