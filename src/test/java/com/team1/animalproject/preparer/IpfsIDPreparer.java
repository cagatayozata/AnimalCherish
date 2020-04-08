package com.team1.animalproject.preparer;

import com.team1.animalproject.model.IpfsID;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile (value = {"test"})
@Component
public class IpfsIDPreparer {

	public static IpfsID olustur() {
		IpfsID ipfsID = IpfsID.builder().id(UUID.randomUUID().toString()).ipfsHash(RandomStringUtils.randomAlphabetic(10)).build();
		ipfsID.setOlusmaTarihi(DateUtil.nowAsDate());
		ipfsID.setOlusturanKullanici(UUID.randomUUID().toString());
		return ipfsID;
	}

}
