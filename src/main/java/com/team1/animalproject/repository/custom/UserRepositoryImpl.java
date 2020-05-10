package com.team1.animalproject.repository.custom;

import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.model.QPetShopWorker;
import com.team1.animalproject.model.QShelterWorker;
import com.team1.animalproject.model.QZooWorker;
import com.team1.animalproject.view.utils.KullaniciTipiEnum;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class UserRepositoryImpl extends QueryDslRepositorySupport implements CustomUserRepository {

	@PersistenceContext
	private final EntityManager em;

	public UserRepositoryImpl(EntityManager entityManager) {
		super(Kullanici.class);
		em = entityManager;
		setEntityManager(em);
	}

	@Override
	public boolean kullaniciBaskaYerdeGorevliMi(String kullaniciId, KullaniciTipiEnum kullaniciTipiEnum) {

		boolean kullaniciGorevlimi = false;

		QShelterWorker qShelterWorker = QShelterWorker.shelterWorker;
		QZooWorker qZooWorker = QZooWorker.zooWorker;
		QPetShopWorker qPetShopWorker = QPetShopWorker.petShopWorker;

		if(!KullaniciTipiEnum.PETSHOP.equals(kullaniciTipiEnum)) kullaniciGorevlimi = from(qPetShopWorker).select(qPetShopWorker.id).where(qPetShopWorker.workerId.eq(kullaniciId)).fetchCount() != 0;

		if(!KullaniciTipiEnum.ZOO.equals(kullaniciTipiEnum))
			if(!kullaniciGorevlimi) kullaniciGorevlimi = from(qZooWorker).select(qZooWorker.id).where(qZooWorker.workerId.eq(kullaniciId)).fetchCount() != 0;

		if(!KullaniciTipiEnum.SHELTER.equals(kullaniciTipiEnum))
			if(!kullaniciGorevlimi) kullaniciGorevlimi = from(qShelterWorker).select(qShelterWorker.id).where(qShelterWorker.workerId.eq(kullaniciId)).fetchCount() != 0;

		return kullaniciGorevlimi;
	}
}
