package com.team1.animalproject.view;

import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.model.ShelterWorker;
import com.team1.animalproject.model.Zoo;
import com.team1.animalproject.model.ZooWorker;
import com.team1.animalproject.service.UserService;
import com.team1.animalproject.service.ZooService;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Scope("view")
@EqualsAndHashCode(callSuper = false)
@Data
public class ZooBean extends BaseViewController<Zoo> implements Serializable {

    private static final long serialVersionUID = -7142769566453097269L;

    private static final Logger logger = LoggerFactory.getLogger(ZooBean.class);

    @Autowired
    private ZooService zooService;

    @Autowired
    private UserService userService;

    private Zoo zoo = new Zoo();
    private List<Zoo> selectedZoos;
    private List<Zoo> allZoos;
    private List<Zoo> filteredZoos;
    private List<Kullanici> workers;
    private List<Kullanici> selectedWorkers;
    private List<Kullanici> filteredWorkers;
    private boolean showWorkerCreateOrEdit;
    private boolean workerInfo;
    private String zooId;

    private List<Kullanici> addedWorkers;
    private List<Kullanici> selectedAddedWorkers;
    private List<Kullanici> filteredAddedWorkers;

    private boolean showCreateOrEdit;
    private boolean showInfo;

    @Override
    @PostConstruct
    public void viewOlustur() {
        super.altVerileriVeIlkEkraniHazirla();
        allZoos = zooService.getAll();
        filteredZoos = new ArrayList<>(allZoos);
    }

    @Override
    public void ilkEkraniHazirla() {
        showCreateOrEdit = false;
        showInfo = false;
        zoo = new Zoo();
        workers = userService.getAll();
        showWorkerCreateOrEdit = false;
        workerInfo = false;
        addedWorkers = new ArrayList<>();
        selectedAddedWorkers = new ArrayList<>();
        filteredAddedWorkers = new ArrayList<>();
        filteredWorkers = new ArrayList<>();
        selectedWorkers = new ArrayList<>();
    }

    public void kaydet() throws IOException {
        zooService.save(zoo);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Başarılı",  "Zoo Ekleme İşlemi Başarıyla Tamamlandı") );
        context.getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/zoo/zoo.jsf");

    }

    public void prepareNewScreen(){
        showCreateOrEdit = true;
    }

    public void prepareUpdateScreen(){
        zoo = selectedZoos.stream().findFirst().get();
        showCreateOrEdit = true;
    }

    public void prepareInfoScreen(){
        zoo = selectedZoos.stream().findFirst().get();
        showCreateOrEdit = true;
        showInfo = true;
    }

    public void sil() throws IOException {
        zooService.delete(selectedZoos);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/zoo/zoo.jsf");
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
        zooId = selectedZoos.stream().findFirst().get().getId();
        List<ZooWorker> workersIn = zooService.getWorkersByShelterId(zooId);
        Optional<List<Kullanici>> kullanicis = userService.findByIdIn(workersIn.stream().map(ZooWorker::getWorkerId).collect(Collectors.toList()));
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

    public void workerSave() throws IOException {
        List<ZooWorker> zooWorkers = new ArrayList<>();
        if(addedWorkers.size() > 0){
            addedWorkers.stream().forEach(kullanici -> {
                zooWorkers.add(ZooWorker.builder()
                        .id(UUID.randomUUID().toString())
                        .zooId(zooId)
                        .workerId(kullanici.id)
                        .build());
            });

            zooService.saveWorker(zooWorkers, zooId);
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Başarılı", "Hayvanat Bahçesi Çalışanları Başarıyla Güncellendi."));
        context.getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/zoo/zoo.jsf");
    }


}
