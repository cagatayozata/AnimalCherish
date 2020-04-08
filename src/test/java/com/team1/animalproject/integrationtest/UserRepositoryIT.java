package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.preparer.UserPreparer;
import com.team1.animalproject.repository.UserRepository;
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
public class UserRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			userRepository.save(UserPreparer.olustur());
		}

		List<Kullanici> all = userRepository.findAll();
		Assert.assertTrue(all.size() == 5);
		userRepository.deleteAll();
	}

	@Test
	public void save() {
		Kullanici user = UserPreparer.olustur();
		userRepository.saveAndFlush(user);

		Kullanici savedUser = userRepository.findById(user.getId()).get();
		Assert.assertTrue(savedUser.equals(user));
		userRepository.deleteAll();
	}

	@Test
	public void update() {
		Kullanici user = UserPreparer.olustur();
		userRepository.saveAndFlush(user);

		Kullanici savedUser = userRepository.findById(user.getId()).get();
		Assert.assertTrue(savedUser.equals(user));

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedUser.setName(toUpdate);
		userRepository.saveAndFlush(savedUser);

		Kullanici updated = userRepository.findById(user.getId()).get();
		Assert.assertEquals(updated.getName(), toUpdate);
		userRepository.deleteAll();
	}

	@Test
	public void delete() {
		Kullanici user = UserPreparer.olustur();
		userRepository.saveAndFlush(user);

		Kullanici savedUser = userRepository.findById(user.getId()).get();
		Assert.assertTrue(savedUser.equals(user));

		userRepository.delete(savedUser);

		Optional<Kullanici> deleted = userRepository.findById(user.getId());
		Assert.assertFalse(deleted.isPresent());
		userRepository.deleteAll();
	}

	@Test
	public void findById() {
		Kullanici user = UserPreparer.olustur();
		userRepository.saveAndFlush(user);

		Kullanici savedUser = userRepository.findById(user.getId()).get();
		Assert.assertTrue(savedUser.equals(user));
		userRepository.deleteAll();
	}

}
