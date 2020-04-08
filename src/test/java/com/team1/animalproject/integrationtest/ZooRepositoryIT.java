package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.Zoo;
import com.team1.animalproject.preparer.ZooPreparer;
import com.team1.animalproject.repository.ZooRepository;
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
public class ZooRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private ZooRepository zooRepository;

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			zooRepository.save(ZooPreparer.olustur());
		}

		List<Zoo> all = zooRepository.findAll();
		Assert.assertTrue(all.size() == 5);
		zooRepository.deleteAll();
	}

	@Test
	public void save() {
		Zoo zoo = ZooPreparer.olustur();
		zooRepository.saveAndFlush(zoo);

		Zoo savedZoo = zooRepository.findById(zoo.getId()).get();
		Assert.assertTrue(savedZoo.equals(zoo));
		zooRepository.deleteAll();
	}

	@Test
	public void update() {
		Zoo zoo = ZooPreparer.olustur();
		zooRepository.saveAndFlush(zoo);

		Zoo savedZoo = zooRepository.findById(zoo.getId()).get();
		Assert.assertTrue(savedZoo.equals(zoo));

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedZoo.setName(toUpdate);
		zooRepository.saveAndFlush(savedZoo);

		Zoo updated = zooRepository.findById(zoo.getId()).get();
		Assert.assertEquals(updated.getName(), toUpdate);
		zooRepository.deleteAll();
	}

	@Test
	public void delete() {
		Zoo zoo = ZooPreparer.olustur();
		zooRepository.saveAndFlush(zoo);

		Zoo savedZoo = zooRepository.findById(zoo.getId()).get();
		Assert.assertTrue(savedZoo.equals(zoo));

		zooRepository.delete(savedZoo);

		Optional<Zoo> deleted = zooRepository.findById(zoo.getId());
		Assert.assertFalse(deleted.isPresent());
		zooRepository.deleteAll();
	}

	@Test
	public void findById() {
		Zoo zoo = ZooPreparer.olustur();
		zooRepository.saveAndFlush(zoo);

		Zoo savedZoo = zooRepository.findById(zoo.getId()).get();
		Assert.assertTrue(savedZoo.equals(zoo));
		zooRepository.deleteAll();
	}

}
