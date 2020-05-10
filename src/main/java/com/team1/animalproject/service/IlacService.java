package com.team1.animalproject.service;

import com.team1.animalproject.model.Ilac;
import com.team1.animalproject.repository.IlacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@SuppressWarnings ("OptionalGetWithoutIsPresent")
@Service
public class IlacService implements IBaseService<Ilac> {

	@Qualifier ("ilacRepository")
	@Autowired
	private IlacRepository ilacRepository;

	@Override
	public List<Ilac> getAll() {
		return ilacRepository.findAllByDurum(true);
	}

	public List<Ilac> ara() {
		return ilacRepository.findAll();
	}

	@Override
	public void save(Ilac ilac) {
		if(ilac.getId() == null) ilac.setId(UUID.randomUUID().toString());
		if(!ilac.isDurum()){
			ilac.setDurum(false);
		}
		ilacRepository.save(ilac);
	}

	@Override
	public void update(Ilac ilac) {
		ilacRepository.save(ilac);
	}

	@Override
	public void delete(List<Ilac> t) {
		ilacRepository.delete(t);
	}

	public Ilac findById(String id) {
		return ilacRepository.findById(id).get();
	}
}