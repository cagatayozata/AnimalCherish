package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.PetShopAnimal;
import com.team1.animalproject.preparer.PetShopAnimalPreparer;
import com.team1.animalproject.repository.PetShopAnimalRepository;
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

@EnableAutoConfiguration
@TestPropertySource (locations = "classpath:/application-test.properties")
@SpringBootTest
public class PetShopAnimalRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private PetShopAnimalRepository petShopAnimalRepository;

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			petShopAnimalRepository.save(PetShopAnimalPreparer.olustur());
		}

		List<PetShopAnimal> all = petShopAnimalRepository.findAll();
		Assert.assertTrue(all.size() == 5);
		petShopAnimalRepository.deleteAll();
	}

	@Test
	public void save() {
		PetShopAnimal petShopAnimal = PetShopAnimalPreparer.olustur();
		petShopAnimalRepository.saveAndFlush(petShopAnimal);

		PetShopAnimal savedPetShopAnimal = petShopAnimalRepository.findById(petShopAnimal.getId()).get();
		Assert.assertTrue(savedPetShopAnimal.equals(petShopAnimal));
		petShopAnimalRepository.deleteAll();
	}

	@Test
	public void update() {
		PetShopAnimal petShopAnimal = PetShopAnimalPreparer.olustur();
		petShopAnimalRepository.saveAndFlush(petShopAnimal);

		PetShopAnimal savedPetShopAnimal = petShopAnimalRepository.findById(petShopAnimal.getId()).get();
		Assert.assertTrue(savedPetShopAnimal.equals(petShopAnimal));

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedPetShopAnimal.setAnimalId(toUpdate);
		petShopAnimalRepository.saveAndFlush(savedPetShopAnimal);

		PetShopAnimal updated = petShopAnimalRepository.findById(petShopAnimal.getId()).get();
		Assert.assertEquals(updated.getAnimalId(), toUpdate);
		petShopAnimalRepository.deleteAll();
	}

	@Test
	public void delete() {
		PetShopAnimal petShopAnimal = PetShopAnimalPreparer.olustur();
		petShopAnimalRepository.saveAndFlush(petShopAnimal);

		PetShopAnimal savedPetShopAnimal = petShopAnimalRepository.findById(petShopAnimal.getId()).get();
		Assert.assertTrue(savedPetShopAnimal.equals(petShopAnimal));

		petShopAnimalRepository.delete(savedPetShopAnimal);

		Optional<PetShopAnimal> deleted = petShopAnimalRepository.findById(petShopAnimal.getId());
		Assert.assertFalse(deleted.isPresent());
		petShopAnimalRepository.deleteAll();
	}

	@Test
	public void findById() {
		PetShopAnimal petShopAnimal = PetShopAnimalPreparer.olustur();
		petShopAnimalRepository.saveAndFlush(petShopAnimal);

		PetShopAnimal savedPetShopAnimal = petShopAnimalRepository.findById(petShopAnimal.getId()).get();
		Assert.assertTrue(savedPetShopAnimal.equals(petShopAnimal));
		petShopAnimalRepository.deleteAll();
	}

}
