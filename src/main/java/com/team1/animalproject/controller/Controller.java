package com.team1.animalproject.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class Controller {

    @RequestMapping("/")
    public void redirectToMainPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getUserPrincipal() == null){
            response.sendRedirect("/landing.jsf");
        }else {
            response.sendRedirect("/index.jsf");
        }
    }
}
