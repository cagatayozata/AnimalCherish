package com.team1.animalproject.repository;

import com.team1.animalproject.model.Cins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("cinsRepository")
public interface CinsRepository extends JpaRepository<Cins, String> {
    Optional<List<Cins>> findByTurId(String turId);

    Cins findById(String id);

    void deleteByTurId(String turId);
}
