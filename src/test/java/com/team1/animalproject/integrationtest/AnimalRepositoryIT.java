package com.team1.animalproject.integrationtest;

import com.team1.animalproject.blockchain.queries.CreateAccount;
import com.team1.animalproject.model.Animal;
import com.team1.animalproject.preparer.AnimalPreparer;
import com.team1.animalproject.repository.AnimalRepository;
import com.team1.animalproject.view.utils.DateUtil;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.stellar.sdk.KeyPair;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@EnableAutoConfiguration
@TestPropertySource (locations = "classpath:/application-test.properties")
@SpringBootTest
public class AnimalRepositoryIT  extends AbstractTestNGSpringContextTests {

	@Autowired
	private AnimalRepository animalRepository;

	@Test
	public void ara() {
		for(int i = 0; i < 10; i++){
			animalRepository.save(AnimalPreparer.olustur());
		}

		List<Animal> animals = animalRepository.animalAra();
		Assert.assertEquals(animals.size(), 10);
		animalRepository.deleteAll();
	}

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			animalRepository.save(AnimalPreparer.olustur());
		}

		List<Animal> animals = animalRepository.findAll();
		Assert.assertEquals(animals.size(), 5);
		animalRepository.deleteAll();
	}

	@Test
	public void save() {
		Animal animal = AnimalPreparer.olustur();
		animalRepository.saveAndFlush(animal);

		List<String> animalIds = Lists.newArrayList();
		animalIds.add(animal.getId());

		@SuppressWarnings ("OptionalGetWithoutIsPresent") Animal savedAnimal = animalRepository.findByIdIn(animalIds).get().get(0);
		Assert.assertEquals(animal, savedAnimal);
		animalRepository.deleteAll();
	}

	@Test
	public void update() {
		Animal animal = AnimalPreparer.olustur();
		animalRepository.saveAndFlush(animal);

		List<String> animalIds = Lists.newArrayList();
		animalIds.add(animal.getId());

		@SuppressWarnings ("OptionalGetWithoutIsPresent") Animal savedAnimal = animalRepository.findByIdIn(animalIds).get().get(0);

		savedAnimal.setName(RandomStringUtils.randomAlphabetic(30));
		animalRepository.save(savedAnimal);

		animalIds = Lists.newArrayList();
		animalIds.add(savedAnimal.getId());

		@SuppressWarnings ("OptionalGetWithoutIsPresent") Animal updatedAnimal = animalRepository.findByIdIn(animalIds).get().get(0);

		Assert.assertNotEquals(animal.getName(), updatedAnimal.getName());
		animalRepository.deleteAll();
	}

	@Test
	public void delete() {
		Animal animal = AnimalPreparer.olustur();
		animalRepository.saveAndFlush(animal);

		List<String> animalIds = Lists.newArrayList();
		animalIds.add(animal.getId());

		@SuppressWarnings ("OptionalGetWithoutIsPresent") Animal savedAnimal = animalRepository.findByIdIn(animalIds).get().get(0);

		animalRepository.delete(savedAnimal);

		animalIds = Lists.newArrayList();
		animalIds.add(animal.getId());

		Optional<List<Animal>> optionalAnimal = animalRepository.findByIdIn(animalIds);

		Assert.assertFalse(optionalAnimal.isPresent());
		animalRepository.deleteAll();
	}

	@Test
	public void findByIdIn() {
		for(int i = 0; i < 5; i++){
			animalRepository.save(AnimalPreparer.olustur());
		}

		List<Animal> animals = animalRepository.findAll();

		List<String> ids = animals.stream().map(Animal::getId).collect(Collectors.toList());

		@SuppressWarnings ("OptionalGetWithoutIsPresent") List<Animal> saveds = animalRepository.findByIdIn(ids).get();

		Assert.assertEquals(saveds.size(), 5);
		animalRepository.deleteAll();
	}

	@Test
	public void sonYediGunIcinEklenenHayvanVerileriniGetir() {
		for(int i = 0; i < 5; i++){
			Animal olusturulan = AnimalPreparer.olustur();
			olusturulan.setOlusmaTarihi(DateUtil.minusDays(DateUtil.nowAsDate(), i));
			animalRepository.save(olusturulan);
		}

		List<Animal> animals = animalRepository.findAll();
		Assert.assertEquals(animals.size(), 5);

		Map<Integer, Long> yediGun = animalRepository.sonYediGunIcinEklenenHayvanVerileriniGetir();

		Assert.assertEquals(yediGun.keySet().size(), 5);
		animalRepository.deleteAll();
	}

	@Test
	public void accountUret() throws IOException {
		KeyPair keyPair = KeyPair.random();
		CreateAccount createAccount = new CreateAccount();
		String testAccount = createAccount.createTestAccount(keyPair);

		System.out.println(testAccount);
	}

	@Test
	public void secretSeedUret() throws IOException {
		KeyPair keyPair = KeyPair.random();
		CreateAccount createAccount = new CreateAccount();
		String testAccount = createAccount.createTestAccount(keyPair);

		System.out.println(testAccount);
	}

}
