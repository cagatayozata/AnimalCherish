package com.team1.animalproject.controller;

import com.team1.animalproject.model.Animal;
import com.team1.animalproject.service.AnimalService;
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
@RequestMapping ("api/v1/animal")
public class AnimalController {

	@Autowired
	private AnimalService animalService;

	@RequestMapping (value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Animal> save(@RequestBody Animal animal) {
		animalService.save(animal);
		return ResponseEntity.ok().body(animal);
	}

	@RequestMapping (value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Animal>> delete(@RequestBody List<Animal> veriler) {
		animalService.delete(veriler);
		return ResponseEntity.ok().body(veriler);
	}

	@GetMapping (value = "/getall")
	public ResponseEntity<List<Animal>> getAll() {
		return ResponseEntity.ok().body(animalService.getAll());
	}

}
