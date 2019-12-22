package com.team1.animalproject.view;

import com.team1.animalproject.auth.Constants;
import com.team1.animalproject.exception.BaseExceptionType;
import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.model.dto.KullaniciPrincipal;
import com.team1.animalproject.service.EmailService;
import com.team1.animalproject.service.UserService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

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
@Scope("view")
@EqualsAndHashCode(callSuper = false)
@Data
@Slf4j
public class UserBean extends BaseViewController<Kullanici> implements Serializable {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    private static final long serialVersionUID = 5246560358820611506L;

    private static final Logger logger = LoggerFactory.getLogger(UserBean.class);

    private Kullanici kullanici = new Kullanici();

    @Override
    @PostConstruct
    public void viewOlustur() {
        super.altVerileriVeIlkEkraniHazirla();
    }

    @Override
    public void ilkEkraniHazirla() {
    }

    public void kayitOl() throws IOException, NoSuchAlgorithmException {
        boolean kayit = userService.kayitOl(kullanici);
        if (kayit) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Başarılı", "Başarıyla kaydolundu."));
            context.getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/login.jsf");
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, BaseExceptionType.KULLANICI_ADI_MAIL_PHONE_KULLANILIYOR.getValidationMessage(), null));
            context.getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    public void login() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext extenalContext = facesContext.getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest) extenalContext.getRequest()).getRequestDispatcher(Constants.LOGIN_PROCESSING_URL);
        try {
            dispatcher.forward((ServletRequest) extenalContext.getRequest(), (ServletResponse) extenalContext.getResponse());
        } catch (ServletException | IOException e) {
            log.error("Couldnt forward request to RequestDispatcher ", e);
        }
        facesContext.responseComplete();
    }

    public void anaSayfayaGit() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/landing.jsf");
    }

    public void sifreSifirla() throws IOException {
        Optional<Kullanici> byUserNameAndEmail = userService.findByUserNameAndEmail(kullanici.getUserName(), kullanici.getEmail());
        if (byUserNameAndEmail.isPresent()) {
            String yeniSifre = RandomStringUtils.randomAlphabetic(10);
            String url = MAIN_URL + "/sifirla/" + byUserNameAndEmail.get().getId() + "/" + yeniSifre;
            emailService.sendSimpleMessage(byUserNameAndEmail.get().getEmail(), "Şifre Sıfırla", "Merhabalar " + byUserNameAndEmail.get().getName() + "\nYeni Şifreniz: " + yeniSifre + "\nDoğrulamak için tıklayınız: " + url);
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

}
