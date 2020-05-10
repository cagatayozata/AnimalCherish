package com.team1.animalproject.view;

import com.team1.animalproject.model.dto.KullaniciPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class BaseViewController<T> implements Serializable {

	private static final long serialVersionUID = -9205236771505559696L;

	protected KullaniciPrincipal kullaniciPrincipal;

	public abstract void viewOlustur() throws IOException;

	public abstract void ilkEkraniHazirla();

	public final void altVerileriVeIlkEkraniHazirla() {

		setAuthenticationPrincipal();
		log.info(this.getClass().getSimpleName() + " is initialized for user " + kullaniciPrincipal.getId());

		ilkEkraniHazirla();

		@SuppressWarnings ("unchecked") List<FacesMessage> facesMessages = (List<FacesMessage>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("facesMessage");

		Optional.ofNullable(facesMessages).orElse(Collections.emptyList()).forEach(facesMessage -> FacesContext.getCurrentInstance().addMessage(null, facesMessage));

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("facesMessage", null);

	}

	/**
	 * Principal nesnesini hazirla
	 */
	private void setAuthenticationPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if("anonymousUser".equalsIgnoreCase(authentication.getPrincipal().toString())){
			kullaniciPrincipal = KullaniciPrincipal.builder().build();
		}else {
			kullaniciPrincipal = (KullaniciPrincipal) authentication.getPrincipal();
		}
	}


}
