package com.team1.animalproject.preparer;

import com.team1.animalproject.model.KullaniciRol;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class KullaniciRolPreparer {

	public static KullaniciRol olustur() {
		KullaniciRol kullaniciRol = KullaniciRol.builder().id(UUID.randomUUID().toString()).kullaniciId(RandomStringUtils.randomAlphabetic(10)).rolId(UUID.randomUUID().toString()).build();
		kullaniciRol.setOlusmaTarihi(DateUtil.nowAsDate());
		kullaniciRol.setOlusturanKullanici(UUID.randomUUID().toString());
		return kullaniciRol;
	}

}
