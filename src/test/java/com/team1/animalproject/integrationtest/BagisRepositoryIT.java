package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.Bagis;
import com.team1.animalproject.preparer.BagisPreparer;
import com.team1.animalproject.repository.BagisRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith (SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class BagisRepositoryIT {

	@Autowired
	private BagisRepository bagisRepository;

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			bagisRepository.save(BagisPreparer.olustur());
		}

		List<Bagis> all = bagisRepository.findAll();
		Assert.assertTrue(all.size() == 5);
	}

	@Test
	public void save() {
		Bagis bagis = BagisPreparer.olustur();
		bagisRepository.saveAndFlush(bagis);

		Bagis savedBagis = bagisRepository.findById(bagis.getId()).get();
		Assert.assertTrue(savedBagis.equals(bagis));
	}

	@Test
	public void update() {
		Bagis bagis = BagisPreparer.olustur();
		bagisRepository.saveAndFlush(bagis);

		Bagis savedBagis = bagisRepository.findById(bagis.getId()).get();
		Assert.assertTrue(savedBagis.equals(bagis));

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedBagis.setName(toUpdate);
		bagisRepository.saveAndFlush(savedBagis);

		Bagis updated = bagisRepository.findById(bagis.getId()).get();
		Assert.assertEquals(updated.getName(), toUpdate);
	}

	@Test
	public void delete() {
		Bagis bagis = BagisPreparer.olustur();
		bagisRepository.saveAndFlush(bagis);

		Bagis savedBagis = bagisRepository.findById(bagis.getId()).get();
		Assert.assertTrue(savedBagis.equals(bagis));

		bagisRepository.delete(savedBagis);

		Optional<Bagis> deleted = bagisRepository.findById(bagis.getId());
		Assert.assertFalse(deleted.isPresent());
	}

	@Test
	public void findById() {
		Bagis bagis = BagisPreparer.olustur();
		bagisRepository.saveAndFlush(bagis);

		Bagis savedBagis = bagisRepository.findById(bagis.getId()).get();
		Assert.assertTrue(savedBagis.equals(bagis));
	}


}
