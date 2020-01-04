package com.team1.animalproject.repository;

import com.team1.animalproject.model.PetShopAnimal;
import com.team1.animalproject.model.ShelterAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("petShopAnimalRepository")
public interface PetShopAnimalRepository extends JpaRepository<PetShopAnimal, String> {

    List<PetShopAnimal> findByPetshopId(String petShopId);

    List<PetShopAnimal> findByPetshopIdAndAnimalId(String petShopId, String animalId);

    void deleteByPetshopId(String shelterId);

}
