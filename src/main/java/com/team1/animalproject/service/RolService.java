package com.team1.animalproject.service;

import com.team1.animalproject.model.Rol;
import com.team1.animalproject.model.RolYetki;
import com.team1.animalproject.model.Yetki;
import com.team1.animalproject.repository.RolRepository;
import com.team1.animalproject.repository.RolYetkiRepository;
import com.team1.animalproject.repository.YetkiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService implements IBaseService<Rol> {

	@Qualifier("rolRepository")
	@Autowired
	private RolRepository rolRepository;

	@Qualifier("yetkiRepository")
	@Autowired
	private YetkiRepository yetkiRepository;

	@Qualifier("rolYetkiRepository")
	@Autowired
	private RolYetkiRepository rolYetkiRepository;


	@Override
	public List<Rol> getAll() {
		return rolRepository.findAll();
	}

	@Override
	public void save(Rol o) {
		rolRepository.save(o);
	}

	@Override
	public void update(Rol o) {
		rolRepository.save(o);
	}

	@Override
	public void delete(List<Rol> o) {
		rolRepository.delete(o);
	}

	public void rolYetkiSave(List<RolYetki> rolYetkis){
		rolYetkiRepository.save(rolYetkis);
	}

	public List<RolYetki> findByRolIdNotIn(String rolId){
		return rolYetkiRepository.findByRolIdNotIn(rolId);
	}

	public List<RolYetki> findByRolIdIn(String rolId){
		return rolYetkiRepository.findByRolIdIn(rolId);
	}

	public List<Yetki> getAllYetkis(){
		return yetkiRepository.findAll();
	}
}
