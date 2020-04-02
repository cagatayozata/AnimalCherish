package com.team1.animalproject.repository.custom;

import com.querydsl.core.Tuple;
import com.team1.animalproject.model.Cins;
import com.team1.animalproject.model.QCins;
import com.team1.animalproject.model.QTur;
import org.apache.commons.compress.utils.Lists;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CinsRepositoryImpl extends QueryDslRepositorySupport implements CustomCinsRepository {

	@PersistenceContext
	private EntityManager em;

	public CinsRepositoryImpl(EntityManager entityManager) {
		super(Cins.class);
		em = entityManager;
		setEntityManager(em);
	}

	@Override
	public List<Cins> cinsAra() {
		QCins qCins = QCins.cins;
		QTur qTur = QTur.tur;

		List<Tuple> tuples = from(qCins).select(qCins, qTur.name).leftJoin(qTur).on(qTur.id.eq(qCins.turId)).fetch();

		List<Cins> cinsList = Lists.newArrayList();
		tuples.stream().forEach(tuple -> {
			Cins cins = tuple.get(qCins);
			cins.setTurAd(tuple.get(qTur.name));
			cinsList.add(cins);
		});

		return cinsList;
	}

}
