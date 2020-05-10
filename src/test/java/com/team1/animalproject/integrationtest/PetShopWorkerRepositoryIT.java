package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.PetShopWorker;
import com.team1.animalproject.preparer.PetShopWorkerPreparer;
import com.team1.animalproject.repository.PetShopWorkerRepository;
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
public class PetShopWorkerRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private PetShopWorkerRepository petShopWorkerRepository;

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			petShopWorkerRepository.save(PetShopWorkerPreparer.olustur());
		}

		List<PetShopWorker> all = petShopWorkerRepository.findAll();
		Assert.assertEquals(all.size(), 5);
		petShopWorkerRepository.deleteAll();
	}

	@Test
	public void save() {
		PetShopWorker petShopWorker = PetShopWorkerPreparer.olustur();
		petShopWorkerRepository.saveAndFlush(petShopWorker);

		PetShopWorker savedPetShopWorker = petShopWorkerRepository.findById(petShopWorker.getId()).get();
		Assert.assertEquals(petShopWorker, savedPetShopWorker);
		petShopWorkerRepository.deleteAll();
	}

	@Test
	public void update() {
		PetShopWorker petShopWorker = PetShopWorkerPreparer.olustur();
		petShopWorkerRepository.saveAndFlush(petShopWorker);

		PetShopWorker savedPetShopWorker = petShopWorkerRepository.findById(petShopWorker.getId()).get();
		Assert.assertEquals(petShopWorker, savedPetShopWorker);

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedPetShopWorker.setPetShopId(toUpdate);
		petShopWorkerRepository.saveAndFlush(savedPetShopWorker);

		PetShopWorker updated = petShopWorkerRepository.findById(petShopWorker.getId()).get();
		Assert.assertEquals(updated.getPetShopId(), toUpdate);
		petShopWorkerRepository.deleteAll();
	}

	@Test
	public void delete() {
		PetShopWorker petShopWorker = PetShopWorkerPreparer.olustur();
		petShopWorkerRepository.saveAndFlush(petShopWorker);

		PetShopWorker savedPetShopWorker = petShopWorkerRepository.findById(petShopWorker.getId()).get();
		Assert.assertEquals(petShopWorker, savedPetShopWorker);

		petShopWorkerRepository.delete(savedPetShopWorker);

		Optional<PetShopWorker> deleted = petShopWorkerRepository.findById(petShopWorker.getId());
		Assert.assertFalse(deleted.isPresent());
		petShopWorkerRepository.deleteAll();
	}

	@Test
	public void findById() {
		PetShopWorker petShopWorker = PetShopWorkerPreparer.olustur();
		petShopWorkerRepository.saveAndFlush(petShopWorker);

		PetShopWorker savedPetShopWorker = petShopWorkerRepository.findById(petShopWorker.getId()).get();
		Assert.assertEquals(petShopWorker, savedPetShopWorker);
		petShopWorkerRepository.deleteAll();
	}

}
