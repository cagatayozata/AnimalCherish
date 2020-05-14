package com.team1.animalproject.repository;

import com.team1.animalproject.model.Animal;
import com.team1.animalproject.repository.custom.CustomAnimalRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository ("animalRepository")
public interface AnimalRepository extends JpaRepository<Animal, String>, CustomAnimalRepository {

	Optional<List<Animal>> findByIdIn(List<String> ids);

}
