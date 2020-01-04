package com.team1.animalproject.service;

import com.team1.animalproject.model.Zoo;
import com.team1.animalproject.model.ZooAnimal;
import com.team1.animalproject.model.ZooWorker;
import com.team1.animalproject.repository.ZooRepository;
import com.team1.animalproject.repository.ZooShopAnimalRepository;
import com.team1.animalproject.repository.ZooWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ZooService implements IBaseService<Zoo> {

    @Qualifier("zooRepository")
    @Autowired
    private ZooRepository zooRepository;

    @Qualifier("zooWorkerRepository")
    @Autowired
    private ZooWorkerRepository zooWorkerRepository;

    @Qualifier("zooShopAnimalRepository")
    @Autowired
    private ZooShopAnimalRepository zooShopAnimalRepository;

    @Override
    public List<Zoo> getAll() {
        List<Zoo> all = zooRepository.findAll();
        all.stream().forEach(zoo -> {
            int size = 0;
            List<ZooWorker> byShelterId = zooWorkerRepository.findByZooId(zoo.getId());
            if(byShelterId != null){
                zoo.setWorkerCount(byShelterId.size());
            }
        });
        return all;
    }

    @Override
    public void save(Zoo zoo) {
        if(zoo.getId() == null)
            zoo.setId(UUID.randomUUID().toString());
        zooRepository.save(zoo);
    }

    @Override
    public void update(Zoo zoo) {
        zooRepository.save(zoo);
    }

    @Override
    public void delete(List<Zoo> t) {
        zooRepository.delete(t);
    }

    public List<ZooWorker> getWorkersByShelterId(String zooId) {
        return zooWorkerRepository.findByZooId(zooId);
    }

    @Transactional
    public void saveWorker(List<ZooWorker> zooWorkers, String zooId) {
        zooWorkerRepository.deleteByZooId(zooId);
        zooWorkerRepository.save(zooWorkers);
    }

    public List<ZooAnimal> getAnimalsByZooId(String zooId) {
        return zooShopAnimalRepository.findByZooId(zooId);
    }

    @Transactional
    public void saveAnimal(List<ZooAnimal> zooAnimals, String zooId) {
        zooShopAnimalRepository.deleteByZooId(zooId);
        zooShopAnimalRepository.save(zooAnimals);
    }
}