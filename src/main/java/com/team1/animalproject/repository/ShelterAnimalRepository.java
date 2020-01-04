package com.team1.animalproject.repository;

import com.team1.animalproject.model.ShelterAnimal;
import com.team1.animalproject.model.ShelterWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("shelterAnimalRepository")
public interface ShelterAnimalRepository extends JpaRepository<ShelterAnimal, String> {

    List<ShelterAnimal> findByShelterId(String shelterId);

    List<ShelterAnimal> findByShelterIdAndAnimalId(String shelterId, String animalId);

    void deleteByShelterId(String shelterId);

}
