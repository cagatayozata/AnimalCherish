package com.team1.animalproject.service;

import com.team1.animalproject.auth.Constants;
import com.team1.animalproject.exception.BaseExceptionType;
import com.team1.animalproject.exception.BusinessRuleException;
import com.team1.animalproject.exception.ViewException;
import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.model.KullaniciRol;
import com.team1.animalproject.repository.UserRepository;
import com.team1.animalproject.view.utils.KullaniciTipiEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserService implements IBaseService<Kullanici> {

	@Qualifier ("userRepository")
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<Kullanici> getAll() {
		return userRepository.findAll();
	}

	@Override
	public void save(Kullanici kullanici) {
		userRepository.save(kullanici);
	}

	@Transactional
	public boolean kayitOl(Kullanici kullanici, boolean girisYapili, boolean sifreDegis) throws IOException, NoSuchAlgorithmException {
		Optional<List<Kullanici>> byUserNameOrEmailOrPhoneNumber = userRepository.findByUserNameOrEmailOrPhoneNumber(kullanici.userName, kullanici.email, kullanici.phoneNumber);
		if(byUserNameOrEmailOrPhoneNumber.isPresent() && !girisYapili){
			return false;
		} else {
			if(sifreDegis){
				kullanici.setId(UUID.randomUUID().toString());
				String sifreHashed = md5Java(kullanici.getPassword());
				kullanici.setPassword(sifreHashed);
			}

			if(!girisYapili) kullanici.setKullaniciTipi(KullaniciTipiEnum.NORMAL_USER.getId());
			kullanici.setOlusturanKullanici("");
			userRepository.save(kullanici);
			if(kullanici.getFileUploadEvent() != null){
				File newFile = new File(Constants.AVATAR_PATH + kullanici.getId() + ".jpg");
				byte[] blobAsBytes = kullanici.getFileUploadEvent().getFile().getContents();
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(blobAsBytes));
				ImageIO.write(image, "JPG", newFile);
			}
			return true;
		}
	}

	public static String md5Java(String message) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String digest = null;

		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] hash = md.digest(message.getBytes("UTF-8"));

		//converting byte array to Hexadecimal String
		StringBuilder sb = new StringBuilder(2 * hash.length);
		for(byte b : hash){
			sb.append(String.format("%02x", b & 0xff));
		}

		digest = sb.toString();

		return digest;
	}

	@Override
	public void update(Kullanici kullanici) {
		userRepository.save(kullanici);
	}

	@Override
	public void delete(List<Kullanici> t) {

	}

	public Optional<Kullanici> findByUserNameAndPassword(String username, String sifre) {
		return userRepository.findByUserNameAndPassword(username, sifre);
	}

	public Optional<Kullanici> findByUserNameAndEmail(String username, String email) {
		return userRepository.findByUserNameAndEmail(username, email);
	}

	public Kullanici findByUserName(String username) {
		Optional<Kullanici> byUserName = userRepository.findByUserName(username);
		if(byUserName.isPresent()){
			Kullanici kullanici = byUserName.get();
			if(kullanici.getKullaniciTipi() == KullaniciTipiEnum.NORMAL_USER.getId()){
				return kullanici;
			}
		}
		return null;
	}

	public Optional<Kullanici> findById(String id) {
		return userRepository.findById(id);
	}

	public Optional<List<Kullanici>> findByIdIn(List<String> ids) {
		return userRepository.findByIdIn(ids);
	}

	public boolean kullaniciBaskaYerdeGorevliMi(String kullaniciId, KullaniciTipiEnum kullaniciTipiEnum) {
		return userRepository.kullaniciBaskaYerdeGorevliMi(kullaniciId, kullaniciTipiEnum);
	}

}
