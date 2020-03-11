package com.team1.animalproject.controller;

import com.team1.animalproject.model.Zoo;
import com.team1.animalproject.service.ZooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin (origins = "*")
@RequestMapping ("api/v1/zoo")
public class ZooController {

	@Autowired
	private ZooService service;

	@RequestMapping (value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Zoo> save(@RequestBody Zoo veri) {
		service.save(veri);
		return ResponseEntity.ok().body(veri);
	}

	@GetMapping (value = "/getall")
	public ResponseEntity<List<Zoo>> getAll() {
		return ResponseEntity.ok().body(service.getAll());
	}

	@RequestMapping (value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Zoo>> delete(@RequestBody List<Zoo> veriler) {
		service.delete(veriler);
		return ResponseEntity.ok().body(veriler);
	}
}
