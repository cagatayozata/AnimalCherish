package com.team1.animalproject.repository;

import com.team1.animalproject.model.Ilac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ilacRepository")
public interface IlacRepository extends JpaRepository<Ilac, String> {

    List<Ilac> findAllByDurum(boolean durum);

    Optional<Ilac> findById(String id);

}
