package com.team1.animalproject.repository;

import com.team1.animalproject.model.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("vetRepository")
public interface VetRepository extends JpaRepository<Vet, String> {

	Vet findByKullaniciId(String kullaniciId);

}
