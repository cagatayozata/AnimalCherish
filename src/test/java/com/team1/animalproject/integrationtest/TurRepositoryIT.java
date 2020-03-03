package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.Tur;
import com.team1.animalproject.preparer.TurPreparer;
import com.team1.animalproject.repository.TurRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith (SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class TurRepositoryIT {

	@Autowired
	private TurRepository turRepository;

	@Test
	public void ara() {
		for(int i = 0; i < 5; i++){
			turRepository.save(TurPreparer.olustur(true));
		}

		List<Tur> turler = turRepository.findAll();
		Assert.assertTrue(turler.size() == 5);
	}

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			turRepository.save(TurPreparer.olustur(false));
		}

		for(int i = 0; i < 5; i++){
			turRepository.save(TurPreparer.olustur(true));
		}

		List<Tur> allByDurum = turRepository.findAllByDurum(false);
		Assert.assertTrue(allByDurum.size() == 5);
	}

	@Test
	public void save() {
		Tur tur = TurPreparer.olustur(true);
		turRepository.saveAndFlush(tur);

		Tur savedTur = turRepository.findById(tur.getId());
		Assert.assertTrue(savedTur.equals(tur));
	}

}
