package com.team1.animalproject.repository;

import com.team1.animalproject.model.ShelterWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("shelterWorkerRepository")
public interface ShelterWorkerRepository extends JpaRepository<ShelterWorker, String> {

    List<ShelterWorker> findByShelterId(String shelterId);

    List<ShelterWorker> findByShelterIdAndWorkerId(String shelterId, String workerId);

    void deleteByShelterId(String shelterId);

}
