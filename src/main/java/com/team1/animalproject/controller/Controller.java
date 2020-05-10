package com.team1.animalproject.controller;

import com.team1.animalproject.auth.Constants;
import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
public class Controller {
    @Autowired
    private UserService userService;

    @Autowired
    ResourceLoader resourceLoader;

    @RequestMapping("/")
    public void redirectToMainPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getUserPrincipal() == null){
            response.sendRedirect("/landing.jsf");
        }else {
            response.sendRedirect("/index.jsf");
        }
    }

    @RequestMapping("/sifirla/{kullaniciId}/{sifre}")
    public void redirectToMainPage(HttpServletRequest request, HttpServletResponse response, @PathVariable("kullaniciId") String kullaniciId, @PathVariable("sifre") String sifre) throws IOException, NoSuchAlgorithmException {
        Optional<Kullanici> byId = userService.findById(kullaniciId);
        if(byId.isPresent()){
            byId.get().setPassword(UserService.md5Java(sifre));
            userService.save(byId.get());
        }
        response.sendRedirect("/index.jsf");
    }

    @RequestMapping("/image/{kullaniciId}")
    public ResponseEntity<byte[]> avatarImage(HttpServletRequest request, HttpServletResponse response, @PathVariable("kullaniciId") String kullaniciId) throws IOException {
        if(kullaniciId != null && kullaniciId.length() != 0 && !kullaniciId.equalsIgnoreCase("anonim")){
            File file = new File(Constants.AVATAR_PATH + kullaniciId + ".jpg");
            response.setContentType("image/jpg");
            if(file.exists()){
                byte[] array = Files.readAllBytes(Paths.get(Constants.AVATAR_PATH + kullaniciId + ".jpg"));
                return ResponseEntity.ok(array);
            }
        }
        return ResponseEntity.ok(Files.readAllBytes(Paths.get(Constants.AVATAR_PATH  + "avatar-male.jpg")));
    }

    @RequestMapping("/{modul}/image/{kullaniciId}")
    public ResponseEntity<byte[]> avatarImageIn(HttpServletRequest request, HttpServletResponse response, @PathVariable("kullaniciId") String kullaniciId,  @PathVariable("modul") String modul) throws IOException {
        if(kullaniciId != null && kullaniciId.length() != 0 && !kullaniciId.equalsIgnoreCase("anonim")){
            File file = new File(Constants.AVATAR_PATH + kullaniciId + ".jpg");
            response.setContentType("image/jpg");
            if(file.exists()){
                byte[] array = Files.readAllBytes(Paths.get(Constants.AVATAR_PATH + kullaniciId + ".jpg"));
                return ResponseEntity.ok(array);
            }
        }
        return ResponseEntity.ok(Files.readAllBytes(Paths.get(Constants.AVATAR_PATH  + "avatar-male.jpg")));
    }
}
