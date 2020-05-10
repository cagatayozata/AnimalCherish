package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.ZooWorker;
import com.team1.animalproject.preparer.ZooWorkerPreparer;
import com.team1.animalproject.repository.ZooWorkerRepository;
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
public class ZooWorkerRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private ZooWorkerRepository zooWorkerRepository;

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			zooWorkerRepository.save(ZooWorkerPreparer.olustur());
		}

		List<ZooWorker> all = zooWorkerRepository.findAll();
		Assert.assertEquals(all.size(), 5);
		zooWorkerRepository.deleteAll();
	}

	@Test
	public void save() {
		ZooWorker zooWorker = ZooWorkerPreparer.olustur();
		zooWorkerRepository.saveAndFlush(zooWorker);

		ZooWorker savedZooWorker = zooWorkerRepository.findById(zooWorker.getId()).get();
		Assert.assertEquals(zooWorker, savedZooWorker);
		zooWorkerRepository.deleteAll();
	}

	@Test
	public void update() {
		ZooWorker zooWorker = ZooWorkerPreparer.olustur();
		zooWorkerRepository.saveAndFlush(zooWorker);

		ZooWorker savedZooWorker = zooWorkerRepository.findById(zooWorker.getId()).get();
		Assert.assertEquals(zooWorker, savedZooWorker);

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedZooWorker.setWorkerId(toUpdate);
		zooWorkerRepository.saveAndFlush(savedZooWorker);

		ZooWorker updated = zooWorkerRepository.findById(zooWorker.getId()).get();
		Assert.assertEquals(updated.getWorkerId(), toUpdate);
		zooWorkerRepository.deleteAll();
	}

	@Test
	public void delete() {
		ZooWorker zooWorker = ZooWorkerPreparer.olustur();
		zooWorkerRepository.saveAndFlush(zooWorker);

		ZooWorker savedZooWorker = zooWorkerRepository.findById(zooWorker.getId()).get();
		Assert.assertEquals(zooWorker, savedZooWorker);

		zooWorkerRepository.delete(savedZooWorker);

		Optional<ZooWorker> deleted = zooWorkerRepository.findById(zooWorker.getId());
		Assert.assertFalse(deleted.isPresent());
		zooWorkerRepository.deleteAll();
	}

	@Test
	public void findById() {
		ZooWorker zooWorker = ZooWorkerPreparer.olustur();
		zooWorkerRepository.saveAndFlush(zooWorker);

		ZooWorker savedZooWorker = zooWorkerRepository.findById(zooWorker.getId()).get();
		Assert.assertEquals(zooWorker, savedZooWorker);
		zooWorkerRepository.deleteAll();
	}

}
