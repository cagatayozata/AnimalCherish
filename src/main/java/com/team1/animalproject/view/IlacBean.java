package com.team1.animalproject.view;

import com.team1.animalproject.model.Ilac;
import com.team1.animalproject.service.IlacService;
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

@SuppressWarnings ("OptionalGetWithoutIsPresent")
@Component
@Scope("view")
@EqualsAndHashCode()
@Data
public class IlacBean extends BaseViewController<Ilac> implements Serializable {

	private static final long serialVersionUID = -5794460359553996241L;

	private static final Logger logger = LoggerFactory.getLogger(IlacBean.class);

	@Autowired
	private IlacService ilacService;

	private Ilac ilac = new Ilac();
	private List<Ilac> selectedIlacs;
	private List<Ilac> allIlacs;
	private List<Ilac> filteredIlacs;

	private boolean showCreateOrEdit;
	private boolean showInfo;

	@Override
	@PostConstruct
	public void viewOlustur() {
		super.altVerileriVeIlkEkraniHazirla();
		allIlacs = ilacService.ara();
		filteredIlacs = new ArrayList<>(allIlacs);
	}

	@Override
	public void ilkEkraniHazirla() {
		showCreateOrEdit = false;
		showInfo = false;
		allIlacs = ilacService.ara();
		filteredIlacs = new ArrayList<>(allIlacs);
		ilac = new Ilac();
	}

	public void kaydet() throws IOException {
		ilacService.save(ilac);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı",  "İlaç verisi başarıyla işlem görmüştür.") );
		context.getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/ilac/ilac.jsf");

	}

	public void kullanimaAl() throws IOException {
		ilac = selectedIlacs.stream().findFirst().get();
		ilac.setDurum(true);
		kaydet();
	}

	public boolean kullanimdaVarmi(){
		return selectedIlacs.stream().anyMatch(Ilac::isDurum);
	}

	public void prepareNewScreen(){
		showCreateOrEdit = true;
	}

	public void prepareUpdateScreen(){
		ilac = selectedIlacs.stream().findFirst().get();
		showCreateOrEdit = true;
	}

	public void prepareInfoScreen(){
		ilac = selectedIlacs.stream().findFirst().get();
		showCreateOrEdit = true;
		showInfo = true;
	}

	public void sil() throws IOException {
		ilacService.delete(selectedIlacs);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/ilac/ilac.jsf");
	}


}
