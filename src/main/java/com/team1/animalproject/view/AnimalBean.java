package com.team1.animalproject.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.team1.animalproject.model.Animal;
import com.team1.animalproject.model.Cins;
import com.team1.animalproject.model.MedicalReport;
import com.team1.animalproject.model.Tur;
import com.team1.animalproject.service.AnimalService;
import com.team1.animalproject.service.BlockchainService;
import com.team1.animalproject.service.CinsService;
import com.team1.animalproject.service.TurService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Scope("view")
@EqualsAndHashCode(callSuper = false)
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
    private BlockchainService blockchainService;

    private Animal animal = new Animal();
    private List<Animal> selectedAnimals;
    private List<Animal> allAnimals;
    private List<Animal> filteredAnimals;

    // medical report
    private List<MedicalReport> medicalReports;
    private List<MedicalReport> selectedMedicalReports;
    private List<MedicalReport> filteredMedicalReports;
    private MedicalReport medicalReport;
    private boolean showMedicalReport;
    private boolean showMedicalReportCreateOrEdit;

    private List<Tur> turler;
    private List<Cins> cinsler;

    private boolean showCreateOrEdit;
    private boolean showInfo;

    @Override
    @PostConstruct
    public void viewOlustur() {
        super.altVerileriVeIlkEkraniHazirla();
        allAnimals = animalService.getAll();
        filteredAnimals = new ArrayList<>(allAnimals);
    }

    @Override
    public void ilkEkraniHazirla() {
        showMedicalReport = false;
        showMedicalReportCreateOrEdit = false;
        showCreateOrEdit = false;
        showInfo = false;
        allAnimals = animalService.getAll();
        filteredAnimals = new ArrayList<>(allAnimals);
        animal = new Animal();
        selectedAnimals = new ArrayList<>();
    }

    public void medicalReportKapat() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/animal/animal.jsf");
    }

    public void medicalReportEkraniHazirla() throws IOException {
        blockchainService.init();
        blockchainService.kullaniciDosyasiOlustur(kullaniciPrincipal.getId());
        medicalReports = blockchainService.getAllByAnimalId(selectedAnimals.stream().findFirst().get().getId());
        medicalReport = new MedicalReport();
        filteredMedicalReports = medicalReports;
        showMedicalReport = true;
        showMedicalReportCreateOrEdit = false;
        boolean validate = blockchainService.validate(kullaniciPrincipal.getId());
        if(!validate){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata", "Blockchain Hatası, Verilerde Değişiklik Tespit Edildi. Veriniz Güncelleniyor, Lütfen Sayfayı Yenileyiniz."));
            context.getExternalContext().getFlash().setKeepMessages(true);
            showMedicalReport = false;
            blockchainService.copyFileUsingStream(new File("authority.achain"), new File(kullaniciPrincipal.getId() + ".achain"));
            selectedMedicalReports = new ArrayList<>();
        }else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Başarılı", "Blockchain başarıyla çalıştırıldı."));
            context.getExternalContext().getFlash().setKeepMessages(true);
            selectedMedicalReports = new ArrayList<>();
        }
    }

    public void saglikRaporuYeniEkraniHazirla(){
        showMedicalReportCreateOrEdit = true;
        showMedicalReport = false;
    }

    public void saglikRaporuDetayHazirla(){
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
    }

    public void onTurChange() {
        if (animal.getTurId() != null && !animal.getTurId().equals(""))
            cinsler = cinsService.findByTurId(animal.getTurId());
    }

    public void kaydet() throws IOException {
        animalService.save(animal);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Başarılı", "Hayvan ekleme işlemi başarıyla tamamlandı."));
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

    public void sil() throws IOException {
        animalService.delete(selectedAnimals);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/animal/animal.jsf");
    }



}
