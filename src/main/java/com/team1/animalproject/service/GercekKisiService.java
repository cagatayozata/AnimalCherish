package com.team1.animalproject.service;

import com.team1.animalproject.model.GercekKisi;
import com.team1.animalproject.repository.GercekKisiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings ("OptionalGetWithoutIsPresent")
@Service
public class GercekKisiService implements IBaseService<GercekKisi> {

	@Qualifier ("gercekKisiRepository")
	@Autowired
	private GercekKisiRepository gercekKisiRepository;

	@Override
	public List<GercekKisi> getAll() {
		return gercekKisiRepository.findAll();
	}

	@Override
	public void save(GercekKisi gercekKisi) {
		if(gercekKisi.getId() == null) gercekKisi.setId(UUID.randomUUID().toString());
		gercekKisiRepository.save(gercekKisi);
	}

	@Override
	public void update(GercekKisi gercekKisi) {
		gercekKisiRepository.save(gercekKisi);
	}

	@Override
	public void delete(List<GercekKisi> t) {
		gercekKisiRepository.delete(t);
	}

	public GercekKisi findById(String id) {
		return gercekKisiRepository.findById(id).get();
	}

	public Optional<GercekKisi> findByKimlikNo(String no) {
		return gercekKisiRepository.findByKimlikNo(no);
	}
}