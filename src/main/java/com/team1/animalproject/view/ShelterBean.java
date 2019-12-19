package com.team1.animalproject.view;

import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.model.Shelter;
import com.team1.animalproject.model.ShelterWorker;
import com.team1.animalproject.service.ShelterService;
import com.team1.animalproject.service.UserService;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
    private String shelterId;


    private boolean showCreateOrEdit;
    private boolean showInfo;
    private boolean showWorkerCreateOrEdit;
    private boolean workerInfo;

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
        filteredWorkers = new ArrayList<>();
        filteredAddedWorkers = new ArrayList<>();
    }

    public void kaydet() throws IOException {
        shelterService.save(shelter);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Başarılı", "Barınak Ekleme İşlemi Başarıyla Tamamlandı"));
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

    public void sil() throws IOException {
        shelterService.delete(selectedShelters);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/shelter/shelter.jsf");
    }
}
