package com.team1.animalproject.controller;

import com.team1.animalproject.model.Animal;
import com.team1.animalproject.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @PostMapping(value = "/animal/save", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Animal> save(@RequestBody Animal animal) {
        animalService.save(animal);
        return ResponseEntity.ok().body(animal);
    }

    @GetMapping(value = "/animal/getall")
    public ResponseEntity<List<Animal>> getAll() {
        return ResponseEntity.ok().body(animalService.getAll());
    }

}
