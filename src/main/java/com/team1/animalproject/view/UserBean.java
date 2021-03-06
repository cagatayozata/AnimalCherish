package com.team1.animalproject.view;

import com.team1.animalproject.auth.Constants;
import com.team1.animalproject.blockchain.queries.CreateAccount;
import com.team1.animalproject.exception.BaseExceptionType;
import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.service.EmailService;
import com.team1.animalproject.service.UserService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.stellar.sdk.KeyPair;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static com.team1.animalproject.auth.Constants.MAIN_URL;

@Component
@Scope ("view")
@EqualsAndHashCode ()
@Data
@Slf4j
public class UserBean extends BaseViewController<Kullanici> implements Serializable {

	private static final long serialVersionUID = 5246560358820611506L;
	private static final Logger logger = LoggerFactory.getLogger(UserBean.class);
	@Autowired
	private UserService userService;
	@Autowired
	private EmailService emailService;
	private Kullanici kullanici = new Kullanici();

	private UploadedFile file;
	private FileUploadEvent fileEvent;
	private boolean anyFile;
	private boolean sifreDegis;
	private boolean girisYapili;

	private String repeatPassword;

	@Override
	@PostConstruct
	public void viewOlustur() {
		super.altVerileriVeIlkEkraniHazirla();
	}

	public boolean validatePassword() {
		if(repeatPassword != null && repeatPassword.equals(kullanici.getPassword())){
			return true;
		}
		return repeatPassword == null;
	}

	@Override
	public void ilkEkraniHazirla() {
		sifreDegis = true;
		anyFile = true;
		girisYapili = false;
		if(kullaniciPrincipal.getId() != null){
			Optional<Kullanici> byId = userService.findById(kullaniciPrincipal.getId());
			if(byId.isPresent()){
				kullanici = byId.get();
				sifreDegis = false;
				girisYapili = true;
			}
		}
	}

	public void sifreDegisir() {
		sifreDegis = true;
	}

	public void kayitOl() throws IOException, NoSuchAlgorithmException {
		kullanici.setFileUploadEvent(fileEvent);
		boolean validate = validatePassword();

		if(!girisYapili){
			KeyPair keyPair = KeyPair.random();
			CreateAccount createAccount = new CreateAccount();
			String testAccount = createAccount.createTestAccount(keyPair);
			kullanici.setKeyPair(new String(keyPair.getSecretSeed()));
		}

		boolean kayit = userService.kayitOl(kullanici, girisYapili, sifreDegis);
		if(validate){
			if(kayit){

				userService.update(kullanici);

				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Başarılı", "Başarıyla kaydolundu."));
				context.getExternalContext().getFlash().setKeepMessages(true);
				FacesContext.getCurrentInstance().getExternalContext().redirect("/login.jsf");
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, BaseExceptionType.KULLANICI_ADI_MAIL_PHONE_KULLANILIYOR.getValidationMessage(), null));
				context.getExternalContext().getFlash().setKeepMessages(true);
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Şifreler uyuşmuyor!", null));
			context.getExternalContext().getFlash().setKeepMessages(true);
		}

	}

	public void login() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext extenalContext = facesContext.getExternalContext();
		RequestDispatcher dispatcher = ((ServletRequest) extenalContext.getRequest()).getRequestDispatcher(Constants.LOGIN_PROCESSING_URL);
		try{
			dispatcher.forward((ServletRequest) extenalContext.getRequest(), (ServletResponse) extenalContext.getResponse());
		} catch (ServletException | IOException e){
			log.error("Couldnt forward request to RequestDispatcher ", e);
		}
		facesContext.responseComplete();
	}

	public void anaSayfayaGit() throws IOException {
		if(!girisYapili){
			FacesContext.getCurrentInstance().getExternalContext().redirect("/landing.jsf");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/index.jsf");
		}
	}

	public void sifreSifirla() throws IOException {
		Optional<Kullanici> byUserNameAndEmail = userService.findByUserNameAndEmail(kullanici.getUserName(), kullanici.getEmail());
		if(byUserNameAndEmail.isPresent()){
			String yeniSifre = RandomStringUtils.randomAlphabetic(10);
			String url = MAIN_URL + "/sifirla/" + byUserNameAndEmail.get().getId() + "/" + yeniSifre;
			emailService.sendSimpleMessage(byUserNameAndEmail.get().getEmail(), "Şifre Sıfırla",
					"Merhabalar " + byUserNameAndEmail.get().getName() + "\nYeni Şifreniz: " + yeniSifre + "\nDoğrulamak için tıklayınız: " + url);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Şifre sıfırlama maili adresinize gönderilmiştir.", null));
			context.getExternalContext().getFlash().setKeepMessages(true);
			FacesContext.getCurrentInstance().getExternalContext().redirect("/login.jsf");

		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Seçili kullanıcı adı ve mail ilişkisi bulunamadı!", null));
			context.getExternalContext().getFlash().setKeepMessages(true);
		}
	}

	public void onUpload() {
		if(file != null) anyFile = false;
	}

	public void onUpload(FileUploadEvent file) {
		System.out.println("File Uploaded" + file.getFile().getFileName());
		this.fileEvent = file;
	}

}
