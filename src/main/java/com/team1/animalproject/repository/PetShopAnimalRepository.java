package com.team1.animalproject.repository;

import com.team1.animalproject.model.PetShopAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository ("petShopAnimalRepository")
public interface PetShopAnimalRepository extends JpaRepository<PetShopAnimal, String> {

	List<PetShopAnimal> findByPetshopId(String petShopId);

	List<PetShopAnimal> findByPetshopIdAndAnimalId(String petShopId, String animalId);

	void deleteByPetshopId(String shelterId);

	Optional<PetShopAnimal> findById(String id);
}
