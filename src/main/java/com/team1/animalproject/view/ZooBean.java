package com.team1.animalproject.view;

import com.team1.animalproject.model.Zoo;
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

@Component
@Scope("view")
@EqualsAndHashCode(callSuper = false)
@Data
public class ZooBean extends BaseViewController<Zoo> implements Serializable {

    private static final long serialVersionUID = -7142769566453097269L;

    private static final Logger logger = LoggerFactory.getLogger(ZooBean.class);

    @Autowired
    private ZooService zooService;

    private Zoo zoo = new Zoo();
    private List<Zoo> selectedZoos;
    private List<Zoo> allZoos;
    private List<Zoo> filteredZoos;

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


}
