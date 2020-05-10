package com.team1.animalproject.integrationtest;

import com.team1.animalproject.model.IpfsID;
import com.team1.animalproject.preparer.IpfsIDPreparer;
import com.team1.animalproject.repository.IpfsIDRepository;
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
public class IpfsIDRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired
	private IpfsIDRepository ipfsIdRepository;

	@Test
	public void getAll() {
		for(int i = 0; i < 5; i++){
			ipfsIdRepository.save(IpfsIDPreparer.olustur());
		}

		List<IpfsID> all = ipfsIdRepository.findAll();
		Assert.assertEquals(all.size(), 5);
		ipfsIdRepository.deleteAll();
	}

	@Test
	public void save() {
		IpfsID ipfsId = IpfsIDPreparer.olustur();
		ipfsIdRepository.saveAndFlush(ipfsId);

		IpfsID savedIpfsID;
		savedIpfsID = ipfsIdRepository.findById(ipfsId.getId()).get();
		Assert.assertEquals(ipfsId, savedIpfsID);
		ipfsIdRepository.deleteAll();
	}

	@Test
	public void update() {
		IpfsID ipfsId = IpfsIDPreparer.olustur();
		ipfsIdRepository.saveAndFlush(ipfsId);

		IpfsID savedIpfsID = ipfsIdRepository.findById(ipfsId.getId()).get();
		Assert.assertEquals(ipfsId, savedIpfsID);

		String toUpdate = RandomStringUtils.randomNumeric(10);
		savedIpfsID.setIpfsHash(toUpdate);
		ipfsIdRepository.saveAndFlush(savedIpfsID);

		IpfsID updated = ipfsIdRepository.findById(ipfsId.getId()).get();
		Assert.assertEquals(updated.getIpfsHash(), toUpdate);
		ipfsIdRepository.deleteAll();
	}

	@Test
	public void delete() {
		IpfsID ipfsId = IpfsIDPreparer.olustur();
		ipfsIdRepository.saveAndFlush(ipfsId);

		IpfsID savedIpfsID = ipfsIdRepository.findById(ipfsId.getId()).get();
		Assert.assertEquals(ipfsId, savedIpfsID);

		ipfsIdRepository.delete(savedIpfsID);

		Optional<IpfsID> deleted = ipfsIdRepository.findById(ipfsId.getId());
		Assert.assertFalse(deleted.isPresent());
		ipfsIdRepository.deleteAll();
	}

	@Test
	public void findById() {
		IpfsID ipfsId = IpfsIDPreparer.olustur();
		ipfsIdRepository.saveAndFlush(ipfsId);

		IpfsID savedIpfsID = ipfsIdRepository.findById(ipfsId.getId()).get();
		Assert.assertEquals(ipfsId, savedIpfsID);
		ipfsIdRepository.deleteAll();
	}

}
