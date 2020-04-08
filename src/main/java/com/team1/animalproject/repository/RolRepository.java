package com.team1.animalproject.repository;

import com.team1.animalproject.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository ("rolRepository")
public interface RolRepository extends JpaRepository<Rol, String> {

	Optional<Rol> findById(String id);
}
