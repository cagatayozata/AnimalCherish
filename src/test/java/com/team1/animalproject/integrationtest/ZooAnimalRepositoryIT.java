package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.ZooAnimal;
import com.team1.animalproject.preparer.ZooAnimalPreparer;
import com.team1.animalproject.repository.ZooAnimalRepository;
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
public class ZooAnimalRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private ZooAnimalRepository zooAnimalRepository;

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			zooAnimalRepository.save(ZooAnimalPreparer.olustur());
		}

		List<ZooAnimal> all = zooAnimalRepository.findAll();
		Assert.assertTrue(all.size() == 5);
		zooAnimalRepository.deleteAll();
	}

	@Test
	public void save() {
		ZooAnimal zooAnimal = ZooAnimalPreparer.olustur();
		zooAnimalRepository.saveAndFlush(zooAnimal);

		ZooAnimal savedZooAnimal = zooAnimalRepository.findById(zooAnimal.getId()).get();
		Assert.assertTrue(savedZooAnimal.equals(zooAnimal));
		zooAnimalRepository.deleteAll();
	}

	@Test
	public void update() {
		ZooAnimal zooAnimal = ZooAnimalPreparer.olustur();
		zooAnimalRepository.saveAndFlush(zooAnimal);

		ZooAnimal savedZooAnimal = zooAnimalRepository.findById(zooAnimal.getId()).get();
		Assert.assertTrue(savedZooAnimal.equals(zooAnimal));

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedZooAnimal.setAnimalId(toUpdate);
		zooAnimalRepository.saveAndFlush(savedZooAnimal);

		ZooAnimal updated = zooAnimalRepository.findById(zooAnimal.getId()).get();
		Assert.assertEquals(updated.getAnimalId(), toUpdate);
		zooAnimalRepository.deleteAll();
	}

	@Test
	public void delete() {
		ZooAnimal zooAnimal = ZooAnimalPreparer.olustur();
		zooAnimalRepository.saveAndFlush(zooAnimal);

		ZooAnimal savedZooAnimal = zooAnimalRepository.findById(zooAnimal.getId()).get();
		Assert.assertTrue(savedZooAnimal.equals(zooAnimal));

		zooAnimalRepository.delete(savedZooAnimal);

		Optional<ZooAnimal> deleted = zooAnimalRepository.findById(zooAnimal.getId());
		Assert.assertFalse(deleted.isPresent());
		zooAnimalRepository.deleteAll();
	}

	@Test
	public void findById() {
		ZooAnimal zooAnimal = ZooAnimalPreparer.olustur();
		zooAnimalRepository.saveAndFlush(zooAnimal);

		ZooAnimal savedZooAnimal = zooAnimalRepository.findById(zooAnimal.getId()).get();
		Assert.assertTrue(savedZooAnimal.equals(zooAnimal));
		zooAnimalRepository.deleteAll();
	}

}
