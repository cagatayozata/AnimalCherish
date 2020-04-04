package com.team1.animalproject.preparer;

import com.team1.animalproject.model.Tur;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class TurPreparer {

	public static Tur olustur(boolean durum){
		Tur tur = Tur.builder().name(RandomStringUtils.randomAlphabetic(30)).id(UUID.randomUUID().toString()).durum(durum).description(RandomStringUtils.randomAlphabetic(30)).build();
		tur.setOlusmaTarihi(DateUtil.nowAsDate());
		tur.setOlusturanKullanici(UUID.randomUUID().toString());
		return tur;
	}

}
