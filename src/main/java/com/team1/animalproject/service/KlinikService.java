package com.team1.animalproject.service;

import com.team1.animalproject.model.Klinik;
import com.team1.animalproject.model.KlinikVet;
import com.team1.animalproject.repository.KlinikRepository;
import com.team1.animalproject.repository.KlinikVetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class KlinikService implements IBaseService<Klinik> {

	@Qualifier ("klinikRepository")
	@Autowired
	private KlinikRepository klinikRepository;

	@Qualifier ("klinikVetRepository")
	@Autowired
	private KlinikVetRepository klinikVetRepository;

	@Autowired
	private VetService vetService;

	@Override
	public List<Klinik> getAll() {
		List<Klinik> all = klinikRepository.findAll();
		all.stream().forEach(klinik -> {
			int size = 0;
			List<KlinikVet> klinikVets = klinikVetRepository.findByKlinikId(klinik.getId());
			if(klinikVets != null){
				klinik.setWorkerCount(klinikVets.size());
			}
		});
		return all;
	}

	@Override
	public void save(Klinik klinik) {
		if(klinik.getId() == null) klinik.setId(UUID.randomUUID().toString());
		klinikRepository.save(klinik);
	}

	@Override
	public void update(Klinik klinik) {
		klinikRepository.save(klinik);
	}

	@Override
	public void delete(List<Klinik> t) {
		klinikRepository.delete(t);
	}

	public List<KlinikVet> getWorkersByKlinikId(String klinikId) {
		return klinikVetRepository.findByKlinikId(klinikId);
	}

	@Transactional
	public void saveWorker(List<KlinikVet> klinikWorkers) {

		klinikVetRepository.deleteByKlinikId(klinikWorkers.stream().findFirst().get().getKlinikId());
		klinikVetRepository.save(klinikWorkers);

	}
}