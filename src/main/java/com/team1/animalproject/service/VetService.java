package com.team1.animalproject.service;

import com.team1.animalproject.model.Vet;
import com.team1.animalproject.repository.VetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VetService implements IBaseService<Vet> {

    @Qualifier("vetRepository")
    @Autowired
    private VetRepository vetRepository;

    @Override
    public List<Vet> getAll() {
        return vetRepository.findAll();
    }

    @Override
    public void save(Vet vet) {
        if(vet.getId() == null)
        vet.setId(UUID.randomUUID().toString());
        vetRepository.save(vet);
    }

    @Override
    public void update(Vet vet) {
        vetRepository.save(vet);
    }

    @Override
    public void delete(List<Vet> t) {
        vetRepository.delete(t);
    }
}