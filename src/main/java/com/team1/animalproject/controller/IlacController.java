package com.team1.animalproject.controller;

import com.team1.animalproject.model.Ilac;
import com.team1.animalproject.service.IlacService;
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
@RequestMapping ("api/v1/ilac")
public class IlacController {

    @Autowired
    private IlacService service;

    @RequestMapping (value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ilac> save(@RequestBody Ilac veri) {
        service.save(veri);
        return ResponseEntity.ok().body(veri);
    }

    @GetMapping (value = "/getall")
    public ResponseEntity<List<Ilac>> getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @RequestMapping (value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Ilac>> delete(@RequestBody List<Ilac> veriler) {
        service.delete(veriler);
        return ResponseEntity.ok().body(veriler);
    }
}
