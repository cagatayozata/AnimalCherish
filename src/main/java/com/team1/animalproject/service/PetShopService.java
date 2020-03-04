package com.team1.animalproject.service;

import com.team1.animalproject.exception.BaseExceptionType;
import com.team1.animalproject.exception.ViewException;
import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.model.PetShop;
import com.team1.animalproject.model.PetShopAnimal;
import com.team1.animalproject.model.PetShopWorker;
import com.team1.animalproject.repository.PetShopAnimalRepository;
import com.team1.animalproject.repository.PetShopRepository;
import com.team1.animalproject.repository.PetShopWorkerRepository;
import com.team1.animalproject.view.utils.KullaniciTipiEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.UUID;

@Service
public class PetShopService implements IBaseService<PetShop> {

	@Qualifier ("petShopRepository")
	@Autowired
	private PetShopRepository petShopRepository;

	@Qualifier ("petShopWorkerRepository")
	@Autowired
	private PetShopWorkerRepository petShopWorkerRepository;

	@Qualifier ("petShopAnimalRepository")
	@Autowired
	private PetShopAnimalRepository petShopAnimalRepository;

	@Autowired
	private UserService userService;

	@Override
	public List<PetShop> getAll() {
		List<PetShop> all = petShopRepository.findAll();
		all.stream().forEach(petShop -> {
			int size = 0;
			List<PetShopWorker> byPetSopId = petShopWorkerRepository.findByPetShopId(petShop.getId());
			if(byPetSopId != null){
				petShop.setWorkerCount(byPetSopId.size());
			}
		});
		return all;
	}

	@Override
	public void save(PetShop petshop) {
		if(petshop.getId() == null) petshop.setId(UUID.randomUUID().toString());
		petShopRepository.save(petshop);
	}

	@Override
	public void update(PetShop petshop) {
		petShopRepository.save(petshop);
	}

	@Override
	public void delete(List<PetShop> t) {
		petShopRepository.delete(t);
	}

	public List<PetShopWorker> getWorkersByPetShopId(String petshopId) {
		return petShopWorkerRepository.findByPetShopId(petshopId);
	}

	@Transactional
	public void saveWorker(List<PetShopWorker> petshopWorkers) {

	    boolean gorevli = petshopWorkers.stream().anyMatch(shelterWorker -> userService.kullaniciBaskaYerdeGorevliMi(shelterWorker.getWorkerId(), KullaniciTipiEnum.SHELTER));
		if(!gorevli){
			List<PetShopWorker> all = petShopWorkerRepository.findAll();

			all.stream().forEach(shelterWorker -> {
				Kullanici kullanici = userService.findById(shelterWorker.getId()).get();
				kullanici.setKullaniciTipi(KullaniciTipiEnum.NORMAL_USER.getId());
				userService.save(kullanici);
			});

			petShopWorkerRepository.deleteByPetShopId(petshopWorkers.stream().findFirst().get().getPetShopId());
			petShopWorkerRepository.save(petshopWorkers);

			petshopWorkers.stream().forEach(shelterWorker -> {
				Kullanici kullanici = userService.findById(shelterWorker.getWorkerId()).get();
				kullanici.setKullaniciTipi(KullaniciTipiEnum.SHELTER.getId());
				userService.update(kullanici);
			});

		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, BaseExceptionType.KULLANICI_ZATEN_GOREVLI.getValidationMessage(), null));
			context.getExternalContext().getFlash().setKeepMessages(true);
			throw new ViewException();
		}

	}

	public List<PetShopAnimal> getAnimalsByPetShopId(String petshopId) {
		return petShopAnimalRepository.findByPetshopId(petshopId);
	}

	@Transactional
	public void saveAnimal(List<PetShopAnimal> petShopAnimals, String petshopId) {
		petShopAnimalRepository.deleteByPetshopId(petshopId);
		petShopAnimalRepository.save(petShopAnimals);
	}
}