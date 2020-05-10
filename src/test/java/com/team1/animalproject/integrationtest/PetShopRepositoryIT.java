package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.PetShop;
import com.team1.animalproject.preparer.PetShopPreparer;
import com.team1.animalproject.repository.PetShopRepository;
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
public class PetShopRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private PetShopRepository petShopRepository;

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			petShopRepository.save(PetShopPreparer.olustur());
		}

		List<PetShop> all = petShopRepository.findAll();
		Assert.assertEquals(all.size(), 5);
		petShopRepository.deleteAll();
	}

	@Test
	public void save() {
		PetShop petShop = PetShopPreparer.olustur();
		petShopRepository.saveAndFlush(petShop);

		PetShop savedPetShop = petShopRepository.findById(petShop.getId()).get();
		Assert.assertEquals(petShop, savedPetShop);
		petShopRepository.deleteAll();
	}

	@Test
	public void update() {
		PetShop petShop = PetShopPreparer.olustur();
		petShopRepository.saveAndFlush(petShop);

		PetShop savedPetShop = petShopRepository.findById(petShop.getId()).get();
		Assert.assertEquals(petShop, savedPetShop);

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedPetShop.setName(toUpdate);
		petShopRepository.saveAndFlush(savedPetShop);

		PetShop updated = petShopRepository.findById(petShop.getId()).get();
		Assert.assertEquals(updated.getName(), toUpdate);
		petShopRepository.deleteAll();
	}

	@Test
	public void delete() {
		PetShop petShop = PetShopPreparer.olustur();
		petShopRepository.saveAndFlush(petShop);

		PetShop savedPetShop = petShopRepository.findById(petShop.getId()).get();
		Assert.assertEquals(petShop, savedPetShop);

		petShopRepository.delete(savedPetShop);

		Optional<PetShop> deleted = petShopRepository.findById(petShop.getId());
		Assert.assertFalse(deleted.isPresent());
		petShopRepository.deleteAll();
	}

	@Test
	public void findById() {
		PetShop petShop = PetShopPreparer.olustur();
		petShopRepository.saveAndFlush(petShop);

		PetShop savedPetShop = petShopRepository.findById(petShop.getId()).get();
		Assert.assertEquals(petShop, savedPetShop);
		petShopRepository.deleteAll();
	}

}
