package com.team1.animalproject.service;

import com.team1.animalproject.model.Animal;
import com.team1.animalproject.model.AnimalTarihce;
import com.team1.animalproject.model.AnimalTarihceDetay;
import com.team1.animalproject.model.MedicalReport;
import com.team1.animalproject.repository.AnimalRepository;
import com.team1.animalproject.view.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AnimalService implements IBaseService<Animal> {

	@Qualifier ("animalRepository")
	@Autowired
	private AnimalRepository animalRepository;

	@Autowired
	private BlockchainService blockchainService;

	@Override
	public List<Animal> getAll() {
		return animalRepository.animalAra();
	}

	@Override
	public void save(Animal o) {
		String islem = "";
		String deger = "";

		if(o.getOlusmaTarihi() != null){
			islem = "Hayvan sayfasında hayvan güncelleme işlemi";
			deger = "Güncellendi";
		} else {
			islem = "Hayvan sayfasında hayvan ekleme işlemi";
			deger = "Eklendi";
		}

		Animal save = animalRepository.save(o);
		AnimalTarihce animalTarihce = AnimalTarihce.builder().animalId(o.id).deger(deger).kimTarafindan(o.guncelleyenId).neZaman(DateUtil.nowAsString()).yapilanIslem(islem).build();
		tarihceKaydet(animalTarihce);
	}

	@Override
	public void update(Animal o) {
		Animal save = animalRepository.save(o);
		AnimalTarihce animalTarihce = AnimalTarihce.builder()
				.animalId(o.id)
				.deger("Güncellendi")
				.kimTarafindan(o.guncelleyenId)
				.neZaman(DateUtil.nowAsString())
				.yapilanIslem("Hayvan sayfasında hayvan güncelleme işlemi")
				.build();
		tarihceKaydet(animalTarihce);
	}

	@Override
	public void delete(List<Animal> o) {
		o.stream().forEach(animal -> {
			try{
				List<MedicalReport> allByAnimalId = blockchainService.getAllByAnimalId(animal.id);
				if(CollectionUtils.isEmpty(allByAnimalId)){
					animalRepository.delete(animal);
				}
			} catch (IOException e){
				e.printStackTrace();
			}
		});
	}

	public long toplamSayi() {
		return animalRepository.count();
	}

	public Optional<List<Animal>> findByIdIn(List<String> ids) {
		return animalRepository.findByIdIn(ids);
	}

	public Map<Integer, Long> sonYediGunIcinEklenenHayvanVerileriniGetir() {
		return animalRepository.sonYediGunIcinEklenenHayvanVerileriniGetir();
	}

	public AnimalTarihceDetay hayvanNerede(String animalId) {
		return animalRepository.hayvanNerede(animalId);
	}

	public void tarihceKaydet(AnimalTarihce animalTarihce) {
		try{
			blockchainService.transactionOlustur(animalTarihce);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
