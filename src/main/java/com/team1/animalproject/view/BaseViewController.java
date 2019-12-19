package com.team1.animalproject.view;

import lombok.extern.slf4j.Slf4j;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class BaseViewController<T> implements Serializable {

	private static final long serialVersionUID = -9205236771505559696L;

	public abstract void viewOlustur();

	public abstract void ilkEkraniHazirla();

	public final void altVerileriVeIlkEkraniHazirla() {

		ilkEkraniHazirla();

		List<FacesMessage> facesMessages = (List<FacesMessage>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("facesMessage");

		Optional.ofNullable(facesMessages).orElse(Collections.emptyList()).forEach(facesMessage -> {
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		});

	}

}
