package com.team1.animalproject.view;

import com.team1.animalproject.model.Animal;
import com.team1.animalproject.model.Cins;
import com.team1.animalproject.model.Tur;
import com.team1.animalproject.service.AnimalService;
import com.team1.animalproject.service.CinsService;
import com.team1.animalproject.service.TurService;
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
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("view")
@EqualsAndHashCode(callSuper = false)
@Data
public class AnimalBean extends BaseViewController<Animal> implements Serializable {

    private static final long serialVersionUID = 5246560358820611506L;

    private static final Logger logger = LoggerFactory.getLogger(AnimalBean.class);

    @Autowired
    private AnimalService animalService;

    @Autowired
    private TurService turService;

    @Autowired
    private CinsService cinsService;

    private Animal animal = new Animal();
    private List<Animal> selectedAnimals;
    private List<Animal> allAnimals;
    private List<Animal> filteredAnimals;

    private List<Tur> turler;
    private List<Cins> cinsler;

    private boolean showCreateOrEdit;
    private boolean showInfo;

    @Override
    @PostConstruct
    public void viewOlustur() {
        super.altVerileriVeIlkEkraniHazirla();
        allAnimals = animalService.getAll();
        filteredAnimals = new ArrayList<>(allAnimals);
    }

    @Override
    public void ilkEkraniHazirla() {
        showCreateOrEdit = false;
        showInfo = false;
        allAnimals = animalService.getAll();
        filteredAnimals = new ArrayList<>(allAnimals);
        animal = new Animal();
        turler = turService.getAll();
    }

    public void onTurChange() {
        if (animal.getTurId() != null && !animal.getTurId().equals(""))
            cinsler = cinsService.findByTurId(animal.getTurId());
    }

    public void kaydet() throws IOException {
        animalService.save(animal);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Başarılı", "Hayvan ekleme işlemi başarıyla tamamlandı."));
        context.getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/animal/animal.jsf");

    }

    public void prepareNewScreen() {
        showCreateOrEdit = true;
    }

    public void prepareUpdateScreen() {
        animal = selectedAnimals.stream().findFirst().get();
		cinsler = cinsService.findByTurId(animal.getTurId());
		showCreateOrEdit = true;
    }

    public void prepareInfoScreen() {
        animal = selectedAnimals.stream().findFirst().get();
		cinsler = cinsService.findByTurId(animal.getTurId());
		showCreateOrEdit = true;
        showInfo = true;
    }

    public void sil() throws IOException {
        animalService.delete(selectedAnimals);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/animal/animal.jsf");
    }


}
