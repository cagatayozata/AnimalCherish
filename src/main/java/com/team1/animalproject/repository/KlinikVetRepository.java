package com.team1.animalproject.repository;

import com.team1.animalproject.model.KlinikVet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository ("klinikVetRepository")
public interface KlinikVetRepository extends JpaRepository<KlinikVet, String> {

	List<KlinikVet> findByKlinikIdAndVetId(String klinikId, String vetId);

	void deleteByKlinikId(String klinikId);

	Optional<KlinikVet> findById(String id);

	List<KlinikVet> findByKlinikId(String klinikId);
}
