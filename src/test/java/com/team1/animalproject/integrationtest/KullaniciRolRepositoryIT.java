package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.KullaniciRol;
import com.team1.animalproject.preparer.KullaniciRolPreparer;
import com.team1.animalproject.repository.KullaniciRolRepository;
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
public class KullaniciRolRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private KullaniciRolRepository kullaniciRolRepository;

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			kullaniciRolRepository.save(KullaniciRolPreparer.olustur());
		}

		List<KullaniciRol> all = kullaniciRolRepository.findAll();
		Assert.assertTrue(all.size() == 5);
		kullaniciRolRepository.deleteAll();
	}

	@Test
	public void save() {
		KullaniciRol kullaniciRol = KullaniciRolPreparer.olustur();
		kullaniciRolRepository.saveAndFlush(kullaniciRol);

		KullaniciRol savedKullaniciRol = kullaniciRolRepository.findById(kullaniciRol.getId()).get();
		Assert.assertTrue(savedKullaniciRol.equals(kullaniciRol));
		kullaniciRolRepository.deleteAll();
	}

	@Test
	public void update() {
		KullaniciRol kullaniciRol = KullaniciRolPreparer.olustur();
		kullaniciRolRepository.saveAndFlush(kullaniciRol);

		KullaniciRol savedKullaniciRol = kullaniciRolRepository.findById(kullaniciRol.getId()).get();
		Assert.assertTrue(savedKullaniciRol.equals(kullaniciRol));

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedKullaniciRol.setKullaniciId(toUpdate);
		kullaniciRolRepository.saveAndFlush(savedKullaniciRol);

		KullaniciRol updated = kullaniciRolRepository.findById(kullaniciRol.getId()).get();
		Assert.assertEquals(updated.getKullaniciId(), toUpdate);
		kullaniciRolRepository.deleteAll();
	}

	@Test
	public void delete() {
		KullaniciRol kullaniciRol = KullaniciRolPreparer.olustur();
		kullaniciRolRepository.saveAndFlush(kullaniciRol);

		KullaniciRol savedKullaniciRol = kullaniciRolRepository.findById(kullaniciRol.getId()).get();
		Assert.assertTrue(savedKullaniciRol.equals(kullaniciRol));

		kullaniciRolRepository.delete(savedKullaniciRol);

		Optional<KullaniciRol> deleted = kullaniciRolRepository.findById(kullaniciRol.getId());
		Assert.assertFalse(deleted.isPresent());
		kullaniciRolRepository.deleteAll();
	}

	@Test
	public void findById() {
		KullaniciRol kullaniciRol = KullaniciRolPreparer.olustur();
		kullaniciRolRepository.saveAndFlush(kullaniciRol);

		KullaniciRol savedKullaniciRol = kullaniciRolRepository.findById(kullaniciRol.getId()).get();
		Assert.assertTrue(savedKullaniciRol.equals(kullaniciRol));
		kullaniciRolRepository.deleteAll();
	}

}
