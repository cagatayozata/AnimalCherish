package com.team1.animalproject.repository;

import com.team1.animalproject.model.Ilac;
import com.team1.animalproject.model.Tur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ilacRepository")
public interface IlacRepository extends JpaRepository<Ilac, String> {

    List<Ilac> findAllByDurum(boolean durum);

    Ilac findById(String id);

}
