package com.team1.animalproject.service;

import com.team1.animalproject.exception.BaseExceptionType;
import com.team1.animalproject.exception.ViewException;
import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.model.Shelter;
import com.team1.animalproject.model.ShelterAnimal;
import com.team1.animalproject.model.ShelterWorker;
import com.team1.animalproject.repository.ShelterAnimalRepository;
import com.team1.animalproject.repository.ShelterRepository;
import com.team1.animalproject.repository.ShelterWorkerRepository;
import com.team1.animalproject.view.utils.KullaniciTipiEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.UUID;

@SuppressWarnings ("ALL")
@Service
public class ShelterService implements IBaseService<Shelter> {

    @Qualifier("shelterRepository")
    @Autowired
    private ShelterRepository shelterRepository;

    @Qualifier("shelterWorkerRepository")
    @Autowired
    private ShelterWorkerRepository shelterWorkerRepository;

    @Qualifier("shelterAnimalRepository")
    @Autowired
    private ShelterAnimalRepository shelterAnimalRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Shelter> getAll() {
        List<Shelter> all = shelterRepository.findAll();
        all.forEach(shelter -> {
            int size = 0;
            List<ShelterWorker> byShelterId = shelterWorkerRepository.findByShelterId(shelter.getId());
            if(byShelterId != null){
                shelter.setWorkerCount(byShelterId.size());
            }
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

    public List<ShelterWorker> getWorkersByShelterId(String shelterId) {
        return shelterWorkerRepository.findByShelterId(shelterId);
    }

    @Transactional
    public void saveWorker(List<ShelterWorker> shelterWorkers, String shelterId) {
        boolean gorevli = shelterWorkers.stream().anyMatch(shelterWorker -> userService.kullaniciBaskaYerdeGorevliMi(shelterWorker.getWorkerId(), KullaniciTipiEnum.SHELTER));
        if(!gorevli){
            List<ShelterWorker> all = shelterWorkerRepository.findAll();

            all.stream().map(shelterWorker -> userService.findById(shelterWorker.getId()).get()).forEach(kullanici -> {
                kullanici.setKullaniciTipi(KullaniciTipiEnum.NORMAL_USER.getId());
                userService.save(kullanici);
            });

            shelterWorkerRepository.deleteByShelterId(shelterId);
            shelterWorkerRepository.save(shelterWorkers);

            shelterWorkers.forEach(shelterWorker -> {
                Kullanici kullanici = userService.findById(shelterWorker.getWorkerId()).get();
                kullanici.setKullaniciTipi(KullaniciTipiEnum.SHELTER.getId());
                userService.update(kullanici);
            });

        }else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, BaseExceptionType.KULLANICI_ZATEN_GOREVLI.getValidationMessage(), null));
            context.getExternalContext().getFlash().setKeepMessages(true);
            throw new ViewException();
        }

    }

    public List<ShelterAnimal> getAnimalsByShelterId(String shelterId) {
        return shelterAnimalRepository.findByShelterId(shelterId);
    }

    @Transactional
    public void saveAnimal(List<ShelterAnimal> shelterAnimals, String shelterId) {
        shelterAnimalRepository.deleteByShelterId(shelterId);
        shelterAnimalRepository.save(shelterAnimals);
    }

    public long toplamSayi(){
        return shelterRepository.count();
    }
}