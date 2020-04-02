package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.Ilac;
import com.team1.animalproject.preparer.IlacPreparer;
import com.team1.animalproject.repository.IlacRepository;
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
public class IlacRepositoryIT {

	@Autowired
	private IlacRepository ilacRepository;

	@Test
	public void ara() {
		for(int i = 0; i < 5; i++){
			ilacRepository.save(IlacPreparer.olustur());
		}

		List<Ilac> all = ilacRepository.findAll();
		Assert.assertTrue(all.size() == 5);
	}

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			ilacRepository.save(IlacPreparer.olustur());
		}

		List<Ilac> all = ilacRepository.findAllByDurum(true);
		Assert.assertTrue(all.size() == 5);
	}

	@Test
	public void save() {
		Ilac ilac = IlacPreparer.olustur();
		ilacRepository.saveAndFlush(ilac);

		Ilac savedIlac = ilacRepository.findById(ilac.getId()).get();
		Assert.assertTrue(savedIlac.equals(ilac));
	}

	@Test
	public void update() {
		Ilac ilac = IlacPreparer.olustur();
		ilacRepository.saveAndFlush(ilac);

		Ilac savedIlac = ilacRepository.findById(ilac.getId()).get();
		Assert.assertTrue(savedIlac.equals(ilac));

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedIlac.setName(toUpdate);
		ilacRepository.saveAndFlush(savedIlac);

		Ilac updated = ilacRepository.findById(ilac.getId()).get();
		Assert.assertEquals(updated.getName(), toUpdate);
	}

	@Test
	public void delete() {
		Ilac ilac = IlacPreparer.olustur();
		ilacRepository.saveAndFlush(ilac);

		Ilac savedIlac = ilacRepository.findById(ilac.getId()).get();
		Assert.assertTrue(savedIlac.equals(ilac));

		ilacRepository.delete(savedIlac);

		Optional<Ilac> deleted = ilacRepository.findById(ilac.getId());
		Assert.assertFalse(deleted.isPresent());
	}

	@Test
	public void findById() {
		Ilac ilac = IlacPreparer.olustur();
		ilacRepository.saveAndFlush(ilac);

		Ilac savedIlac = ilacRepository.findById(ilac.getId()).get();
		Assert.assertTrue(savedIlac.equals(ilac));
	}


}
