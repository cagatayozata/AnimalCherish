package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.ShelterAnimal;
import com.team1.animalproject.preparer.ShelterAnimalPreparer;
import com.team1.animalproject.repository.ShelterAnimalRepository;
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
public class ShelterAnimalRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private ShelterAnimalRepository shelterAnimalRepository;

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			shelterAnimalRepository.save(ShelterAnimalPreparer.olustur());
		}

		List<ShelterAnimal> all = shelterAnimalRepository.findAll();
		Assert.assertEquals(all.size(), 5);
		shelterAnimalRepository.deleteAll();
	}

	@Test
	public void save() {
		ShelterAnimal shelterAnimal = ShelterAnimalPreparer.olustur();
		shelterAnimalRepository.saveAndFlush(shelterAnimal);

		ShelterAnimal savedShelterAnimal = shelterAnimalRepository.findById(shelterAnimal.getId()).get();
		Assert.assertEquals(shelterAnimal, savedShelterAnimal);
		shelterAnimalRepository.deleteAll();
	}

	@Test
	public void update() {
		ShelterAnimal shelterAnimal = ShelterAnimalPreparer.olustur();
		shelterAnimalRepository.saveAndFlush(shelterAnimal);

		ShelterAnimal savedShelterAnimal = shelterAnimalRepository.findById(shelterAnimal.getId()).get();
		Assert.assertEquals(shelterAnimal, savedShelterAnimal);

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedShelterAnimal.setAnimalId(toUpdate);
		shelterAnimalRepository.saveAndFlush(savedShelterAnimal);

		ShelterAnimal updated = shelterAnimalRepository.findById(shelterAnimal.getId()).get();
		Assert.assertEquals(updated.getAnimalId(), toUpdate);
		shelterAnimalRepository.deleteAll();
	}

	@Test
	public void delete() {
		ShelterAnimal shelterAnimal = ShelterAnimalPreparer.olustur();
		shelterAnimalRepository.saveAndFlush(shelterAnimal);

		ShelterAnimal savedShelterAnimal = shelterAnimalRepository.findById(shelterAnimal.getId()).get();
		Assert.assertEquals(shelterAnimal, savedShelterAnimal);

		shelterAnimalRepository.delete(savedShelterAnimal);

		Optional<ShelterAnimal> deleted = shelterAnimalRepository.findById(shelterAnimal.getId());
		Assert.assertFalse(deleted.isPresent());
		shelterAnimalRepository.deleteAll();
	}

	@SuppressWarnings ("OptionalGetWithoutIsPresent")
	@Test
	public void findById() {
		ShelterAnimal shelterAnimal = ShelterAnimalPreparer.olustur();
		shelterAnimalRepository.saveAndFlush(shelterAnimal);

		@SuppressWarnings ("OptionalGetWithoutIsPresent") ShelterAnimal savedShelterAnimal = shelterAnimalRepository.findById(shelterAnimal.getId()).get();
		Assert.assertEquals(shelterAnimal, savedShelterAnimal);
		shelterAnimalRepository.deleteAll();
	}

}
