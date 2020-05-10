package com.team1.animalproject.view;

import com.team1.animalproject.model.*;
import com.team1.animalproject.service.AnimalService;
import com.team1.animalproject.service.PetShopService;
import com.team1.animalproject.service.UserService;
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
public class PetShopBean extends BaseViewController<PetShop> implements Serializable {

	private static final long serialVersionUID = -5486608545743134294L;

	private static final Logger logger = LoggerFactory.getLogger(PetShopBean.class);

	@Autowired
	private PetShopService petShopService;
	@Autowired
	private UserService userService;
	@Autowired
	private AnimalService animalService;

	private PetShop petShop = new PetShop();
	private List<PetShop> selectedPetShops;
	private List<PetShop> allPetShops;
	private List<PetShop> filteredPetShops;
	private List<Kullanici> workers;
	private List<Kullanici> selectedWorkers;
	private List<Kullanici> filteredWorkers;

	private List<Kullanici> addedWorkers;
	private List<Kullanici> selectedAddedWorkers;
	private List<Kullanici> filteredAddedWorkers;
	private String petShopId;

	private List<Animal> animals;
	private List<Animal> selectedAnimals;
	private List<Animal> filteredAnimals;
	private List<Animal> addedAnimals;
	private List<Animal> selectedAddedAnimals;
	private List<Animal> filteredAddedAnimals;

	private boolean showCreateOrEdit;
	private boolean showInfo;
	private boolean showWorkerCreateOrEdit;
	private boolean workerInfo;
	private boolean showAnimalCreateOrEdit;

	@Override
	@PostConstruct
	public void viewOlustur() {
		super.altVerileriVeIlkEkraniHazirla();
		allPetShops = petShopService.getAll();
		filteredPetShops = new ArrayList<>(allPetShops);
	}

	@Override
	public void ilkEkraniHazirla() {
		showCreateOrEdit = false;
		showInfo = false;
		petShop = new PetShop();
		showWorkerCreateOrEdit = false;
		workerInfo = false;
		addedWorkers = new ArrayList<>();
		workers = userService.getAll();
		animals = animalService.getAll();
		filteredWorkers = new ArrayList<>();
		filteredAddedWorkers = new ArrayList<>();
		filteredAnimals = new ArrayList<>();
		filteredAddedAnimals = new ArrayList<>();
		addedAnimals = new ArrayList<>();
		showAnimalCreateOrEdit = false;
	}

	public void kaydet() throws IOException {
		petShopService.save(petShop);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı", "Pet Shop verisi başarıyla işlem görmüştür."));
		context.getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/petshop/petshop.jsf");
	}

	public void prepareNewScreen() {
		showCreateOrEdit = true;
	}

	public void prepareUpdateScreen() {
		petShop = selectedPetShops.stream().findFirst().get();
		showCreateOrEdit = true;
	}

	public void prepareInfoScreen() {
		petShop = selectedPetShops.stream().findFirst().get();
		showCreateOrEdit = true;
		showInfo = true;
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
		petShopId = selectedPetShops.stream().findFirst().get().getId();
		List<PetShopAnimal> animalsIn = petShopService.getAnimalsByPetShopId(petShopId);
		Optional<List<Animal>> animals = animalService.findByIdIn(animalsIn.stream().map(PetShopAnimal::getAnimalId).collect(Collectors.toList()));
		addedAnimals = animals.orElseGet(ArrayList::new);
		filteredAddedAnimals = addedAnimals;
		showAnimalCreateOrEdit = true;
	}

	@SuppressWarnings ("SuspiciousMethodCalls")
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

	private void findWorkers() {
		petShopId = selectedPetShops.stream().findFirst().get().getId();
		List<PetShopWorker> workersIn = petShopService.getWorkersByPetShopId(petShopId);
		Optional<List<Kullanici>> kullanicis = userService.findByIdIn(workersIn.stream().map(PetShopWorker::getWorkerId).collect(Collectors.toList()));
		addedWorkers = kullanicis.orElseGet(ArrayList::new);
		filteredAddedWorkers = addedWorkers;
		showWorkerCreateOrEdit = true;
	}

	private void findWorkersForAdd() {
		workers.removeAll(addedWorkers);
		filteredWorkers = workers;
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

	public void sil() throws IOException {
		petShopService.delete(selectedPetShops);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/petshop/petshop.jsf");
	}

	public void workerSave() throws IOException {
		List<PetShopWorker> petShopWorker = new ArrayList<>();
		addedWorkers.forEach(kullanici -> petShopWorker.add(PetShopWorker.builder().id(UUID.randomUUID().toString()).petShopId(petShopId).workerId(kullanici.id).build()));

		petShopService.saveWorker(petShopWorker);

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı", "Pet Shop Çalışanları Başarıyla Güncellendi."));
		context.getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/petshop/petshop.jsf");
	}

	public void animalsSave() throws IOException {
		List<PetShopAnimal> petShopAnimals = new ArrayList<>();
		if(addedAnimals.size() > 0){
			addedAnimals.forEach(animal -> {
				petShopAnimals.add(PetShopAnimal.builder().id(UUID.randomUUID().toString()).petshopId(petShopId).animalId(animal.id).build());

				AnimalTarihce animalTarihce = AnimalTarihce.builder()
						.animalId(animal.getId())
						.deger("Petshop: " + selectedPetShops.stream().findFirst().get().getName())
						.kimTarafindan(kullaniciPrincipal.getId())
						.neZaman(DateUtil.nowAsString())
						.yapilanIslem("Petshop sayfasında hayvan ilişkilendirme işlemi")
						.build();
				animalService.tarihceKaydet(animalTarihce);
			});

			petShopService.saveAnimal(petShopAnimals, petShopId);
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı", "PetShop'a Ait Hayvanlar Başarıyla Güncellendi."));
		context.getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/petshop/petshop.jsf");
	}
}
