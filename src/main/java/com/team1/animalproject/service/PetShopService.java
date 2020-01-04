package com.team1.animalproject.service;

import com.team1.animalproject.model.PetShop;
import com.team1.animalproject.model.PetShopAnimal;
import com.team1.animalproject.model.PetShopWorker;
import com.team1.animalproject.repository.PetShopAnimalRepository;
import com.team1.animalproject.repository.PetShopRepository;
import com.team1.animalproject.repository.PetShopWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PetShopService implements IBaseService<PetShop> {

    @Qualifier("petShopRepository")
    @Autowired
    private PetShopRepository petShopRepository;

    @Qualifier("petShopWorkerRepository")
    @Autowired
    private PetShopWorkerRepository petShopWorkerRepository;

    @Qualifier("petShopAnimalRepository")
    @Autowired
    private PetShopAnimalRepository petShopAnimalRepository;

    @Override
    public List<PetShop> getAll() {
        List<PetShop> all = petShopRepository.findAll();
        all.stream().forEach(petShop -> {
            int size = 0;
            List<PetShopWorker> byPetSopId = petShopWorkerRepository.findByPetShopId(petShop.getId());
            if(byPetSopId != null){
                petShop.setWorkerCount(byPetSopId.size());
            }
        });
        return all;
    }

    @Override
    public void save(PetShop petshop) {
        if (petshop.getId() == null)
            petshop.setId(UUID.randomUUID().toString());
        petShopRepository.save(petshop);
    }

    @Override
    public void update(PetShop petshop) {
        petShopRepository.save(petshop);
    }

    @Override
    public void delete(List<PetShop> t) {
        petShopRepository.delete(t);
    }

    public void addWorker(List<PetShopWorker> petShopWorkers) {
        petShopWorkerRepository.save(petShopWorkers);
    }

    public List<PetShopWorker> getWorkersByPetShopId(String petshopId) {
        return petShopWorkerRepository.findByPetShopId(petshopId);
    }

    @Transactional
    public void saveWorker(List<PetShopWorker> petshopWorkers) {
        petShopWorkerRepository.deleteByPetShopId(petshopWorkers.stream().findFirst().get().getPetShopId());
        petShopWorkerRepository.save(petshopWorkers);
    }

    public List<PetShopAnimal> getAnimalsByPetShopId(String petshopId) {
        return petShopAnimalRepository.findByPetshopId(petshopId);
    }

    @Transactional
    public void saveAnimal(List<PetShopAnimal> petShopAnimals, String petshopId) {
        petShopAnimalRepository.deleteByPetshopId(petshopId);
        petShopAnimalRepository.save(petShopAnimals);
    }
}