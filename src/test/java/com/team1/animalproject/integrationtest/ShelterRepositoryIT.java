package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.Shelter;
import com.team1.animalproject.preparer.ShelterPreparer;
import com.team1.animalproject.repository.ShelterRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

@SuppressWarnings ("ALL")
@EnableAutoConfiguration
@TestPropertySource (locations = "classpath:/application-test.properties")
@SpringBootTest
public class ShelterRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private ShelterRepository shelterRepository;

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			shelterRepository.save(ShelterPreparer.olustur());
		}

		List<Shelter> all = shelterRepository.findAll();
		Assert.assertEquals(all.size(), 5);
		shelterRepository.deleteAll();
	}

	@Test
	public void save() {
		Shelter shelter = ShelterPreparer.olustur();
		shelterRepository.saveAndFlush(shelter);

		Shelter savedShelter = shelterRepository.findById(shelter.getId()).get();
		Assert.assertEquals(shelter, savedShelter);
		shelterRepository.deleteAll();
	}

	@Test
	public void update() {
		Shelter shelter = ShelterPreparer.olustur();
		shelterRepository.saveAndFlush(shelter);

		Shelter savedShelter = shelterRepository.findById(shelter.getId()).get();
		Assert.assertEquals(shelter, savedShelter);

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedShelter.setName(toUpdate);
		shelterRepository.saveAndFlush(savedShelter);

		Shelter updated = shelterRepository.findById(shelter.getId()).get();
		Assert.assertEquals(updated.getName(), toUpdate);
		shelterRepository.deleteAll();
	}

	@Test
	public void delete() {
		Shelter shelter = ShelterPreparer.olustur();
		shelterRepository.saveAndFlush(shelter);

		Shelter savedShelter = shelterRepository.findById(shelter.getId()).get();
		Assert.assertEquals(shelter, savedShelter);

		shelterRepository.delete(savedShelter);

		Optional<Shelter> deleted = shelterRepository.findById(shelter.getId());
		Assert.assertFalse(deleted.isPresent());
		shelterRepository.deleteAll();
	}

	@Test
	public void findById() {
		Shelter shelter = ShelterPreparer.olustur();
		shelterRepository.saveAndFlush(shelter);

		Shelter savedShelter = shelterRepository.findById(shelter.getId()).get();
		Assert.assertEquals(shelter, savedShelter);
		shelterRepository.deleteAll();
	}

}
