package com.team1.animalproject.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.team1.animalproject.auth.Constants;
import com.team1.animalproject.model.Animal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
@Scope ("view")
@EqualsAndHashCode ()
@Slf4j
@Data
@URLMapping (id = "loginBean", pattern = "/deneme", viewId = "/login.jsf")
public class LoginBean extends BaseViewController<Animal> implements Serializable {

	private static final long serialVersionUID = -4864366458003777266L;

	private static final Logger logger = LoggerFactory.getLogger(LoginBean.class);

	@Getter
	private boolean hata;

	@Getter
	private boolean captchaGoster;

	@Override
	@PostConstruct
	public void viewOlustur() {
		super.altVerileriVeIlkEkraniHazirla();
	}

	@Override
	public void ilkEkraniHazirla() {

	}

	public void mainPage() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("/landing.jsf");
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

}
