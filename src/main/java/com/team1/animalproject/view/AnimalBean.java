package com.team1.animalproject.view;

import com.team1.animalproject.model.Animal;
import com.team1.animalproject.model.AnimalTarihce;
import com.team1.animalproject.model.AnimalTarihceDetay;
import com.team1.animalproject.model.Cins;
import com.team1.animalproject.model.GercekKisi;
import com.team1.animalproject.model.Ilac;
import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.model.MedicalReport;
import com.team1.animalproject.model.MedicalReportMedicine;
import com.team1.animalproject.model.Tur;
import com.team1.animalproject.model.dto.KullaniciPrincipal;
import com.team1.animalproject.service.AnimalService;
import com.team1.animalproject.service.BlockchainService;
import com.team1.animalproject.service.CinsService;
import com.team1.animalproject.service.GercekKisiService;
import com.team1.animalproject.service.IlacService;
import com.team1.animalproject.service.RaporService;
import com.team1.animalproject.service.TurService;
import com.team1.animalproject.service.UserService;
import com.team1.animalproject.view.utils.DateUtil;
import com.team1.animalproject.view.utils.JSFUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.compress.utils.Lists;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Scope ("view")
@EqualsAndHashCode (callSuper = false)
@Data
public class AnimalBean extends BaseViewController<Animal> implements Serializable {

	private static final long serialVersionUID = 5246560358820611506L;

	private static final Logger logger = LoggerFactory.getLogger(AnimalBean.class);

	@Autowired
	private AnimalService animalService;

	@Autowired
	private TurService turService;

	@Autowired
	private CinsService cinsService;

	@Autowired
	private IlacService ilacService;

	@Autowired
	private BlockchainService blockchainService;

	@Autowired
	private RaporService raporService;

	@Autowired
	private UserService userService;

	@Autowired
	private GercekKisiService gercekKisiService;

	private Animal animal = new Animal();
	private List<Animal> selectedAnimals;
	private List<Animal> allAnimals;
	private List<Animal> filteredAnimals;

	// medical report
	private List<MedicalReport> medicalReports;
	private List<MedicalReport> selectedMedicalReports;
	private List<MedicalReport> filteredMedicalReports;
	private List<MedicalReportMedicine> medicalReportMedicines;
	private MedicalReport medicalReport;
	private MedicalReportMedicine medicalReportMedicine;
	private AnimalTarihceDetay animalTarihceDetay;
	private boolean showMedicalReport;
	private boolean showMedicalReportCreateOrEdit;
	private boolean showIlacList;
	private boolean showIlacCreateOrEdit;
	private boolean showTarihce;

	//Tarihce
	private List<AnimalTarihce> tarihceList;

	private List<Tur> turler;
	private List<Cins> cinsler;
	private List<Ilac> ilaclar;
	private String sahipNo;
	private String kupeNo;

	private boolean showCreateOrEdit;
	private boolean showInfo;

	@Override
	@PostConstruct
	public void viewOlustur() {
		super.altVerileriVeIlkEkraniHazirla();
	}

	@Override
	public void ilkEkraniHazirla() {
		showMedicalReport = false;
		showMedicalReportCreateOrEdit = false;
		showIlacList = false;
		showIlacCreateOrEdit = false;
		showCreateOrEdit = false;
		showInfo = false;
		showTarihce = false;
		tarihceList = Lists.newArrayList();

		allAnimals = animalService.getAll();
		if(kullaniciPrincipal.getAramaKelimesi() != null){
			String aramaKelimesi = kullaniciPrincipal.getAramaKelimesi();
			if(allAnimals != null){
				allAnimals = allAnimals.stream()
						.filter(animal1 -> animal1.getTurAd().toLowerCase().contains(aramaKelimesi.toLowerCase()) || animal1.getId().toLowerCase().contains(aramaKelimesi.toLowerCase())
								|| animal1.getAddress().toLowerCase().contains(aramaKelimesi.toLowerCase()) || animal1.getName().toLowerCase().contains(aramaKelimesi.toLowerCase()))
						.collect(Collectors.toList());
			}

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			((KullaniciPrincipal) authentication.getPrincipal()).setAramaKelimesi(null);
		}

		filteredAnimals = new ArrayList<>(allAnimals);
		animal = new Animal();
		turler = turService.getAll();
		selectedAnimals = new ArrayList<>();
	}

