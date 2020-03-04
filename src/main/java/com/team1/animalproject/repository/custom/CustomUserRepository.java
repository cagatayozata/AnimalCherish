package com.team1.animalproject.repository.custom;

import com.team1.animalproject.view.utils.KullaniciTipiEnum;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomUserRepository {

	boolean kullaniciBaskaYerdeGorevliMi(String kullaniciId, KullaniciTipiEnum kullaniciTipiEnum);
}
