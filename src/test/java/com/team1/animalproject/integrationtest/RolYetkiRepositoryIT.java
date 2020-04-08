package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.RolYetki;
import com.team1.animalproject.preparer.RolYetkiPreparer;
import com.team1.animalproject.repository.RolYetkiRepository;
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
public class RolYetkiRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private RolYetkiRepository rolYetkiRepository;

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			rolYetkiRepository.save(RolYetkiPreparer.olustur());
		}

		List<RolYetki> all = rolYetkiRepository.findAll();
		Assert.assertTrue(all.size() == 5);
		rolYetkiRepository.deleteAll();
	}

	@Test
	public void save() {
		RolYetki rolYetki = RolYetkiPreparer.olustur();
		rolYetkiRepository.saveAndFlush(rolYetki);

		RolYetki savedRolYetki = rolYetkiRepository.findById(rolYetki.getId()).get();
		Assert.assertTrue(savedRolYetki.equals(rolYetki));
		rolYetkiRepository.deleteAll();
	}

	@Test
	public void update() {
		RolYetki rolYetki = RolYetkiPreparer.olustur();
		rolYetkiRepository.saveAndFlush(rolYetki);

		RolYetki savedRolYetki = rolYetkiRepository.findById(rolYetki.getId()).get();
		Assert.assertTrue(savedRolYetki.equals(rolYetki));

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedRolYetki.setRolId(toUpdate);
		rolYetkiRepository.saveAndFlush(savedRolYetki);

		RolYetki updated = rolYetkiRepository.findById(rolYetki.getId()).get();
		Assert.assertEquals(updated.getRolId(), toUpdate);
		rolYetkiRepository.deleteAll();
	}

	@Test
	public void delete() {
		RolYetki rolYetki = RolYetkiPreparer.olustur();
		rolYetkiRepository.saveAndFlush(rolYetki);

		RolYetki savedRolYetki = rolYetkiRepository.findById(rolYetki.getId()).get();
		Assert.assertTrue(savedRolYetki.equals(rolYetki));

		rolYetkiRepository.delete(savedRolYetki);

		Optional<RolYetki> deleted = rolYetkiRepository.findById(rolYetki.getId());
		Assert.assertFalse(deleted.isPresent());
		rolYetkiRepository.deleteAll();
	}

	@Test
	public void findById() {
		RolYetki rolYetki = RolYetkiPreparer.olustur();
		rolYetkiRepository.saveAndFlush(rolYetki);

		RolYetki savedRolYetki = rolYetkiRepository.findById(rolYetki.getId()).get();
		Assert.assertTrue(savedRolYetki.equals(rolYetki));
		rolYetkiRepository.deleteAll();
	}

}
