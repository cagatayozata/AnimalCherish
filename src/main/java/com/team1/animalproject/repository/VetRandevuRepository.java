package com.team1.animalproject.repository;

import com.team1.animalproject.model.VetRandevu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository ("vetRandevuRepository")
public interface VetRandevuRepository extends JpaRepository<VetRandevu, String> {

	List<VetRandevu> findByVetId(String id);

	List<VetRandevu> findByRandevuAlanKullanici(String id);
}
