package com.team1.animalproject.service;

import com.team1.animalproject.model.Animal;
import com.team1.animalproject.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService implements IBaseService<Animal> {

	@Qualifier("animalRepository")
	@Autowired
	private AnimalRepository animalRepository;

	@Autowired
	private TurService turService;

	@Autowired
	private CinsService cinsService;

	@Override
	public List<Animal> getAll() {
		List<Animal> all = animalRepository.findAll();
		if(all != null)
			all.stream().forEach(animal -> {
				animal.setTurAd(turService.findById(animal.getTurId()).getName());
				animal.setCinsAd(cinsService.findById(animal.getCinsId()).getName());
			});
		return all;
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

}
