package com.team1.animalproject.service;

import com.team1.animalproject.model.Tur;
import com.team1.animalproject.repository.TurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TurService implements IBaseService<Tur> {

	@Qualifier ("turRepository")
	@Autowired
	private TurRepository turRepository;

	@Autowired
	private CinsService cinsService;

	@Override
	public List<Tur> getAll() {
		return turRepository.findAllByDurum(true);
	}

	public List<Tur> ara() {
		return turRepository.findAll();
	}

	@Override
	public void save(Tur tur) {
		if(tur.getId() == null) tur.setId(UUID.randomUUID().toString());
		if(!tur.isDurum()){
			tur.setDurum(false);
		}
		turRepository.save(tur);
	}

	@Override
	public void update(Tur tur) {
		turRepository.save(tur);
	}

	@Override
	public void delete(List<Tur> t) {
		// Animal varsa uyari var
		t.stream().forEach(tur -> cinsService.deleteByTurId(tur.getId()));
		turRepository.delete(t);
	}

	public Tur findById(String id) {
		return turRepository.findById(id);
	}
}