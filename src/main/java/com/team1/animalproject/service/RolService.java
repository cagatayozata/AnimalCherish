package com.team1.animalproject.service;

import com.team1.animalproject.model.KullaniciRol;
import com.team1.animalproject.model.Rol;
import com.team1.animalproject.model.RolYetki;
import com.team1.animalproject.model.Yetki;
import com.team1.animalproject.repository.KullaniciRolRepository;
import com.team1.animalproject.repository.RolRepository;
import com.team1.animalproject.repository.RolYetkiRepository;
import com.team1.animalproject.repository.YetkiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

	@Qualifier("kullaniciRolRepository")
	@Autowired
	private KullaniciRolRepository kullaniciRolRepository;

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
		if(o.stream().anyMatch(rol -> rol.getId().equalsIgnoreCase("e1a34de4-48d5-4919-8661-3e54fb3e68e2")))
			return;
		rolRepository.delete(o);
	}

	@Transactional
	public void rolYetkiSave(List<RolYetki> rolYetkis, String rolId){
		rolYetkiRepository.deleteByRolId(rolId);
		rolYetkiRepository.save(rolYetkis);
	}

	public List<RolYetki> findByRolIdNotIn(String rolId){
		return rolYetkiRepository.findByRolIdNot(rolId);
	}

	public List<RolYetki> findByRolIdIn(String rolId){
		return rolYetkiRepository.findByRolId(rolId);
	}

	public List<Yetki> getAllYetkis(){
		return yetkiRepository.findAll();
	}

	public List<Yetki> findYetkiByIds(List<String> yetkiIds){
		return yetkiRepository.findByIdIn(yetkiIds);
	}

	public List<String> findByKullaniciId(String kullaniciId){
		List<String> yetkiKods = new ArrayList<>();
		List<KullaniciRol> byKullaniciId = kullaniciRolRepository.findByKullaniciId(kullaniciId);
		if(byKullaniciId != null){
			List<String> rolIds = byKullaniciId.stream().map(KullaniciRol::getRolId).collect(Collectors.toList());
			List<RolYetki> byRolIdIn = rolYetkiRepository.findByRolIdIn(rolIds);
			if(byRolIdIn != null){
				List<Yetki> byIdIn = yetkiRepository.findByIdIn(byRolIdIn.stream().map(RolYetki::getYetkiId).collect(Collectors.toList()));
				if(byIdIn != null){
					yetkiKods = byIdIn.stream().map(Yetki::getKod).collect(Collectors.toList());
				}
			}
		}
		return yetkiKods;
	}

	public List<KullaniciRol> findByRolIdKullanici(String rolId){
		return kullaniciRolRepository.findByRolId(rolId);
	}

	public List<String> herkesRoluYetkileriGetir() {
		List<String> yetkiKods = new ArrayList<>();
		List<RolYetki> byRolIdIn = rolYetkiRepository.findByRolId("e1a34de4-48d5-4919-8661-3e54fb3e68e2");
		if (byRolIdIn != null) {
			List<Yetki> byIdIn = yetkiRepository.findByIdIn(byRolIdIn.stream().map(RolYetki::getYetkiId).collect(Collectors.toList()));
			if (byIdIn != null) {
				yetkiKods = byIdIn.stream().map(Yetki::getKod).collect(Collectors.toList());
			}
		}
		return yetkiKods;
	}

	@Transactional
	public void saveKullanici(List<KullaniciRol> kullaniciRols, String rolId){
		kullaniciRolRepository.deleteByRolId(rolId);
		kullaniciRolRepository.save(kullaniciRols);
	}

}
