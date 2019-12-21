package com.team1.animalproject.service;

import com.team1.animalproject.model.Zoo;
import com.team1.animalproject.repository.ZooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ZooService implements IBaseService<Zoo> {

    @Qualifier("zooRepository")
    @Autowired
    private ZooRepository zooRepository;

    @Override
    public List<Zoo> getAll() {
        return zooRepository.findAll();
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
}