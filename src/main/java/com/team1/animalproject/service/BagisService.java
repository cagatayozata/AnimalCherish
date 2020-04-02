package com.team1.animalproject.service;

import com.team1.animalproject.model.Bagis;
import com.team1.animalproject.repository.BagisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BagisService implements IBaseService<Bagis> {

	@Qualifier ("bagisRepository")
	@Autowired
	private BagisRepository bagisRepository;

	@Override
	public List<Bagis> getAll() {
		List<Bagis> bagislar = bagisRepository.findAll();
		return bagisRepository.findAll();
	}

	@Override
	public void save(Bagis bagis) {
		if(bagis.getId() == null) bagis.setId(UUID.randomUUID().toString());
		bagisRepository.save(bagis);
	}

	@Override
	public void update(Bagis bagis) {
		bagisRepository.save(bagis);
	}

	@Override
	public void delete(List<Bagis> t) {
		bagisRepository.delete(t);
	}

	public Bagis findById(String id) {
		return bagisRepository.findById(id);
	}
}