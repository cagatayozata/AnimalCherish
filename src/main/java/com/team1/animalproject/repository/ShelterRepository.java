package com.team1.animalproject.repository;

import com.team1.animalproject.model.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository ("shelterRepository")
public interface ShelterRepository extends JpaRepository<Shelter, String> {

	Optional<Shelter> findById(String id);
}
