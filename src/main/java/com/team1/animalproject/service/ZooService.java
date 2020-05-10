package com.team1.animalproject.service;

import com.team1.animalproject.exception.BaseExceptionType;
import com.team1.animalproject.exception.ViewException;
import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.model.Zoo;
import com.team1.animalproject.model.ZooAnimal;
import com.team1.animalproject.model.ZooWorker;
import com.team1.animalproject.repository.ZooRepository;
import com.team1.animalproject.repository.ZooAnimalRepository;
import com.team1.animalproject.repository.ZooWorkerRepository;
import com.team1.animalproject.view.utils.KullaniciTipiEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.UUID;

@SuppressWarnings ("ALL")
@Service
public class ZooService implements IBaseService<Zoo> {

	@Qualifier ("zooRepository")
	@Autowired
	private ZooRepository zooRepository;

	@Qualifier ("zooWorkerRepository")
	@Autowired
	private ZooWorkerRepository zooWorkerRepository;

	@Qualifier ("zooAnimalRepository")
	@Autowired
	private ZooAnimalRepository zooAnimalRepository;

	@Autowired
	private UserService userService;

	@Override
	public List<Zoo> getAll() {
		List<Zoo> all = zooRepository.findAll();
		all.forEach(zoo -> {
			int size = 0;
			List<ZooWorker> byShelterId = zooWorkerRepository.findByZooId(zoo.getId());
			if(byShelterId != null){
				zoo.setWorkerCount(byShelterId.size());
			}
		});
		return all;
	}

	@Override
	public void save(Zoo zoo) {
		if(zoo.getId() == null) zoo.setId(UUID.randomUUID().toString());
		zooRepository.save(zoo);
	}

	@Override
	public void update(Zoo zoo) {
		zooRepository.save(zoo);
	}

	@Override
	public void delete(List<Zoo> t) {
		zooRepository.delete(t);
	}

	public List<ZooWorker> getWorkersByShelterId(String zooId) {
		return zooWorkerRepository.findByZooId(zooId);
	}

	@Transactional
	public void saveWorker(List<ZooWorker> zooWorkers, String zooId) {
		boolean gorevli = zooWorkers.stream().anyMatch(shelterWorker -> userService.kullaniciBaskaYerdeGorevliMi(shelterWorker.getWorkerId(), KullaniciTipiEnum.SHELTER));
		if(!gorevli){
			List<ZooWorker> all = zooWorkerRepository.findAll();

			all.forEach(shelterWorker -> {
				Kullanici kullanici = userService.findById(shelterWorker.getId()).get();
				kullanici.setKullaniciTipi(KullaniciTipiEnum.NORMAL_USER.getId());
				userService.save(kullanici);
			});

			zooWorkerRepository.deleteByZooId(zooId);
			zooWorkerRepository.save(zooWorkers);

			zooWorkers.forEach(shelterWorker -> {
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

	public List<ZooAnimal> getAnimalsByZooId(String zooId) {
		return zooAnimalRepository.findByZooId(zooId);
	}

	@Transactional
	public void saveAnimal(List<ZooAnimal> zooAnimals, String zooId) {
		zooAnimalRepository.deleteByZooId(zooId);
		zooAnimalRepository.save(zooAnimals);
	}
}