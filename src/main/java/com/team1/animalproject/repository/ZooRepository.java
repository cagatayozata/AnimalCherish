package com.team1.animalproject.repository;

import com.team1.animalproject.model.Zoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository ("zooRepository")
public interface ZooRepository extends JpaRepository<Zoo, String> {

	Optional<Zoo> findById(String id);
}