	public void medicalReportKapat() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("/animal/animal.jsf");
	}

	public void medicalReportEkraniHazirla() throws IOException {
		medicalReports = blockchainService.getAllByAnimalId(selectedAnimals.stream().findFirst().get().getId());
		medicalReport = new MedicalReport();
		filteredMedicalReports = medicalReports;
		showMedicalReport = true;
		showMedicalReportCreateOrEdit = false;
		showIlacList = false;
		showIlacCreateOrEdit = false;
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Blockchain başarıyla çalıştırıldı."));
		context.getExternalContext().getFlash().setKeepMessages(true);
		selectedMedicalReports = new ArrayList<>();
	}

	public void saglikRaporuYeniEkraniHazirla() {
		showMedicalReportCreateOrEdit = true;
		showMedicalReport = false;
	}

	public void saglikRaporuDetayHazirla() {
		showMedicalReportCreateOrEdit = true;
		medicalReport = selectedMedicalReports.stream().findFirst().get();
		showMedicalReport = false;
		showInfo = true;
	}

	public void raporKaydet() throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		medicalReport.setAnimalId(selectedAnimals.stream().findFirst().get().getId());
		medicalReport.setDate(dateFormat.format(date));
		medicalReport.setOlusturan(kullaniciPrincipal.getId());
		blockchainService.transactionOlustur(medicalReport);
		AnimalTarihce animalTarihce = AnimalTarihce.builder()
				.animalId(selectedAnimals.stream().findFirst().get().getId())
				.deger("Rapor Numarası: " + medicalReport.getReportNum())
				.kimTarafindan(kullaniciPrincipal.getId())
				.neZaman(DateUtil.nowAsString())
				.yapilanIslem("Hayvan sayfasında sağlık raporu ekleme işlemi")
				.build();
		animalService.tarihceKaydet(animalTarihce);
	}

	public void onTurChange() {
		if(animal.getTurId() != null && !animal.getTurId().equals("")) cinsler = cinsService.findByTurId(animal.getTurId());
	}

	public void kaydet() throws IOException {
		animal.setGuncelleyenId(kullaniciPrincipal.getId());
		animalService.save(animal);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı", "Hayvan verisi başarıyla işlem görmüştür."));
		context.getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/animal/animal.jsf");

	}

	public void prepareNewScreen() {
		showCreateOrEdit = true;
	}

	public void prepareUpdateScreen() {
		animal = selectedAnimals.stream().findFirst().get();
		cinsler = cinsService.findByTurId(animal.getTurId());
		showCreateOrEdit = true;
	}

	public void prepareInfoScreen() {
		animal = selectedAnimals.stream().findFirst().get();
		cinsler = cinsService.findByTurId(animal.getTurId());
		showCreateOrEdit = true;
		showInfo = true;
	}

	public void ilacEklemeEkraniHazirla() throws IOException {
		ilaclar = ilacService.getAll();
		showIlacCreateOrEdit = false;
		showIlacList = true;
		medicalReport = selectedMedicalReports.stream().findFirst().get();
		medicalReportMedicines = blockchainService.ilaclariGetir(medicalReport.getId(), kullaniciPrincipal.getId());
		medicalReportMedicine = MedicalReportMedicine.builder().build();
	}

	public void ilacYeniEkraniHazirla() {
		ilaclar = ilacService.getAll();
		showIlacCreateOrEdit = true;
		showIlacList = false;
		medicalReportMedicine = MedicalReportMedicine.builder().build();
	}

	public void ilacKaydet() throws IOException {
		medicalReportMedicine.setMedicalReportId(medicalReport.getId());
		blockchainService.transactionOlustur(medicalReportMedicine, kullaniciPrincipal.getId());
		AnimalTarihce animalTarihce = AnimalTarihce.builder()
				.animalId(selectedAnimals.stream().findFirst().get().getId())
				.deger("Rapor Numarası: " + medicalReport.getReportNum() + " / Eklenen İlaç: " + medicalReportMedicine.getIlacId())
				.kimTarafindan(kullaniciPrincipal.getId())
				.neZaman(DateUtil.nowAsString())
				.yapilanIslem("Hayvan sayfasında sağlık raporu için ilaç ekleme işlemi")
				.build();
		animalService.tarihceKaydet(animalTarihce);
	}

	public void ilacEkraniKapat() {
		showMedicalReport = true;
		showIlacCreateOrEdit = false;
		showIlacList = false;
	}

	public void sahipEklemeEkraniHazirla() {
		sahipNo = null;
		animal = selectedAnimals.stream().findFirst().get();
	}

	public void sahipDogrulaVeKaydet() {
		Optional<GercekKisi> byKimlikNo = gercekKisiService.findByKimlikNo(sahipNo);
		if(byKimlikNo.isPresent()){
			animal.setGuncelleyenId(kullaniciPrincipal.getId());
			animal.setSahipId(byKimlikNo.get().getId());
			animalService.update(animal);
			JSFUtil.hideDialog("sahipEklemeDialogWidgetVar");
			AnimalTarihce animalTarihce = AnimalTarihce.builder()
					.animalId(animal.getId())
					.deger("Sahip kimlik no: " + sahipNo)
					.kimTarafindan(kullaniciPrincipal.getId())
					.neZaman(DateUtil.nowAsString())
					.yapilanIslem("Hayvan sayfasında hayvan sahibi ilişkilendirme işlemi")
					.build();
			animalService.tarihceKaydet(animalTarihce);

		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Hata", "Kimlik No Doğrulanamadı!"));
			context.getExternalContext().getFlash().setKeepMessages(true);
		}
	}

	public StreamedContent receteCikart() throws FileNotFoundException {
		DefaultStreamedContent defaultStreamedContent = null;
		try{
			medicalReport = selectedMedicalReports.stream().findFirst().get();
			String raporYolu = raporService.raporuOlustur(kullaniciPrincipal.getId(), medicalReport.getId());
			FileInputStream fileInputStream = new FileInputStream(new File(raporYolu));
			defaultStreamedContent = new DefaultStreamedContent(fileInputStream);
			defaultStreamedContent.setName(UUID.randomUUID().toString() + ".pdf");
			JSFUtil.executeScript("removePageRedirectBlock()");
		} catch (Exception e){
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Hata", "Reçetesini çıkartmak istediğiniz rapora ilaç girilmemiştir."));
			context.getExternalContext().getFlash().setKeepMessages(true);
		}

		return defaultStreamedContent;
	}

	public void sil() throws IOException {
		animalService.delete(selectedAnimals);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/animal/animal.jsf");
	}

	public void sorgula() {
		animalTarihceDetay = animalService.hayvanNerede(kupeNo);
	}

	public void tarihceHazirla() throws IOException {
		showTarihce = true;
		tarihceList = blockchainService.tarihceGetir(selectedAnimals.stream().findFirst().get().getId());

		tarihceList.stream().forEach(animalTarihce -> {
			Optional<Kullanici> byId = userService.findById(animalTarihce.getKimTarafindan());
			byId.ifPresent(kullanici -> animalTarihce.setKimTarafindan(kullanici.getUserName()));
		});
	}
}
