package com.team1.animalproject.view;

import com.team1.animalproject.model.*;
import com.team1.animalproject.service.AnimalService;
import com.team1.animalproject.service.ShelterService;
import com.team1.animalproject.service.UserService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.id.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Scope("view")
@EqualsAndHashCode(callSuper = false)
@Data
public class ShelterBean extends BaseViewController<Shelter> implements Serializable {

    private static final long serialVersionUID = -5486608545743134294L;

    private static final Logger logger = LoggerFactory.getLogger(ShelterBean.class);

    @Autowired
    private ShelterService shelterService;
    @Autowired
    private UserService userService;
    @Autowired
    private AnimalService animalService;

    private Shelter shelter = new Shelter();
    private List<Shelter> selectedShelters;
    private List<Shelter> allShelters;
    private List<Shelter> filteredShelters;


    private List<Kullanici> workers;
    private List<Kullanici> selectedWorkers;
    private List<Kullanici> filteredWorkers;
    private List<Kullanici> addedWorkers;
    private List<Kullanici> selectedAddedWorkers;
    private List<Kullanici> filteredAddedWorkers;

    private List<Animal> animals;
    private List<Animal> selectedAnimals;
    private List<Animal> filteredAnimals;
    private List<Animal> addedAnimals;
    private List<Animal> selectedAddedAnimals;
    private List<Animal> filteredAddedAnimals;
    private String shelterId;


    private boolean showCreateOrEdit;
    private boolean showInfo;
    private boolean showWorkerCreateOrEdit;
    private boolean workerInfo;
    private boolean showAnimalCreateOrEdit;

    @Override
    @PostConstruct
    public void viewOlustur() {
        super.altVerileriVeIlkEkraniHazirla();
        allShelters = shelterService.getAll();
        filteredShelters = new ArrayList<>(allShelters);
    }

    @Override
    public void ilkEkraniHazirla() {
        showCreateOrEdit = false;
        showInfo = false;
        shelter = new Shelter();
        showWorkerCreateOrEdit = false;
        workerInfo = false;
        addedWorkers = new ArrayList<>();
        workers = userService.getAll();
        animals = animalService.getAll();
        filteredWorkers = new ArrayList<>();
        filteredAddedWorkers = new ArrayList<>();
        filteredAnimals = new ArrayList<>();
        filteredAddedAnimals = new ArrayList<>();
        addedAnimals = new ArrayList<>();
        showAnimalCreateOrEdit = false;
    }

    public void kaydet() throws IOException {
        shelterService.save(shelter);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Başarılı", "Barınak verisi başarıyla işlem görmüştür."));
        context.getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/shelter/shelter.jsf");
    }

    public void prepareNewScreen() {
        showCreateOrEdit = true;
    }

    public void prepareUpdateScreen() {
        shelter = selectedShelters.stream().findFirst().get();
        showCreateOrEdit = true;
    }

    public void prepareInfoScreen() {
        shelter = selectedShelters.stream().findFirst().get();
        showCreateOrEdit = true;
        showInfo = true;
    }

    public void prepareWorkerNewScreen() {
        showWorkerCreateOrEdit = true;
        findWorkers();
        findWorkersForAdd();
    }

    public void prepareAnimalNewScreen() {
        showAnimalCreateOrEdit = true;
        findAnimals();
        findAnimalsForAdd();
    }

    public void prepareWorkerUpdateScreen() {
        findWorkers();
        findWorkersForAdd();
    }

    public void prepareWorkerInfoScreen() {
        findWorkersForAdd();
        findWorkers();
        workerInfo = true;
    }

    public void addWorker() {
        addedWorkers.addAll(selectedWorkers);
        workers.removeAll(selectedWorkers);
        selectedWorkers = new ArrayList<>();
    }

    public void deleteWorker() {
        addedWorkers.removeAll(selectedAddedWorkers);
        workers.addAll(selectedAddedWorkers);
        selectedAddedWorkers = new ArrayList<>();
    }

    public void addAnimal() {
        addedAnimals.addAll(selectedAnimals);
        animals.removeAll(selectedAnimals);
        selectedAnimals = new ArrayList<>();
    }

    public void deleteAnimal() {
        addedAnimals.removeAll(selectedAddedAnimals);
        animals.addAll(selectedAddedAnimals);
        selectedAddedWorkers = new ArrayList<>();
    }

    private void findWorkers() {
        shelterId = selectedShelters.stream().findFirst().get().getId();
        List<ShelterWorker> workersIn = shelterService.getWorkersByShelterId(shelterId);
        Optional<List<Kullanici>> kullanicis = userService.findByIdIn(workersIn.stream().map(ShelterWorker::getWorkerId).collect(Collectors.toList()));
        if (kullanicis.isPresent()) {
            addedWorkers = kullanicis.get();
        } else {
            addedWorkers = new ArrayList<>();
        }
        filteredAddedWorkers = addedWorkers;
        showWorkerCreateOrEdit = true;
    }

    private void findWorkersForAdd() {
       workers.removeAll(addedWorkers);
       filteredWorkers = workers;
    }


    private void findAnimals() {
        shelterId = selectedShelters.stream().findFirst().get().getId();
        List<ShelterAnimal> animalsIn = shelterService.getAnimalsByShelterId(shelterId);
        Optional<List<Animal>> animals = animalService.findByIdIn(animalsIn.stream().map(ShelterAnimal::getAnimalId).collect(Collectors.toList()));
        if (animals.isPresent()) {
            addedAnimals = animals.get();
        } else {
            addedAnimals = new ArrayList<>();
        }
        filteredAddedAnimals = addedAnimals;
        showAnimalCreateOrEdit = true;
    }

    private void findAnimalsForAdd() {
        animals.removeAll(addedWorkers);
        filteredAnimals = animals;
    }

    public void sil() throws IOException {
        shelterService.delete(selectedShelters);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/shelter/shelter.jsf");
    }

    public void workerSave() throws IOException {
        List<ShelterWorker> shelterWorkers = new ArrayList<>();
        if(addedWorkers.size() > 0){
            addedWorkers.stream().forEach(kullanici -> {
                shelterWorkers.add(ShelterWorker.builder()
                        .id(UUID.randomUUID().toString())
                        .shelterId(shelterId)
                        .workerId(kullanici.id)
                        .build());
            });

            shelterService.saveWorker(shelterWorkers, shelterId);
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Başarılı", "Barınak Çalışanları Başarıyla Güncellendi."));
        context.getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/shelter/shelter.jsf");
    }

    public void animalsSave() throws IOException {
        List<ShelterAnimal> shelterAnimals = new ArrayList<>();
        if(addedAnimals.size() > 0){
            addedAnimals.stream().forEach(animal -> {
                shelterAnimals.add(ShelterAnimal.builder()
                        .id(UUID.randomUUID().toString())
                        .shelterId(shelterId)
                        .animalId(animal.id)
                        .build());
            });

            shelterService.saveAnimal(shelterAnimals, shelterId);
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Başarılı", "Barınağa Ait Hayvanlar Başarıyla Güncellendi."));
        context.getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/shelter/shelter.jsf");
    }
}
