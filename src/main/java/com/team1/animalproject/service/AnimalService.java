package com.team1.animalproject.service;

import com.team1.animalproject.model.Animal;
import com.team1.animalproject.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService implements IBaseService<Animal> {

	@Qualifier("animalRepository")
	@Autowired
	private AnimalRepository animalRepository;

	@Override
	public List<Animal> getAll() {
		return animalRepository.findAll();
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
}
