package com.team1.animalproject.repository;

import com.team1.animalproject.model.Cins;
import com.team1.animalproject.repository.custom.CustomCinsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository ("cinsRepository")
public interface CinsRepository extends JpaRepository<Cins, String>, CustomCinsRepository {

	Optional<List<Cins>> findByTurId(String turId);

	Optional<Cins> findById(String id);

	void deleteByTurId(String turId);

	List<Cins> findAllByDurum(boolean durum);
}
