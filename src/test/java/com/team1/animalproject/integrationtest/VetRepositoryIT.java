package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.Vet;
import com.team1.animalproject.preparer.VetPreparer;
import com.team1.animalproject.repository.VetRepository;
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
public class VetRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private VetRepository vetRepository;

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			vetRepository.save(VetPreparer.olustur());
		}

		List<Vet> all = vetRepository.findAll();
		Assert.assertEquals(all.size(), 5);
		vetRepository.deleteAll();
	}

	@Test
	public void save() {
		Vet vet = VetPreparer.olustur();
		vetRepository.saveAndFlush(vet);

		Vet savedVet = vetRepository.findById(vet.getId()).get();
		Assert.assertEquals(vet, savedVet);
		vetRepository.deleteAll();
	}

	@Test
	public void update() {
		Vet vet = VetPreparer.olustur();
		vetRepository.saveAndFlush(vet);

		Vet savedVet = vetRepository.findById(vet.getId()).get();
		Assert.assertEquals(vet, savedVet);

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedVet.setName(toUpdate);
		vetRepository.saveAndFlush(savedVet);

		Vet updated = vetRepository.findById(vet.getId()).get();
		Assert.assertEquals(updated.getName(), toUpdate);
		vetRepository.deleteAll();
	}

	@Test
	public void delete() {
		Vet vet = VetPreparer.olustur();
		vetRepository.saveAndFlush(vet);

		Vet savedVet = vetRepository.findById(vet.getId()).get();
		Assert.assertEquals(vet, savedVet);

		vetRepository.delete(savedVet);

		Optional<Vet> deleted = vetRepository.findById(vet.getId());
		Assert.assertFalse(deleted.isPresent());
		vetRepository.deleteAll();
	}

	@Test
	public void findById() {
		Vet vet = VetPreparer.olustur();
		vetRepository.saveAndFlush(vet);

		Vet savedVet = vetRepository.findById(vet.getId()).get();
		Assert.assertEquals(vet, savedVet);
		vetRepository.deleteAll();
	}

}
