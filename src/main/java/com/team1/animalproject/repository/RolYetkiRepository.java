package com.team1.animalproject.repository;

import com.team1.animalproject.model.RolYetki;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository ("rolYetkiRepository")
public interface RolYetkiRepository extends JpaRepository<RolYetki, String> {

	List<RolYetki> findByRolIdNot(String rolId);

	List<RolYetki> findByRolId(String rolId);

	List<RolYetki> findByRolIdIn(List<String> rolIds);

	void deleteByRolId(String id);

	Optional<RolYetki> findById(String id);
}
