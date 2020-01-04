package com.team1.animalproject.repository;

import com.team1.animalproject.model.PetShopAnimal;
import com.team1.animalproject.model.ZooAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("zooShopAnimalRepository")
public interface ZooShopAnimalRepository extends JpaRepository<ZooAnimal, String> {

    List<ZooAnimal> findByZooId(String zooId);

    List<ZooAnimal> findByZooIdAndAnimalId(String zooId, String animalId);

    void deleteByZooId(String zooId);

}
