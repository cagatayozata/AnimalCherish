package com.team1.animalproject.service;

import com.team1.animalproject.model.Animal;
import com.team1.animalproject.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AnimalService implements IBaseService<Animal> {

	@Qualifier ("animalRepository")
	@Autowired
	private AnimalRepository animalRepository;

	@Override
	public List<Animal> getAll() {
		return animalRepository.animalAra();
	}

	@Override
	public void save(Animal o) {
		animalRepository.save(o);
	}

	@Override
	public void update(Animal o) {
		animalRepository.save(o);
	}

	@Override
	public void delete(List<Animal> o) {
		animalRepository.delete(o);
	}

	public Optional<List<Animal>> findByIdIn(List<String> ids) {
		return animalRepository.findByIdIn(ids);
	}

	public Map<Integer, Long> sonYediGunIcinEklenenHayvanVerileriniGetir(){
		return animalRepository.sonYediGunIcinEklenenHayvanVerileriniGetir();
	}

}
