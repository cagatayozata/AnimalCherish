package com.team1.animalproject.service;

import com.team1.animalproject.model.Vet;
import com.team1.animalproject.model.VetRandevu;
import com.team1.animalproject.repository.VetRandevuRepository;
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

    @Qualifier("vetRandevuRepository")
    @Autowired
    private VetRandevuRepository vetRandevuRepository;

    @Override
    public List<Vet> getAll() {
        return vetRepository.findAll();
    }

    public List<VetRandevu> getAllRandevu(String vetId) {
        return vetRandevuRepository.findByVetId(vetId);
    }

    public List<VetRandevu> getAllRandevuByKullanici(String kullaniciId) {
        return vetRandevuRepository.findByRandevuAlanKullanici(kullaniciId);
    }

    public void vetRandevuKaydet(VetRandevu vetRandevu){
        if(vetRandevu.getId() == null)
            vetRandevu.setId(UUID.randomUUID().toString());
        vetRandevuRepository.save(vetRandevu);
    }

    public void vetRandevuSil(VetRandevu vetRandevu){
        vetRandevuRepository.delete(vetRandevu);
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

    public Vet findByKullaniciId(String id){
        return vetRepository.findByKullaniciId(id);
    }

    public long toplamSayi(){
        return vetRepository.count();
    }
}