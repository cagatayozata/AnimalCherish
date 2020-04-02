package com.team1.animalproject.repository;

import com.team1.animalproject.model.GercekKisi;
import com.team1.animalproject.model.Ilac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository ("gercekKisiRepository")
public interface GercekKisiRepository extends JpaRepository<GercekKisi, String> {

	Optional<GercekKisi> findById(String id);

	Optional<GercekKisi> findByKimlikNo(String kimlikNo);
}
