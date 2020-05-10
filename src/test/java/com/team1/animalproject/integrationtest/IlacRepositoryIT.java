package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.Ilac;
import com.team1.animalproject.preparer.IlacPreparer;
import com.team1.animalproject.repository.IlacRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

@SuppressWarnings ("ALL")
@EnableAutoConfiguration
@TestPropertySource (locations = "classpath:/application-test.properties")
@SpringBootTest
public class IlacRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private IlacRepository ilacRepository;

	@Test
	public void ara() {
		for(int i = 0; i < 5; i++){
			ilacRepository.save(IlacPreparer.olustur());
		}

		List<Ilac> all = ilacRepository.findAll();
		Assert.assertEquals(all.size(), 5);
		ilacRepository.deleteAll();
	}

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			ilacRepository.save(IlacPreparer.olustur());
		}

		List<Ilac> all = ilacRepository.findAllByDurum(true);
		Assert.assertEquals(all.size(), 5);
		ilacRepository.deleteAll();
	}

	@Test
	public void save() {
		Ilac ilac = IlacPreparer.olustur();
		ilacRepository.saveAndFlush(ilac);

		Ilac savedIlac = ilacRepository.findById(ilac.getId()).get();
		Assert.assertEquals(ilac, savedIlac);
		ilacRepository.deleteAll();
	}

	@Test
	public void update() {
		Ilac ilac = IlacPreparer.olustur();
		ilacRepository.saveAndFlush(ilac);

		Ilac savedIlac = ilacRepository.findById(ilac.getId()).get();
		Assert.assertEquals(ilac, savedIlac);

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedIlac.setName(toUpdate);
		ilacRepository.saveAndFlush(savedIlac);

		Ilac updated = ilacRepository.findById(ilac.getId()).get();
		Assert.assertEquals(updated.getName(), toUpdate);
		ilacRepository.deleteAll();
	}

	@Test
	public void delete() {
		Ilac ilac = IlacPreparer.olustur();
		ilacRepository.saveAndFlush(ilac);

		Ilac savedIlac = ilacRepository.findById(ilac.getId()).get();
		Assert.assertEquals(ilac, savedIlac);

		ilacRepository.delete(savedIlac);

		Optional<Ilac> deleted = ilacRepository.findById(ilac.getId());
		Assert.assertFalse(deleted.isPresent());
		ilacRepository.deleteAll();
	}

	@Test
	public void findById() {
		Ilac ilac = IlacPreparer.olustur();
		ilacRepository.saveAndFlush(ilac);

		Ilac savedIlac = ilacRepository.findById(ilac.getId()).get();
		Assert.assertEquals(ilac, savedIlac);
		ilacRepository.deleteAll();
	}


}
