package com.team1.animalproject.view;

import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.model.KullaniciRol;
import com.team1.animalproject.model.Rol;
import com.team1.animalproject.model.RolYetki;
import com.team1.animalproject.model.Yetki;
import com.team1.animalproject.service.RolService;
import com.team1.animalproject.service.UserService;
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
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings ("ALL")
@Component
@Scope ("view")
@EqualsAndHashCode ()
@Data
public class RolBean extends BaseViewController<Rol> implements Serializable {

	private static final long serialVersionUID = -3431201652451639852L;

	private static final Logger logger = LoggerFactory.getLogger(RolBean.class);

	@Autowired
	private RolService rolService;

	@Autowired
	private UserService userService;

	private Rol rol = new Rol();
	private List<Rol> selectedRols;
	private List<Rol> allRols;
	private List<Rol> filteredRols;
	private DualListModel<Yetki> yetkis;

	private List<Kullanici> workers;
	private List<Kullanici> selectedWorkers;
	private List<Kullanici> filteredWorkers;

	private List<Kullanici> addedWorkers;
	private List<Kullanici> selectedAddedWorkers;
	private List<Kullanici> filteredAddedWorkers;

	private boolean showCreateOrEdit;
	private boolean showInfo;
	private boolean showWorkerCreateOrEdit;
	private boolean workerInfo;
	private String rolId;

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
		showWorkerCreateOrEdit = false;
		workerInfo = false;
		addedWorkers = new ArrayList<>();
		workers = userService.getAll();
		filteredWorkers = new ArrayList<>();
		filteredAddedWorkers = new ArrayList<>();
	}

	public void kaydet() throws IOException {
		if(rol.getId() == null) rol.setId(UUID.randomUUID().toString());

		List<Yetki> target = yetkis.getTarget();

		List<RolYetki> rolYetkis = new ArrayList<>();
		target.forEach(yetki -> rolYetkis.add(RolYetki.builder().id(UUID.randomUUID().toString()).rolId(rol.getId()).yetkiId(yetki.getId()).build()));

		rolService.save(rol);
		rolYetkis.forEach(rolYetki -> rolYetki.setRolId(rol.getId()));
		rolService.rolYetkiSave(rolYetkis, rol.getId());
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı", "Rol başarıyla eklendi."));
		context.getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/rol/rol.jsf");

	}

	public void prepareNewScreen() {
		prepareYetkis();
		showCreateOrEdit = true;
	}

	public void prepareUpdateScreen() {
		rol = selectedRols.stream().findFirst().get();
		prepareYetkis();
		showCreateOrEdit = true;
	}

	public void prepareInfoScreen() {
		rol = selectedRols.stream().findFirst().get();
		prepareYetkis();
		showCreateOrEdit = true;
		showInfo = true;
	}

	public void prepareWorkerNewScreen() {
		showWorkerCreateOrEdit = true;
		findKullanicis();
		findKullanicisForAdd();
	}

	public void deleteWorker() {
		addedWorkers.removeAll(selectedAddedWorkers);
		workers.addAll(selectedAddedWorkers);
		selectedAddedWorkers = new ArrayList<>();
	}

	public void addWorker() {
		addedWorkers.addAll(selectedWorkers);
		workers.removeAll(selectedWorkers);
		selectedWorkers = new ArrayList<>();
	}

	private void findKullanicis() {
		rolId = selectedRols.stream().findFirst().get().getId();
		List<KullaniciRol> workersIn = rolService.findByRolIdKullanici(rolId);
		Optional<List<Kullanici>> kullanicis = userService.findByIdIn(workersIn.stream().map(KullaniciRol::getKullaniciId).collect(Collectors.toList()));
		addedWorkers = kullanicis.orElseGet(ArrayList::new);
		filteredAddedWorkers = addedWorkers;
		showWorkerCreateOrEdit = true;
	}

	private void findKullanicisForAdd() {
		workers.removeAll(addedWorkers);
		filteredWorkers = workers;
	}

	public void prepareYetkis() {
		yetkis = new DualListModel<>();

		List<RolYetki> ekliOlmayanlar = new ArrayList<>();
		List<RolYetki> ekliler = new ArrayList<>();

		List<Yetki> ekliYetkiler = new ArrayList<>();
		List<Yetki> ekliOlmayanYetkiler = new ArrayList<>();

		List<Yetki> allYetkis = rolService.getAllYetkis();
		if(allYetkis != null) ekliOlmayanYetkiler = allYetkis;

		if(rol.getId() != null){

			List<RolYetki> byRolIdNotIn = rolService.findByRolIdNotIn(rol.getId());
			List<RolYetki> byRolIdIn = rolService.findByRolIdIn(rol.getId());

			if(byRolIdNotIn != null){
			}

			if(byRolIdIn != null){
				ekliler = byRolIdIn;
			}

			List<Yetki> ekliIds = rolService.findYetkiByIds(ekliler.stream().map(RolYetki::getYetkiId).collect(Collectors.toList()));
			if(ekliIds != null){
				ekliYetkiler = ekliIds;
			}
		}

		ekliOlmayanYetkiler.removeAll(ekliYetkiler);

		yetkis.setSource(ekliOlmayanYetkiler);
		yetkis.setTarget(ekliYetkiler);
	}

	public void sil() throws IOException {
		rolService.delete(selectedRols);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/rol/rol.jsf");
	}

	public void workerSave() throws IOException {
		List<KullaniciRol> kullaniciRols = new ArrayList<>();
		if(addedWorkers.size() > 0){
			addedWorkers.forEach(kullanici -> kullaniciRols.add(KullaniciRol.builder().id(UUID.randomUUID().toString()).rolId(rolId).kullaniciId(kullanici.id).build()));

			rolService.saveKullanici(kullaniciRols, rolId);
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Başarılı", "Kullanıcı İlişkileri Başarıyla Güncellendi."));
		context.getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/rol/rol.jsf");
	}
}
