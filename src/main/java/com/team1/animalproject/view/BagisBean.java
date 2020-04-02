package com.team1.animalproject.view;

import com.team1.animalproject.model.Bagis;
import com.team1.animalproject.service.BagisService;
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
public class BagisBean extends BaseViewController<Bagis> implements Serializable {

	private static final long serialVersionUID = -7675607119976535506L;

	private static final Logger logger = LoggerFactory.getLogger(BagisBean.class);

	@Autowired
	private BagisService bagisService;

	private Bagis bagis = new Bagis();
	private List<Bagis> selectedBagiss;
	private List<Bagis> allBagiss;
	private List<Bagis> filteredBagiss;

	private boolean showCreateOrEdit;
	private boolean showInfo;

	@Override
	@PostConstruct
	public void viewOlustur() {
		super.altVerileriVeIlkEkraniHazirla();
		allBagiss = bagisService.getAll();
		filteredBagiss = new ArrayList<>(allBagiss);
	}

	@Override
	public void ilkEkraniHazirla() {
		showCreateOrEdit = false;
		showInfo = false;
		allBagiss = bagisService.getAll();
		filteredBagiss = new ArrayList<>(allBagiss);
		bagis = new Bagis();
	}

	public void kaydet() throws IOException {
		bagisService.save(bagis);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı",  "Bağış verisi başarıyla işlem görmüştür.") );
		context.getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/bagis/bagis.jsf");

	}

	public void prepareNewScreen(){
		showCreateOrEdit = true;
	}

	public void prepareUpdateScreen(){
		bagis = selectedBagiss.stream().findFirst().get();
		showCreateOrEdit = true;
	}

	public void prepareInfoScreen(){
		bagis = selectedBagiss.stream().findFirst().get();
		showCreateOrEdit = true;
		showInfo = true;
	}

	public void sil() throws IOException {
		bagisService.delete(selectedBagiss);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/bagis/bagis.jsf");
	}


}
