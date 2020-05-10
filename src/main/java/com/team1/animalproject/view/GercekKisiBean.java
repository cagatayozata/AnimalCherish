package com.team1.animalproject.view;

import com.team1.animalproject.model.GercekKisi;
import com.team1.animalproject.service.GercekKisiService;
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
@Scope ("view")
@EqualsAndHashCode ()
@Data
public class GercekKisiBean extends BaseViewController<GercekKisi> implements Serializable {

	private static final long serialVersionUID = -4784862612385542516L;

	private static final Logger logger = LoggerFactory.getLogger(GercekKisiBean.class);

	@Autowired
	private GercekKisiService gercekKisiService;

	private GercekKisi gercekKisi = new GercekKisi();
	private List<GercekKisi> selectedGercekKisis;
	private List<GercekKisi> allGercekKisis;
	private List<GercekKisi> filteredGercekKisis;

	private boolean showCreateOrEdit;
	private boolean showInfo;

	@Override
	@PostConstruct
	public void viewOlustur() {
		super.altVerileriVeIlkEkraniHazirla();
		allGercekKisis = gercekKisiService.getAll();
		filteredGercekKisis = new ArrayList<>(allGercekKisis);
	}

	@Override
	public void ilkEkraniHazirla() {
		showCreateOrEdit = false;
		showInfo = false;
		allGercekKisis = gercekKisiService.getAll();
		filteredGercekKisis = new ArrayList<>(allGercekKisis);
		gercekKisi = new GercekKisi();
	}

	public void kaydet() throws IOException {
		gercekKisiService.save(gercekKisi);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı", "Gerçek kişi verisi başarıyla işlem görmüştür."));
		context.getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/gercekkisi/gercekkisi.jsf");

	}

	public void prepareNewScreen() {
		showCreateOrEdit = true;
	}

	public void prepareUpdateScreen() {
		gercekKisi = selectedGercekKisis.stream().findFirst().get();
		showCreateOrEdit = true;
	}

	public void prepareInfoScreen() {
		gercekKisi = selectedGercekKisis.stream().findFirst().get();
		showCreateOrEdit = true;
		showInfo = true;
	}

	public void sil() throws IOException {
		gercekKisiService.delete(selectedGercekKisis);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/gercekkisi/gercekkisi.jsf");
	}

}
