package com.team1.animalproject.view;

import com.team1.animalproject.model.Rol;
import com.team1.animalproject.model.RolYetki;
import com.team1.animalproject.model.Yetki;
import com.team1.animalproject.service.RolService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("view")
@EqualsAndHashCode(callSuper = false)
@Data
public class RolBean extends BaseViewController<Rol> implements Serializable {

	private static final long serialVersionUID = -3431201652451639852L;

	private static final Logger logger = LoggerFactory.getLogger(RolBean.class);

	@Autowired
	private RolService rolService;

	private Rol rol = new Rol();
	private List<Rol> selectedRols;
	private List<Rol> allRols;
	private List<Rol> filteredRols;
	private DualListModel<Yetki> yetkis;

	private boolean showCreateOrEdit;
	private boolean showInfo;

	@Override
	@PostConstruct
	public void viewOlustur() {
		super.altVerileriVeIlkEkraniHazirla();
		allRols = rolService.getAll();
		filteredRols = new ArrayList<>(allRols);
	}

	@Override
	public void ilkEkraniHazirla() {
		showCreateOrEdit = false;
		showInfo = false;
		rol = new Rol();
	}

	public void kaydet() throws IOException {
		rolService.save(rol);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı",  "Rol başarıyla eklendi.") );
		context.getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/animal/animal.jsf");

	}

	public void prepareNewScreen(){
		showCreateOrEdit = true;
	}

	public void prepareUpdateScreen(){
		rol = selectedRols.stream().findFirst().get();
		showCreateOrEdit = true;
	}

	public void prepareInfoScreen(){
		rol = selectedRols.stream().findFirst().get();
		showCreateOrEdit = true;
		showInfo = true;
	}
	
	public void prepareYetkis(){
		List<RolYetki> ekliOlmayanlar = new ArrayList<>();
		List<RolYetki> byRolIdNotIn = rolService.findByRolIdNotIn(rol.getId());
		List<RolYetki> ekliler = new ArrayList<>();
		List<RolYetki> byRolIdIn = rolService.findByRolIdIn(rol.getId());

		if(byRolIdNotIn != null){
			ekliOlmayanlar = byRolIdNotIn;
		}

		if(byRolIdIn != null){
			ekliler = byRolIdIn;
		}
	}

	public void sil() throws IOException {
		rolService.delete(selectedRols);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/animal/animal.jsf");
	}
}
