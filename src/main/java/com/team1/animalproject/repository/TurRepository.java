package com.team1.animalproject.repository;

import com.team1.animalproject.model.Tur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("turRepository")
public interface TurRepository extends JpaRepository<Tur, String> {

    Tur findById(String id);

    List<Tur> findAllByDurum(boolean durum);

}
