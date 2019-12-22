package com.team1.animalproject.repository;

import com.team1.animalproject.model.Animal;
import com.team1.animalproject.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("rolRepository")
public interface RolRepository extends JpaRepository<Rol, String> {

}
