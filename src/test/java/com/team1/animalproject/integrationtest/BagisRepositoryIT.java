package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.Bagis;
import com.team1.animalproject.preparer.BagisPreparer;
import com.team1.animalproject.repository.BagisRepository;
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

@EnableAutoConfiguration
@TestPropertySource (locations = "classpath:/application-test.properties")
@SpringBootTest
public class BagisRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private BagisRepository bagisRepository;

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			bagisRepository.save(BagisPreparer.olustur());
		}

		List<Bagis> all = bagisRepository.findAll();
		Assert.assertEquals(all.size(), 5);
		bagisRepository.deleteAll();
	}

	@Test
	public void save() {
		Bagis bagis = BagisPreparer.olustur();
		bagisRepository.saveAndFlush(bagis);

		//noinspection OptionalGetWithoutIsPresent
		Bagis savedBagis = bagisRepository.findById(bagis.getId()).get();
		Assert.assertEquals(bagis, savedBagis);
		bagisRepository.deleteAll();
	}

	@Test
	public void update() {
		Bagis bagis = BagisPreparer.olustur();
		bagisRepository.saveAndFlush(bagis);

		//noinspection OptionalGetWithoutIsPresent
		Bagis savedBagis = bagisRepository.findById(bagis.getId()).get();
		Assert.assertEquals(bagis, savedBagis);

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedBagis.setName(toUpdate);
		bagisRepository.saveAndFlush(savedBagis);

		//noinspection OptionalGetWithoutIsPresent
		Bagis updated = bagisRepository.findById(bagis.getId()).get();
		Assert.assertEquals(updated.getName(), toUpdate);
		bagisRepository.deleteAll();
	}

	@Test
	public void delete() {
		Bagis bagis = BagisPreparer.olustur();
		bagisRepository.saveAndFlush(bagis);

		//noinspection OptionalGetWithoutIsPresent
		Bagis savedBagis = bagisRepository.findById(bagis.getId()).get();
		Assert.assertEquals(bagis, savedBagis);

		bagisRepository.delete(savedBagis);

		Optional<Bagis> deleted = bagisRepository.findById(bagis.getId());
		Assert.assertFalse(deleted.isPresent());
		bagisRepository.deleteAll();
	}

	@Test
	public void findById() {
		Bagis bagis = BagisPreparer.olustur();
		bagisRepository.saveAndFlush(bagis);

		//noinspection OptionalGetWithoutIsPresent
		Bagis savedBagis = bagisRepository.findById(bagis.getId()).get();
		Assert.assertEquals(bagis, savedBagis);
		bagisRepository.deleteAll();
	}

}
