package com.team1.animalproject.repository;

import com.team1.animalproject.model.Yetki;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("yetkiRepository")
public interface YetkiRepository extends JpaRepository<Yetki, String> {

}
