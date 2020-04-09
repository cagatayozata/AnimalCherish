package com.team1.animalproject.view;

import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.model.Vet;
import com.team1.animalproject.model.VetRandevu;
import com.team1.animalproject.service.UserService;
import com.team1.animalproject.service.VetService;
import com.team1.animalproject.view.utils.JSONUtils;
import com.team1.animalproject.view.utils.KullaniciTipiEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

@Component
@Scope ("view")
@EqualsAndHashCode (callSuper = false)
@Data
public class VetBean extends BaseViewController<Vet> implements Serializable {

	private static final long serialVersionUID = -5486608545743134294L;

	private static final Logger logger = LoggerFactory.getLogger(VetBean.class);

	@Autowired
	private VetService vetService;
	@Autowired
	private UserService userService;

	private Vet vet = new Vet();
	private List<Vet> selectedVets;
	private List<Vet> allVets;
	private List<Vet> filteredVets;

	private VetRandevu vetRandevu = new VetRandevu();
	private List<VetRandevu> selectedVetRandevus;
	private List<VetRandevu> allVetRandevus;
	private List<VetRandevu> filteredVetRandevus;
	private Kullanici randevuAlanKisi;

	private Map<String, String> distincts;
	private Map<String, String> cities;
	private Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();

	private boolean showCreateOrEdit;
	private boolean showInfo;
	private boolean showKullaniciIliski;
	private boolean showRandevu;
	private boolean showRandevuNewScreen;
	private boolean showRandevuNewUpdateButton;
	private boolean showRandevuInfo;
	private String kullaniciAdi;

	@Override
	@PostConstruct
	public void viewOlustur() {
		super.altVerileriVeIlkEkraniHazirla();
		allVets = vetService.getAll();
		filteredVets = new ArrayList<>(allVets);
	}

	@Override
	public void ilkEkraniHazirla() {
		JSONUtils jsonUtils = new JSONUtils();
		try{
			jsonUtils.jsonParse();
		} catch (IOException e){
			e.printStackTrace();
		}
		cities = jsonUtils.getCities();
		data = jsonUtils.getData();
		showCreateOrEdit = false;
		showKullaniciIliski = false;
		cities = cities.entrySet().stream().sorted(comparingByValue()).collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
		showInfo = false;
		showRandevu = false;
		showRandevuInfo = false;
		showRandevuNewUpdateButton = false;
		vet = new Vet();
	}

	public void kaydet() throws IOException {
		vetService.save(vet);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı", "Veteriner hekim verisi başarıyla işlem görmüştür."));
		context.getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/vet/vet.jsf");

	}

	public void prepareNewScreen() {
		showCreateOrEdit = true;
	}

	public void prepareUpdateScreen() {
		vet = selectedVets.stream().findFirst().get();
		distincts = data.get(vet.getCity());
		showCreateOrEdit = true;
	}

	public void prepareInfoScreen() {
		vet = selectedVets.stream().findFirst().get();
		distincts = data.get(vet.getCity());
		showCreateOrEdit = true;
		showInfo = true;
	}

	public void prepareRandevuScreen() {
		vet = selectedVets.stream().findFirst().get();
		allVetRandevus = vetService.getAllRandevu(vet.getId());
		filteredVetRandevus = Lists.newArrayList();
		showCreateOrEdit = false;
		showInfo = false;
		showRandevu = true;
		showRandevuInfo = false;
		showRandevuNewScreen = false;
	}

	public void prepareRandevuNewScreen() {
		showCreateOrEdit = false;
		showInfo = false;
		showRandevuInfo = false;
		showRandevuNewScreen = true;
	}

	public void vetRandevuKaydet(){
		vetRandevu.setVetId(vet.getId());
		vetService.vetRandevuKaydet(vetRandevu);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı", "Veteriner hekim randevu verisi başarıyla işlem görmüştür."));
		context.getExternalContext().getFlash().setKeepMessages(true);
		prepareRandevuScreen();
	}

	public void vetRandevuSil(){
		vetRandevu = selectedVetRandevus.stream().findFirst().get();
		vetService.vetRandevuSil(vetRandevu);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı", "Veteriner hekim randevu verisi başarıyla işlem görmüştür."));
		context.getExternalContext().getFlash().setKeepMessages(true);
		prepareRandevuScreen();
	}

	public boolean veterinerMi(){
		return kullaniciPrincipal.getId().equals(vet.getKullaniciId());
	}

	public boolean randevuIcinUygunMu(){
		return selectedVetRandevus != null && !CollectionUtils.isEmpty(selectedVetRandevus) && !kullaniciPrincipal.getId().equals(vet.getKullaniciId()) && selectedVetRandevus.stream().findFirst().get().getRandevuAlanKullanici() == null;
	}

	public boolean randevuIptalEdilebilirMi(){
		return !CollectionUtils.isEmpty(selectedVetRandevus) && kullaniciPrincipal.getId().equals(selectedVetRandevus.stream().findFirst().get().getRandevuAlanKullanici());
	}

	public void randevuAl(){
		VetRandevu vetRandevu = selectedVetRandevus.stream().findFirst().get();
		vetRandevu.setRandevuAlanKullanici(kullaniciPrincipal.getId());
		vetService.vetRandevuKaydet(vetRandevu);
	}

	public void randevuIptalEt(){
		VetRandevu vetRandevu = selectedVetRandevus.stream().findFirst().get();
		vetRandevu.setRandevuAlanKullanici(null);
		vetService.vetRandevuKaydet(vetRandevu);
	}

	public void randevuAlanKisiBilgileriniGetir(){
		VetRandevu vetRandevu = selectedVetRandevus.stream().findFirst().get();
		Optional<Kullanici> kullanici = userService.findById(vetRandevu.getRandevuAlanKullanici());
		randevuAlanKisi = kullanici.get();
	}

	public void sil() throws IOException {
		vetService.delete(selectedVets);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/vet/vet.jsf");
	}

	public void onCityChange() {
		if(vet.getCity() != null && !vet.getCity().equals("")){
			distincts = data.get(vet.getCity());
		} else distincts = new HashMap<String, String>();
	}

	public void kullaniciIliskiEkraniHazirla() {
		vet = selectedVets.stream().findFirst().get();
		showKullaniciIliski = true;
		showCreateOrEdit = false;
	}

	public void dogrula() throws IOException {
		Kullanici kullanici = userService.findByUserName(vet.getKullaniciAdi());
		if(kullanici != null){
			vet.setKullaniciId(kullanici.getId());
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Başarısız", "Girilen kullanıcı adı bulunamadı veya başka bir yerde görevldiri."));
		}
	}

	public void kullaniciKaydet() throws IOException {
		if(vet.getKullaniciId() != null){
			vetService.save(vet);
			Kullanici byId = userService.findById(vet.getKullaniciId()).get();
			byId.setKullaniciTipi(KullaniciTipiEnum.VET.getId());
			userService.save(byId);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Başarılı", "Veteriner hekim başarıyla bir kullanıcı ile ilişkilendirilmiştir."));
			context.getExternalContext().getFlash().setKeepMessages(true);
			FacesContext.getCurrentInstance().getExternalContext().redirect("/vet/vet.jsf");
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Hata", "Lütfen doğru bir kullanıcı giriniz!"));
		}

	}

}
