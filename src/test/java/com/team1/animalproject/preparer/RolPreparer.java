package com.team1.animalproject.preparer;

import com.team1.animalproject.model.Rol;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class RolPreparer {

	public static Rol olustur() {
		Rol rol = Rol.builder().id(UUID.randomUUID().toString()).name(RandomStringUtils.randomAlphabetic(10)).build();
		rol.setOlusmaTarihi(DateUtil.nowAsDate());
		rol.setOlusturanKullanici(UUID.randomUUID().toString());
		return rol;
	}

}
