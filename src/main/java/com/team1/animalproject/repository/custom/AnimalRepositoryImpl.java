package com.team1.animalproject.repository.custom;

import com.querydsl.core.Tuple;
import com.team1.animalproject.model.Animal;
import com.team1.animalproject.model.QAnimal;
import com.team1.animalproject.model.QCins;
import com.team1.animalproject.model.QTur;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.compress.utils.Lists;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

}
