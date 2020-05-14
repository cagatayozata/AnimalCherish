package com.team1.animalproject.controller;

import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@CrossOrigin (origins = "*")
@RequestMapping ("api/v1/kullanici")
public class KullaniciController {

	@Autowired
	private UserService service;

	@RequestMapping (value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Kullanici> save(@RequestBody Kullanici veri) {
		service.save(veri);
		return ResponseEntity.ok().body(veri);
	}

	@RequestMapping (value = "/kayitol", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Kullanici> kayitOl(@RequestBody Kullanici veri) throws IOException, NoSuchAlgorithmException {
		service.kayitOl(veri, false, true);
		return ResponseEntity.ok().body(veri);
	}

	@GetMapping (value = "/getall")
	public ResponseEntity<List<Kullanici>> getAll() {
		return ResponseEntity.ok().body(service.getAll());
	}

	@RequestMapping (value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Kullanici>> delete(@RequestBody List<Kullanici> veriler) {
		service.delete(veriler);
		return ResponseEntity.ok().body(veriler);
	}
}
