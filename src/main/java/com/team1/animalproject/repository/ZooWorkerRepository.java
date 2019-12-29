package com.team1.animalproject.repository;

import com.team1.animalproject.model.ZooWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("zooWorkerRepository")
public interface ZooWorkerRepository extends JpaRepository<ZooWorker, String> {

    List<ZooWorker> findByZooId(String zooId);

    List<ZooWorker> findByZooIdAndWorkerId(String zooId, String workerId);

    void deleteByZooId(String zooId);

}
