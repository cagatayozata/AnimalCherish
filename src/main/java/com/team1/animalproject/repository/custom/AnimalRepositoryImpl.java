package com.team1.animalproject.repository.custom;

import com.querydsl.core.Tuple;
import com.team1.animalproject.model.Animal;
import com.team1.animalproject.model.AnimalTarihce;
import com.team1.animalproject.model.AnimalTarihceDetay;
import com.team1.animalproject.model.GercekKisi;
import com.team1.animalproject.model.PetShop;
import com.team1.animalproject.model.QAnimal;
import com.team1.animalproject.model.QCins;
import com.team1.animalproject.model.QGercekKisi;
import com.team1.animalproject.model.QPetShop;
import com.team1.animalproject.model.QPetShopAnimal;
import com.team1.animalproject.model.QShelter;
import com.team1.animalproject.model.QShelterAnimal;
import com.team1.animalproject.model.QTur;
import com.team1.animalproject.model.QZoo;
import com.team1.animalproject.model.QZooAnimal;
import com.team1.animalproject.model.Shelter;
import com.team1.animalproject.model.Zoo;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.compress.utils.Lists;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimalRepositoryImpl extends QueryDslRepositorySupport implements CustomAnimalRepository {

	@PersistenceContext
	private EntityManager em;

	public AnimalRepositoryImpl(EntityManager entityManager) {
		super(Animal.class);
		em = entityManager;
		setEntityManager(em);
	}

	@Override
	public List<Animal> animalAra() {
		QCins qCins = QCins.cins;
		QTur qTur = QTur.tur;
		QAnimal qAnimal = QAnimal.animal;

		List<Tuple> tuples = from(qAnimal).select(qAnimal, qCins.name, qTur.name).leftJoin(qTur).on(qTur.id.eq(qAnimal.turId)).leftJoin(qCins).on(qCins.id.eq(qAnimal.cinsId)).fetch();

		List<Animal> animalList = Lists.newArrayList();
		tuples.stream().forEach(tuple -> {
			Animal animal = tuple.get(qAnimal);
			animal.setTurAd(tuple.get(qTur.name));
			animal.setCinsAd(tuple.get(qCins.name));
			animalList.add(animal);
		});

		return animalList;
	}

	@Override
	public Map<Integer, Long> sonYediGunIcinEklenenHayvanVerileriniGetir() {
		QAnimal qAnimal = QAnimal.animal;

		List<Tuple> tuples = from(qAnimal).select(qAnimal.count(), qAnimal.olusmaTarihi)
				.where(qAnimal.olusmaTarihi.gt(DateUtil.minusDays(DateUtil.nowAsDate(), 7)))
				.groupBy(qAnimal.olusmaTarihi)
				.fetch();

		Map<Integer, Long> veri = new HashMap<>();

		tuples.stream().forEach(tuple -> {
			int day = tuple.get(qAnimal.olusmaTarihi).getDay();
			if(veri.containsKey(day)){
				Long aLong = veri.get(day);
				aLong = tuple.get(qAnimal.count()) + aLong;
				veri.put(day, aLong);
			}else {
				veri.put(day, tuple.get(qAnimal.count()));
			}
		});

		return veri;
	}

	@Override
	public AnimalTarihceDetay hayvanNerede(String animalId){
		QAnimal qAnimal = QAnimal.animal;
		QZoo qZoo = QZoo.zoo;
		QZooAnimal qZooAnimal = QZooAnimal.zooAnimal;
		QShelter qShelter = QShelter.shelter;
		QShelterAnimal qShelterAnimal = QShelterAnimal.shelterAnimal;
		QPetShop qPetShop = QPetShop.petShop;
		QPetShopAnimal qPetShopAnimal = QPetShopAnimal.petShopAnimal;
		QGercekKisi qGercekKisi = QGercekKisi.gercekKisi;

		List<Tuple> tuples = from(qAnimal).select(qZoo, qShelter, qGercekKisi, qZooAnimal.olusmaTarihi, qShelterAnimal.olusmaTarihi, qPetShopAnimal.olusmaTarihi)
				.leftJoin(qZooAnimal)
				.on(qZooAnimal.animalId.eq(qAnimal.id))
				.leftJoin(qShelterAnimal)
				.on(qShelterAnimal.animalId.eq(qAnimal.id))
				.leftJoin(qPetShopAnimal)
				.on(qPetShopAnimal.animalId.eq(qAnimal.id))
				.leftJoin(qZoo)
				.on(qZoo.id.eq(qZooAnimal.zooId))
				.leftJoin(qShelter)
				.on(qShelter.id.eq(qShelterAnimal.shelterId))
				.leftJoin(qPetShop)
				.on(qPetShop.id.eq(qPetShopAnimal.petshopId))
				.leftJoin(qGercekKisi)
				.on(qAnimal.sahipId.eq(qGercekKisi.id))
				.where(qAnimal.id.eq(animalId))
				.fetch();

		AnimalTarihceDetay animalTarihceDetay = new AnimalTarihceDetay();

		tuples.forEach(tuple -> {
			if(tuple.get(qZoo) != null){
				Zoo zoo = tuple.get(qZoo);
				animalTarihceDetay.setKurumAdi(zoo.getName());
				animalTarihceDetay.setKurumaTanimlanmaTarihi(DateUtil.dateAsString(tuple.get(qZooAnimal.olusmaTarihi)));
			} else if(tuple.get(qPetShop) != null){
				PetShop petShop = tuple.get(qPetShop);
				animalTarihceDetay.setKurumAdi(petShop.getName());
				animalTarihceDetay.setKurumaTanimlanmaTarihi(DateUtil.dateAsString(tuple.get(qPetShopAnimal.olusmaTarihi)));
			} else if(tuple.get(qShelter) != null){
				Shelter shelter = tuple.get(qShelter);
				animalTarihceDetay.setKurumAdi(shelter.getName());
				animalTarihceDetay.setKurumaTanimlanmaTarihi(DateUtil.dateAsString(tuple.get(qShelterAnimal.olusmaTarihi)));
			} else if(tuple.get(qGercekKisi) != null){
				GercekKisi gercekKisi = tuple.get(qGercekKisi);
				animalTarihceDetay.setKurumAdi(gercekKisi.getAd());
				animalTarihceDetay.setKurumaTanimlanmaTarihi(DateUtil.dateAsString(gercekKisi.getOlusmaTarihi()));
			}else {
				animalTarihceDetay.setKurumAdi("Bilinmiyor");
			}
		});

		return animalTarihceDetay;
	}

}
