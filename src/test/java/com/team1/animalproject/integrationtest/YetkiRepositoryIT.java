package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.Yetki;
import com.team1.animalproject.preparer.YetkiPreparer;
import com.team1.animalproject.repository.YetkiRepository;
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
public class YetkiRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private YetkiRepository yetkiRepository;

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			yetkiRepository.save(YetkiPreparer.olustur());
		}

		List<Yetki> all = yetkiRepository.findAll();
		Assert.assertEquals(all.size(), 5);
		yetkiRepository.deleteAll();
	}

	@Test
	public void save() {
		Yetki yetki = YetkiPreparer.olustur();
		yetkiRepository.saveAndFlush(yetki);

		Yetki savedYetki = yetkiRepository.findById(yetki.getId()).get();
		Assert.assertEquals(yetki, savedYetki);
		yetkiRepository.deleteAll();
	}

	@Test
	public void update() {
		Yetki yetki = YetkiPreparer.olustur();
		yetkiRepository.saveAndFlush(yetki);

		Yetki savedYetki = yetkiRepository.findById(yetki.getId()).get();
		Assert.assertEquals(yetki, savedYetki);

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedYetki.setName(toUpdate);
		yetkiRepository.saveAndFlush(savedYetki);

		Yetki updated = yetkiRepository.findById(yetki.getId()).get();
		Assert.assertEquals(updated.getName(), toUpdate);
		yetkiRepository.deleteAll();
	}

	@Test
	public void delete() {
		Yetki yetki = YetkiPreparer.olustur();
		yetkiRepository.saveAndFlush(yetki);

		Yetki savedYetki = yetkiRepository.findById(yetki.getId()).get();
		Assert.assertEquals(yetki, savedYetki);

		yetkiRepository.delete(savedYetki);

		Optional<Yetki> deleted = yetkiRepository.findById(yetki.getId());
		Assert.assertFalse(deleted.isPresent());
		yetkiRepository.deleteAll();
	}

	@Test
	public void findById() {
		Yetki yetki = YetkiPreparer.olustur();
		yetkiRepository.saveAndFlush(yetki);

		Yetki savedYetki = yetkiRepository.findById(yetki.getId()).get();
		Assert.assertEquals(yetki, savedYetki);
		yetkiRepository.deleteAll();
	}

}
