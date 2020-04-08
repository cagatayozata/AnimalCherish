package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.ShelterWorker;
import com.team1.animalproject.preparer.ShelterWorkerPreparer;
import com.team1.animalproject.repository.ShelterWorkerRepository;
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
public class ShelterWorkerRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private ShelterWorkerRepository shelterWorkerRepository;

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			shelterWorkerRepository.save(ShelterWorkerPreparer.olustur());
		}

		List<ShelterWorker> all = shelterWorkerRepository.findAll();
		Assert.assertTrue(all.size() == 5);
		shelterWorkerRepository.deleteAll();
	}

	@Test
	public void save() {
		ShelterWorker shelterWorker = ShelterWorkerPreparer.olustur();
		shelterWorkerRepository.saveAndFlush(shelterWorker);

		ShelterWorker savedShelterWorker = shelterWorkerRepository.findById(shelterWorker.getId()).get();
		Assert.assertTrue(savedShelterWorker.equals(shelterWorker));
		shelterWorkerRepository.deleteAll();
	}

	@Test
	public void update() {
		ShelterWorker shelterWorker = ShelterWorkerPreparer.olustur();
		shelterWorkerRepository.saveAndFlush(shelterWorker);

		ShelterWorker savedShelterWorker = shelterWorkerRepository.findById(shelterWorker.getId()).get();
		Assert.assertTrue(savedShelterWorker.equals(shelterWorker));

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedShelterWorker.setShelterId(toUpdate);
		shelterWorkerRepository.saveAndFlush(savedShelterWorker);

		ShelterWorker updated = shelterWorkerRepository.findById(shelterWorker.getId()).get();
		Assert.assertEquals(updated.getShelterId(), toUpdate);
		shelterWorkerRepository.deleteAll();
	}

	@Test
	public void delete() {
		ShelterWorker shelterWorker = ShelterWorkerPreparer.olustur();
		shelterWorkerRepository.saveAndFlush(shelterWorker);

		ShelterWorker savedShelterWorker = shelterWorkerRepository.findById(shelterWorker.getId()).get();
		Assert.assertTrue(savedShelterWorker.equals(shelterWorker));

		shelterWorkerRepository.delete(savedShelterWorker);

		Optional<ShelterWorker> deleted = shelterWorkerRepository.findById(shelterWorker.getId());
		Assert.assertFalse(deleted.isPresent());
		shelterWorkerRepository.deleteAll();
	}

	@Test
	public void findById() {
		ShelterWorker shelterWorker = ShelterWorkerPreparer.olustur();
		shelterWorkerRepository.saveAndFlush(shelterWorker);

		ShelterWorker savedShelterWorker = shelterWorkerRepository.findById(shelterWorker.getId()).get();
		Assert.assertTrue(savedShelterWorker.equals(shelterWorker));
		shelterWorkerRepository.deleteAll();
	}

}
