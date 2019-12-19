package com.team1.animalproject.service;

import com.team1.animalproject.model.Shelter;
import com.team1.animalproject.model.ShelterWorker;
import com.team1.animalproject.repository.ShelterRepository;
import com.team1.animalproject.repository.ShelterWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ShelterService implements IBaseService<Shelter> {

    @Qualifier("shelterRepository")
    @Autowired
    private ShelterRepository shelterRepository;

    @Qualifier("shelterWorkerRepository")
    @Autowired
    private ShelterWorkerRepository shelterWorkerRepository;

    @Override
    public List<Shelter> getAll() {
        List<Shelter> all = shelterRepository.findAll();
        all.stream().forEach(shelter -> {
            int size = 0;
            List<ShelterWorker> byShelterId = shelterWorkerRepository.findByShelterId(shelter.getId());
            if(byShelterId != null)
            shelter.setWorkerCount(byShelterId.size());
        });
        return all;
    }

    @Override
    public void save(Shelter shelter) {
        if (shelter.getId() == null)
            shelter.setId(UUID.randomUUID().toString());
        shelterRepository.save(shelter);
    }

    @Override
    public void update(Shelter shelter) {
        shelterRepository.save(shelter);
    }

    @Override
    public void delete(List<Shelter> t) {
        shelterRepository.delete(t);
    }

    public void addWorker(List<ShelterWorker> shelterWorkers) {
        shelterWorkerRepository.save(shelterWorkers);
    }

    public List<ShelterWorker> getWorkersByShelterId(String shelterId) {
        return shelterWorkerRepository.findByShelterId(shelterId);
    }

    @Transactional
    public void saveWorker(List<ShelterWorker> shelterWorkers) {
        shelterWorkerRepository.deleteByShelterId(shelterWorkers.stream().findFirst().get().getShelterId());
        shelterWorkerRepository.save(shelterWorkers);
    }
}