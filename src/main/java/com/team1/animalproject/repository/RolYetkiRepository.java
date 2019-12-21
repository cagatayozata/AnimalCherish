package com.team1.animalproject.repository;

import com.team1.animalproject.model.RolYetki;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("rolYetkiRepository")
public interface RolYetkiRepository extends JpaRepository<RolYetki, String> {

    List<RolYetki> findByRolIdNotIn(String rolId);

    List<RolYetki> findByRolIdIn(String rolId);


}
