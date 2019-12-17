package com.team1.animalproject.view;

import com.team1.animalproject.model.Vet;
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
public class VetBean extends BaseViewController<Vet> implements Serializable {

	private static final long serialVersionUID = -5486608545743134294L;

	private static final Logger logger = LoggerFactory.getLogger(VetBean.class);

	@Autowired
	private VetService vetService;

	private Vet vet = new Vet();
	private List<Vet> selectedVets;
	private List<Vet> allVets;
	private List<Vet> filteredVets;

	private boolean showCreateOrEdit;
	private boolean showInfo;

	@Override
	@PostConstruct
	public void viewOlustur() {
		super.altVerileriVeIlkEkraniHazirla();
		allVets = vetService.getAll();
		filteredVets = new ArrayList<>(allVets);
	}

	@Override
	public void ilkEkraniHazirla() {
		showCreateOrEdit = false;
		showInfo = false;
		vet = new Vet();
	}

	public void kaydet() throws IOException {
		vetService.save(vet);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı",  "Veteriner Ekleme İşlemi Başarıyla Tamamlandı") );
		context.getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/vet/vet.jsf");

	}

	public void prepareNewScreen(){
		showCreateOrEdit = true;
	}

	public void prepareUpdateScreen(){
		vet = selectedVets.stream().findFirst().get();
		showCreateOrEdit = true;
	}

	public void prepareInfoScreen(){
		vet = selectedVets.stream().findFirst().get();
		showCreateOrEdit = true;
		showInfo = true;
	}

	public void sil() throws IOException {
		vetService.delete(selectedVets);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/vet/vet.jsf");
	}


}
