package com.team1.animalproject.repository;

import com.team1.animalproject.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("animalRepository")
public interface AnimalRepository extends JpaRepository<Animal, String> {

}
