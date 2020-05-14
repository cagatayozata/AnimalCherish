package com.team1.animalproject.service;

import com.team1.animalproject.model.Cins;
import com.team1.animalproject.repository.CinsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings ("OptionalGetWithoutIsPresent")
@Service
public class CinsService implements IBaseService<Cins> {

	@Qualifier ("cinsRepository")
	@Autowired
	private CinsRepository cinsRepository;

	@Autowired
	private TurService turService;

	@Override
	public List<Cins> getAll() {
		return cinsRepository.findAllByDurum(true);
	}

	public List<Cins> ara() {
		return cinsRepository.cinsAra();
	}

	public List<Cins> findByTurId(String turId) {
		List<Cins> cins = new ArrayList<>();
		Optional<List<Cins>> byTurId = cinsRepository.findByTurId(turId);
		if(byTurId.isPresent()){
			cins = byTurId.get();
		}
		return cins;
	}

	@Override
	public void save(Cins cins) {
		if(cins.getId() == null) cins.setId(UUID.randomUUID().toString());
		if(!cins.isDurum()){
			cins.setDurum(false);
		}
		cinsRepository.save(cins);
	}

	@Override
	public void update(Cins cins) {
		cinsRepository.save(cins);
	}

	@Override
	public void delete(List<Cins> t) {
		cinsRepository.delete(t);
	}

	@Transactional
	public void deleteByTurId(String turId) {
		cinsRepository.deleteByTurId(turId);
	}

	public Cins findById(String id) {
		return cinsRepository.findById(id).get();
	}
}