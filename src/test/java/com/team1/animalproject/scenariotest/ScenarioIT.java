package com.team1.animalproject.scenariotest;

import com.google.common.collect.Lists;
import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.model.Rol;
import com.team1.animalproject.model.RolYetki;
import com.team1.animalproject.preparer.RolPreparer;
import com.team1.animalproject.preparer.RolYetkiPreparer;
import com.team1.animalproject.preparer.UserPreparer;
import com.team1.animalproject.repository.KullaniciRolRepository;
import com.team1.animalproject.repository.RolRepository;
import com.team1.animalproject.repository.RolYetkiRepository;
import com.team1.animalproject.repository.UserRepository;
import com.team1.animalproject.service.RolService;
import com.team1.animalproject.service.UserService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith (Cucumber.class)
public class ScenarioIT {

	@InjectMocks
	private UserService userService;

	@InjectMocks
	private RolService rolService;

	@Mock(name = "userRepository")
	private UserRepository userRepository;

	@Mock(name = "rolRepository")
	private RolRepository rolRepository;

	@Mock(name = "rolYetkiRepository")
	private RolYetkiRepository rolYetkiRepository;

	@Mock(name = "rolYetkiRepository")
	private KullaniciRolRepository kullaniciRolRepository;

	private Kullanici user;

	@Before
	@Test
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}

	@Given ("Filling the user entity")
	@Test
	public void userDoldur() {
		user = UserPreparer.olustur();
	}

	@When ("Saving the variable")
	@Test
	public void userKaydet() {
		userService = mock(UserService.class);
		userRepository = mock(UserRepository.class);
		when(userRepository.save((Kullanici) Mockito.any())).thenReturn(user);
		userService.save(user);
	}

	@Then ("Must be registered successfully")
	@Test
	public void kayitIslemiBasariliMi() {
		userService = mock(UserService.class);
		user = UserPreparer.olustur();
		BDDMockito.given(userService.findById(Mockito.anyString())).willReturn(Optional.ofNullable(user));
		Optional<Kullanici> optional = userService.findById(user.getId());
		Assert.assertTrue(optional.isPresent());
	}

	@Then ("Login with username and password")
	@Test
	public void girisYap() {
		userService = mock(UserService.class);
		user = UserPreparer.olustur();
		BDDMockito.given(userService.findByUserNameAndPassword(Mockito.anyString(), Mockito.anyString())).willReturn(Optional.ofNullable(user));
		Optional<Kullanici> optional = userService.findByUserNameAndPassword(user.getId(), user.getPassword());
		Assert.assertTrue(optional.isPresent());
	}
}
