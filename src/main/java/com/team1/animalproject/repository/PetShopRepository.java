package com.team1.animalproject.repository;

import com.team1.animalproject.model.PetShop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetShopRepository extends JpaRepository<PetShop, String> {
}
