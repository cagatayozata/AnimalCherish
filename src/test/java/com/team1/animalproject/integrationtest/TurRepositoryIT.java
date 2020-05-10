package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.Tur;
import com.team1.animalproject.preparer.TurPreparer;
import com.team1.animalproject.repository.TurRepository;
import org.testng.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

@SuppressWarnings ("ALL")
@EnableAutoConfiguration
@TestPropertySource (locations = "classpath:/application-test.properties")
@SpringBootTest
public class TurRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private TurRepository turRepository;

	@Test
	public void ara() {
		for(int i = 0; i < 5; i++){
			turRepository.save(TurPreparer.olustur(true));
		}

		List<Tur> turler = turRepository.findAll();
		Assert.assertEquals(turler.size(), 5);
		turRepository.deleteAll();
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
		Assert.assertEquals(allByDurum.size(), 5);
		turRepository.deleteAll();
	}

	@Test
	public void save() {
		Tur tur = TurPreparer.olustur(true);
		turRepository.saveAndFlush(tur);

		Tur savedTur = turRepository.findById(tur.getId()).get();
		Assert.assertEquals(tur, savedTur);
		turRepository.deleteAll();
	}

}
