package com.team1.animalproject.repository;

import com.team1.animalproject.model.Klinik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KlinikRepository extends JpaRepository<Klinik, String> {

	Optional<Klinik> findById(String id);
}
