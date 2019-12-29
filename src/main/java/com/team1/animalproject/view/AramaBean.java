package com.team1.animalproject.view;

import com.team1.animalproject.model.Animal;
import com.team1.animalproject.model.dto.KullaniciPrincipal;
import com.team1.animalproject.service.AnimalService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Component
@Scope("view")
@EqualsAndHashCode(callSuper = false)
@Slf4j
@Data
public class AramaBean extends BaseViewController<Animal> implements Serializable {

	private static final long serialVersionUID = 2030071240265799771L;

	private static final Logger logger = LoggerFactory.getLogger(AramaBean.class);

	@Autowired
	private AnimalService animalService;

	private String arama;

	@Override
	@PostConstruct
	public void viewOlustur() {
		super.altVerileriVeIlkEkraniHazirla();
	}

	@Override
	public void ilkEkraniHazirla() {

	}

	public void ara() throws IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		((KullaniciPrincipal) authentication.getPrincipal()).setAramaKelimesi(arama);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/animal/animal.jsf");
	}
}
