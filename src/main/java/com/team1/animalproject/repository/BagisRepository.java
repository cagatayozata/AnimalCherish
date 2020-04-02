package com.team1.animalproject.repository;

import com.team1.animalproject.model.Bagis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository ("bagisRepository")
public interface BagisRepository extends JpaRepository<Bagis, String> {

	Bagis findById(String id);
}
