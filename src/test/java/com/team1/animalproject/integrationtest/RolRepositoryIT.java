package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.Rol;
import com.team1.animalproject.preparer.RolPreparer;
import com.team1.animalproject.repository.RolRepository;
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
public class RolRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private RolRepository rolRepository;

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			rolRepository.save(RolPreparer.olustur());
		}

		List<Rol> all = rolRepository.findAll();
		Assert.assertTrue(all.size() == 5);
		rolRepository.deleteAll();
	}

	@Test
	public void save() {
		Rol rol = RolPreparer.olustur();
		rolRepository.saveAndFlush(rol);

		Rol savedRol = rolRepository.findById(rol.getId()).get();
		Assert.assertTrue(savedRol.equals(rol));
		rolRepository.deleteAll();
	}

	@Test
	public void update() {
		Rol rol = RolPreparer.olustur();
		rolRepository.saveAndFlush(rol);

		Rol savedRol = rolRepository.findById(rol.getId()).get();
		Assert.assertTrue(savedRol.equals(rol));

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedRol.setName(toUpdate);
		rolRepository.saveAndFlush(savedRol);

		Rol updated = rolRepository.findById(rol.getId()).get();
		Assert.assertEquals(updated.getName(), toUpdate);
		rolRepository.deleteAll();
	}

	@Test
	public void delete() {
		Rol rol = RolPreparer.olustur();
		rolRepository.saveAndFlush(rol);

		Rol savedRol = rolRepository.findById(rol.getId()).get();
		Assert.assertTrue(savedRol.equals(rol));

		rolRepository.delete(savedRol);

		Optional<Rol> deleted = rolRepository.findById(rol.getId());
		Assert.assertFalse(deleted.isPresent());
		rolRepository.deleteAll();
	}

	@Test
	public void findById() {
		Rol rol = RolPreparer.olustur();
		rolRepository.saveAndFlush(rol);

		Rol savedRol = rolRepository.findById(rol.getId()).get();
		Assert.assertTrue(savedRol.equals(rol));
		rolRepository.deleteAll();
	}

}
