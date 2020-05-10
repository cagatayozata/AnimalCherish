package com.team1.animalproject.view;

import com.team1.animalproject.model.*;
import com.team1.animalproject.service.AnimalService;
import com.team1.animalproject.service.UserService;
import com.team1.animalproject.service.ZooService;
import com.team1.animalproject.view.utils.DateUtil;
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
public class ZooBean extends BaseViewController<Zoo> implements Serializable {

	private static final long serialVersionUID = -7142769566453097269L;

	private static final Logger logger = LoggerFactory.getLogger(ZooBean.class);

	@Autowired
	private ZooService zooService;
	@Autowired
	private UserService userService;
	@Autowired
	private AnimalService animalService;

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

	private List<Animal> animals;
	private List<Animal> selectedAnimals;
	private List<Animal> filteredAnimals;
	private List<Animal> addedAnimals;
	private List<Animal> selectedAddedAnimals;
	private List<Animal> filteredAddedAnimals;
	private boolean showAnimalCreateOrEdit;

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
		animals = animalService.getAll();
		filteredAnimals = new ArrayList<>();
		filteredAddedAnimals = new ArrayList<>();
		addedAnimals = new ArrayList<>();
		showAnimalCreateOrEdit = false;
	}

	public void kaydet() throws IOException {
		zooService.save(zoo);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı", "Zoo verisi başarıyla işlem görmüştür."));
		context.getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/zoo/zoo.jsf");

	}

	public void prepareNewScreen() {
		showCreateOrEdit = true;
	}

	public void prepareUpdateScreen() {
		zoo = selectedZoos.stream().findFirst().get();
		showCreateOrEdit = true;
	}

	public void prepareInfoScreen() {
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

	public void prepareAnimalNewScreen() {
		showAnimalCreateOrEdit = true;
		findAnimals();
		findAnimalsForAdd();
	}

	private void findAnimals() {
		zooId = selectedZoos.stream().findFirst().get().getId();
		List<ZooAnimal> animalsIn = zooService.getAnimalsByZooId(zooId);
		Optional<List<Animal>> animals = animalService.findByIdIn(animalsIn.stream().map(ZooAnimal::getAnimalId).collect(Collectors.toList()));
		addedAnimals = animals.orElseGet(ArrayList::new);
		filteredAddedAnimals = addedAnimals;
		showAnimalCreateOrEdit = true;
	}

	private void findAnimalsForAdd() {
		animals.removeAll(addedWorkers);
		filteredAnimals = animals;
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
		zooId = selectedZoos.stream().findFirst().get().getId();
		List<ZooWorker> workersIn = zooService.getWorkersByShelterId(zooId);
		Optional<List<Kullanici>> kullanicis = userService.findByIdIn(workersIn.stream().map(ZooWorker::getWorkerId).collect(Collectors.toList()));
		addedWorkers = kullanicis.orElseGet(ArrayList::new);
		filteredAddedWorkers = addedWorkers;
		showWorkerCreateOrEdit = true;
	}

	private void findWorkersForAdd() {
		workers.removeAll(addedWorkers);
		filteredWorkers = workers;
	}

	public void workerSave() throws IOException {
		List<ZooWorker> zooWorkers = new ArrayList<>();
		addedWorkers.forEach(kullanici -> zooWorkers.add(ZooWorker.builder().id(UUID.randomUUID().toString()).zooId(zooId).workerId(kullanici.id).build()));

		zooService.saveWorker(zooWorkers, zooId);

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı", "Hayvanat Bahçesi Çalışanları Başarıyla Güncellendi."));
		context.getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/zoo/zoo.jsf");
	}

	public void animalsSave() throws IOException {
		List<ZooAnimal> zooAnimals = new ArrayList<>();
		if(addedAnimals.size() > 0){
			addedAnimals.forEach(animal -> {
				zooAnimals.add(ZooAnimal.builder().id(UUID.randomUUID().toString()).zooId(zooId).animalId(animal.id).build());

				AnimalTarihce animalTarihce = AnimalTarihce.builder()
						.animalId(animal.getId())
						.deger("Hayvanat Bahçesi: " + selectedZoos.stream().findFirst().get())
						.kimTarafindan(kullaniciPrincipal.getId())
						.neZaman(DateUtil.nowAsString())
						.yapilanIslem("Hayvanat bahçesi sayfasında hayvan ilişkilendirme işlemi")
						.build();
				animalService.tarihceKaydet(animalTarihce);
			});

			zooService.saveAnimal(zooAnimals, zooId);
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı", "Hayvanat Bahçesine Ait Hayvanlar Başarıyla Güncellendi."));
		context.getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/zoo/zoo.jsf");
	}

}
