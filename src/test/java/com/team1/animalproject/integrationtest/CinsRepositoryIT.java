package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.Cins;
import com.team1.animalproject.preparer.CinsPreparer;
import com.team1.animalproject.repository.CinsRepository;
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
public class CinsRepositoryIT {

	@Autowired
	private CinsRepository cinsRepository;

	@Test
	public void ara() {
		for(int i = 0; i < 5; i++){
			cinsRepository.save(CinsPreparer.olustur());
		}

		List<Cins> all = cinsRepository.cinsAra();
		Assert.assertTrue(all.size() == 5);
	}

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			cinsRepository.save(CinsPreparer.olustur());
		}

		List<Cins> all = cinsRepository.findAllByDurum(true);
		Assert.assertTrue(all.size() == 5);
	}

	@Test
	public void save() {
		Cins cins = CinsPreparer.olustur();
		cinsRepository.saveAndFlush(cins);

		Cins savedCins = cinsRepository.findById(cins.getId()).get();
		Assert.assertTrue(savedCins.equals(cins));
	}

	@Test
	public void update() {
		Cins cins = CinsPreparer.olustur();
		cinsRepository.saveAndFlush(cins);

		Cins savedCins = cinsRepository.findById(cins.getId()).get();
		Assert.assertTrue(savedCins.equals(cins));

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedCins.setName(toUpdate);
		cinsRepository.saveAndFlush(savedCins);

		Cins updated = cinsRepository.findById(cins.getId()).get();
		Assert.assertEquals(updated.getName(), toUpdate);
	}

	@Test
	public void delete() {
		Cins cins = CinsPreparer.olustur();
		cinsRepository.saveAndFlush(cins);

		Cins savedCins = cinsRepository.findById(cins.getId()).get();
		Assert.assertTrue(savedCins.equals(cins));

		cinsRepository.delete(savedCins);

		Optional<Cins> deleted = cinsRepository.findById(cins.getId());
		Assert.assertFalse(deleted.isPresent());
	}

	@Test
	public void findById() {
		Cins cins = CinsPreparer.olustur();
		cinsRepository.saveAndFlush(cins);

		Cins savedCins = cinsRepository.findById(cins.getId()).get();
		Assert.assertTrue(savedCins.equals(cins));
	}

	@Test
	public void deleteByTurId() {
		Cins cins = CinsPreparer.olustur();
		cinsRepository.saveAndFlush(cins);

		cinsRepository.deleteByTurId(cins.getTurId());

		Optional<Cins> deleted = cinsRepository.findById(cins.getId());
		Assert.assertFalse(deleted.isPresent());
	}

}
