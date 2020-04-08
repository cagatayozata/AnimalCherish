package com.team1.animalproject.repository;

import com.team1.animalproject.model.ShelterAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository ("shelterAnimalRepository")
public interface ShelterAnimalRepository extends JpaRepository<ShelterAnimal, String> {

	List<ShelterAnimal> findByShelterId(String shelterId);

	List<ShelterAnimal> findByShelterIdAndAnimalId(String shelterId, String animalId);

	void deleteByShelterId(String shelterId);

	Optional<ShelterAnimal> findById(String id);
}
