package com.team1.animalproject.view;

import com.team1.animalproject.model.Shelter;
import com.team1.animalproject.service.ShelterService;
import com.team1.animalproject.service.VetService;
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
public class ShelterBean extends BaseViewController<Shelter> implements Serializable {

	private static final long serialVersionUID = -5486608545743134294L;

	private static final Logger logger = LoggerFactory.getLogger(ShelterBean.class);

	@Autowired
	private ShelterService shelterService;

	private Shelter shelter = new Shelter();
	private List<Shelter> selectedShelters;
	private List<Shelter> allShelters;
	private List<Shelter> filteredShelters;

	private boolean showCreateOrEdit;
	private boolean showInfo;

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
	}

	public void kaydet() throws IOException {
		shelterService.save(shelter);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı",  "Barınak Ekleme İşlemi Başarıyla Tamamlandı") );
		context.getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/shelter/shelter.jsf");

	}

	public void prepareNewScreen(){
		showCreateOrEdit = true;
	}

	public void prepareUpdateScreen(){
		shelter = selectedShelters.stream().findFirst().get();
		showCreateOrEdit = true;
	}

	public void prepareInfoScreen(){
		shelter = selectedShelters.stream().findFirst().get();
		showCreateOrEdit = true;
		showInfo = true;
	}

	public void sil() throws IOException {
		shelterService.delete(selectedShelters);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/shelter/shelter.jsf");
	}


}
