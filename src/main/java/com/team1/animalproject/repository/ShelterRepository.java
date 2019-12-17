package com.team1.animalproject.repository;

import com.team1.animalproject.model.Shelter;
import com.team1.animalproject.model.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("shelterRepository")
public interface ShelterRepository extends JpaRepository<Shelter, String> {

}
