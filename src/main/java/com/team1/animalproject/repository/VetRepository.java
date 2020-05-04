package com.team1.animalproject.repository;

import com.team1.animalproject.model.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository ("vetRepository")
public interface VetRepository extends JpaRepository<Vet, String> {

	Vet findByKullaniciId(String kullaniciId);

	Optional<Vet> findById(String id);

	Optional<List<Vet>> findByIdIn(List<String> collect);

}
