package com.team1.animalproject.controller;

import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
public class Controller {
    @Autowired
    private UserService userService;

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
}
