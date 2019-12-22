package com.team1.animalproject.view;

import com.team1.animalproject.model.Animal;
import com.team1.animalproject.model.Tur;
import com.team1.animalproject.service.AnimalService;
import com.team1.animalproject.service.TurService;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
public class TurBean extends BaseViewController<Tur> implements Serializable {

	private static final long serialVersionUID = 78920392726425941L;

	private static final Logger logger = LoggerFactory.getLogger(TurBean.class);

	@Autowired
	private TurService turService;

	private Tur tur = new Tur();
	private List<Tur> selectedTurs;
	private List<Tur> allTurs;
	private List<Tur> filteredTurs;

	private boolean showCreateOrEdit;
	private boolean showInfo;

	@Override
	@PostConstruct
	public void viewOlustur() {
		super.altVerileriVeIlkEkraniHazirla();
		allTurs = turService.getAll();
		filteredTurs = new ArrayList<>(allTurs);
	}

	@Override
	public void ilkEkraniHazirla() {
		showCreateOrEdit = false;
		showInfo = false;
		allTurs = turService.getAll();
		filteredTurs = new ArrayList<>(allTurs);
		tur = new Tur();
	}

	public void kaydet() throws IOException {
		turService.save(tur);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı",  "Tür ekleme işlemi başarıyla tamamlandı.") );
		context.getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/tur/tur.jsf");

	}

	public void prepareNewScreen(){
		showCreateOrEdit = true;
	}

	public void prepareUpdateScreen(){
		tur = selectedTurs.stream().findFirst().get();
		showCreateOrEdit = true;
	}

	public void prepareInfoScreen(){
		tur = selectedTurs.stream().findFirst().get();
		showCreateOrEdit = true;
		showInfo = true;
	}

	public void sil() throws IOException {
		turService.delete(selectedTurs);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/tur/tur.jsf");
	}


}
