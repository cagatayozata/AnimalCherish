package com.team1.animalproject.view;

import com.team1.animalproject.model.Klinik;
import com.team1.animalproject.model.KlinikVet;
import com.team1.animalproject.model.Vet;
import com.team1.animalproject.service.KlinikService;
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
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings ("ALL")
@Component
@Scope ("view")
@EqualsAndHashCode ()
@Data
public class KlinikBean extends BaseViewController<Klinik> implements Serializable {

	private static final long serialVersionUID = -1275549413418695569L;

	private static final Logger logger = LoggerFactory.getLogger(KlinikBean.class);

	@Autowired
	private KlinikService klinikService;
	@Autowired
	private VetService vetService;

	private Klinik klinik = new Klinik();
	private List<Klinik> selectedKliniks;
	private List<Klinik> allKliniks;
	private List<Klinik> filteredKliniks;
	private List<Vet> vets;
	private List<Vet> selectedVets;
	private List<Vet> filteredVets;

	private List<Vet> addedVets;
	private List<Vet> selectedAddedVets;
	private List<Vet> filteredAddedVets;
	private String klinikId;

	private boolean showCreateOrEdit;
	private boolean showInfo;
	private boolean showVetCreateOrEdit;
	private boolean vetInfo;
	private boolean showAnimalCreateOrEdit;

	@Override
	@PostConstruct
	public void viewOlustur() {
		super.altVerileriVeIlkEkraniHazirla();
		allKliniks = klinikService.getAll();
		filteredKliniks = new ArrayList<>(allKliniks);
	}

	@Override
	public void ilkEkraniHazirla() {
		showCreateOrEdit = false;
		showInfo = false;
		klinik = new Klinik();
		showVetCreateOrEdit = false;
		vetInfo = false;
		addedVets = new ArrayList<>();
		vets = vetService.getAll();
		filteredVets = new ArrayList<>();
		filteredAddedVets = new ArrayList<>();
		showAnimalCreateOrEdit = false;
	}

	public void kaydet() throws IOException {
		klinikService.save(klinik);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı", "Pet Shop verisi başarıyla işlem görmüştür."));
		context.getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/klinik/klinik.jsf");
	}

	public void prepareNewScreen() {
		showCreateOrEdit = true;
	}

	public void prepareUpdateScreen() {
		klinik = selectedKliniks.stream().findFirst().get();
		showCreateOrEdit = true;
	}

	public void prepareInfoScreen() {
		klinik = selectedKliniks.stream().findFirst().get();
		showCreateOrEdit = true;
		showInfo = true;
	}

	public void prepareVetNewScreen() {
		showVetCreateOrEdit = true;
		findVets();
		findVetsForAdd();
	}

	public void prepareVetUpdateScreen() {
		findVets();
		findVetsForAdd();
	}

	public void prepareVetInfoScreen() {
		findVetsForAdd();
		findVets();
		vetInfo = true;
	}

	public void addVet() {
		addedVets.addAll(selectedVets);
		vets.removeAll(selectedVets);
		selectedVets = new ArrayList<>();
	}

	public void deleteVet() {
		addedVets.removeAll(selectedAddedVets);
		vets.addAll(selectedAddedVets);
		selectedAddedVets = new ArrayList<>();
	}

	private void findVets() {
		klinikId = selectedKliniks.stream().findFirst().get().getId();
		List<KlinikVet> vetsIn = klinikService.getWorkersByKlinikId(klinikId);
		Optional<List<Vet>> kullanicis = vetService.findByIdIn(vetsIn.stream().map(KlinikVet::getVetId).collect(Collectors.toList()));
		addedVets = kullanicis.orElseGet(ArrayList::new);
		filteredAddedVets = addedVets;
		showVetCreateOrEdit = true;
	}

	private void findVetsForAdd() {
		vets.removeAll(addedVets);
		filteredVets = vets;
	}

	public void sil() throws IOException {
		klinikService.delete(selectedKliniks);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/klinik/klinik.jsf");
	}

	public void vetSave() throws IOException {
		List<KlinikVet> klinikVet = new ArrayList<>();
		addedVets.forEach(kullanici -> klinikVet.add(KlinikVet.builder().id(UUID.randomUUID().toString()).klinikId(klinikId).vetId(kullanici.id).build()));

		klinikService.saveWorker(klinikVet);

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı", "Klinik Çalışanları Başarıyla Güncellendi."));
		context.getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/klinik/klinik.jsf");
	}

}
