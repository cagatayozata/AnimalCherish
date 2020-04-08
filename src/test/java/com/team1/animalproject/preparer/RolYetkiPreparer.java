package com.team1.animalproject.preparer;

import com.team1.animalproject.model.RolYetki;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class RolYetkiPreparer {

	public static RolYetki olustur() {
		RolYetki rolYetki = RolYetki.builder().id(UUID.randomUUID().toString()).rolId(RandomStringUtils.randomAlphabetic(10)).yetkiId(UUID.randomUUID().toString()).build();
		rolYetki.setOlusmaTarihi(DateUtil.nowAsDate());
		rolYetki.setOlusturanKullanici(UUID.randomUUID().toString());
		return rolYetki;
	}
}
