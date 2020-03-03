package com.team1.animalproject.repository.custom;

import com.team1.animalproject.model.Animal;
import com.team1.animalproject.model.Cins;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface CustomAnimalRepository {

	List<Animal> animalAra();

	Map<Integer, Long> sonYediGunIcinEklenenHayvanVerileriniGetir();
}
