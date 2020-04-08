package com.team1.animalproject.repository;

import com.team1.animalproject.model.PetShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetShopRepository extends JpaRepository<PetShop, String> {

	Optional<PetShop> findById(String id);
}
