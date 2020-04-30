package com.team1.animalproject.repository;

import com.team1.animalproject.model.AnimalTarihce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository ("animalTarihceRepository")
public interface AnimalTarihceRepository extends JpaRepository<AnimalTarihce, String> {

	List<AnimalTarihce> findByAnimalId(String animalId);

}
