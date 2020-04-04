package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.Cins;
import com.team1.animalproject.preparer.CinsPreparer;
import com.team1.animalproject.repository.CinsRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

@EnableAutoConfiguration
@TestPropertySource (locations = "classpath:/application-test.properties")
@SpringBootTest
@Transactional
public class CinsRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private CinsRepository cinsRepository;

	@Test
	public void ara() {
		for(int i = 0; i < 5; i++){
			cinsRepository.save(CinsPreparer.olustur());
		}

		List<Cins> all = cinsRepository.cinsAra();
		Assert.assertTrue(all.size() == 5);
		cinsRepository.deleteAll();
	}

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			cinsRepository.save(CinsPreparer.olustur());
		}

		List<Cins> all = cinsRepository.findAllByDurum(true);
		Assert.assertTrue(all.size() == 5);
		cinsRepository.deleteAll();
	}

	@Test
	public void save() {
		Cins cins = CinsPreparer.olustur();
		cinsRepository.saveAndFlush(cins);

		Cins savedCins = cinsRepository.findById(cins.getId()).get();
		Assert.assertTrue(savedCins.equals(cins));
		cinsRepository.deleteAll();
	}

	@Test
	public void update() {
		Cins cins = CinsPreparer.olustur();
		cinsRepository.saveAndFlush(cins);

		Cins savedCins = cinsRepository.findById(cins.getId()).get();
		Assert.assertTrue(savedCins.equals(cins));

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedCins.setName(toUpdate);
		cinsRepository.saveAndFlush(savedCins);

		Cins updated = cinsRepository.findById(cins.getId()).get();
		Assert.assertEquals(updated.getName(), toUpdate);
		cinsRepository.deleteAll();
	}

	@Test
	public void delete() {
		Cins cins = CinsPreparer.olustur();
		cinsRepository.saveAndFlush(cins);

		Cins savedCins = cinsRepository.findById(cins.getId()).get();
		Assert.assertTrue(savedCins.equals(cins));

		cinsRepository.delete(savedCins);

		Optional<Cins> deleted = cinsRepository.findById(cins.getId());
		Assert.assertFalse(deleted.isPresent());
		cinsRepository.deleteAll();
	}

	@Test
	public void findById() {
		Cins cins = CinsPreparer.olustur();
		cinsRepository.saveAndFlush(cins);

		Cins savedCins = cinsRepository.findById(cins.getId()).get();
		Assert.assertTrue(savedCins.equals(cins));
		cinsRepository.deleteAll();
	}
}
