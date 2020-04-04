package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.GercekKisi;
import com.team1.animalproject.preparer.GercekKisiPreparer;
import com.team1.animalproject.repository.GercekKisiRepository;
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
public class GercekKisiRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private GercekKisiRepository gercekKisiRepository;

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			gercekKisiRepository.save(GercekKisiPreparer.olustur());
		}

		List<GercekKisi> all = gercekKisiRepository.findAll();
		Assert.assertTrue(all.size() == 5);
		gercekKisiRepository.deleteAll();
	}

	@Test
	public void save() {
		GercekKisi gercekKisi = GercekKisiPreparer.olustur();
		gercekKisiRepository.saveAndFlush(gercekKisi);

		GercekKisi savedGercekKisi = gercekKisiRepository.findById(gercekKisi.getId()).get();
		Assert.assertTrue(savedGercekKisi.equals(gercekKisi));
		gercekKisiRepository.deleteAll();
	}

	@Test
	public void update() {
		GercekKisi gercekKisi = GercekKisiPreparer.olustur();
		gercekKisiRepository.saveAndFlush(gercekKisi);

		GercekKisi savedGercekKisi = gercekKisiRepository.findById(gercekKisi.getId()).get();
		Assert.assertTrue(savedGercekKisi.equals(gercekKisi));

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedGercekKisi.setAd(toUpdate);
		gercekKisiRepository.saveAndFlush(savedGercekKisi);

		GercekKisi updated = gercekKisiRepository.findById(gercekKisi.getId()).get();
		Assert.assertEquals(updated.getAd(), toUpdate);
		gercekKisiRepository.deleteAll();
	}

	@Test
	public void delete() {
		GercekKisi gercekKisi = GercekKisiPreparer.olustur();
		gercekKisiRepository.saveAndFlush(gercekKisi);

		GercekKisi savedGercekKisi = gercekKisiRepository.findById(gercekKisi.getId()).get();
		Assert.assertTrue(savedGercekKisi.equals(gercekKisi));

		gercekKisiRepository.delete(savedGercekKisi);

		Optional<GercekKisi> deleted = gercekKisiRepository.findById(gercekKisi.getId());
		Assert.assertFalse(deleted.isPresent());
		gercekKisiRepository.deleteAll();
	}

	@Test
	public void findById() {
		GercekKisi gercekKisi = GercekKisiPreparer.olustur();
		gercekKisiRepository.saveAndFlush(gercekKisi);

		GercekKisi savedGercekKisi = gercekKisiRepository.findById(gercekKisi.getId()).get();
		Assert.assertTrue(savedGercekKisi.equals(gercekKisi));
		gercekKisiRepository.deleteAll();
	}

	@Test
	public void findByKimlikNo() {
		GercekKisi gercekKisi = GercekKisiPreparer.olustur();
		gercekKisiRepository.saveAndFlush(gercekKisi);

		Optional<GercekKisi> savedGercekKisi = gercekKisiRepository.findByKimlikNo(gercekKisi.getKimlikNo());
		Assert.assertTrue(savedGercekKisi.isPresent());
		gercekKisiRepository.deleteAll();
	}


}
