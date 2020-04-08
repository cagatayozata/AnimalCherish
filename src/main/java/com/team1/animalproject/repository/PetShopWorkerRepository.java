package com.team1.animalproject.repository;

import com.team1.animalproject.model.PetShopWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("petShopWorkerRepository")
public interface PetShopWorkerRepository extends JpaRepository<PetShopWorker, String> {

    List<PetShopWorker> findByPetShopId(String petshopId);

    List<PetShopWorker> findByPetShopIdAndWorkerId(String petshopId, String workerId);

    void deleteByPetShopId(String petshopId);

	Optional<PetShopWorker> findById(String id);
}
