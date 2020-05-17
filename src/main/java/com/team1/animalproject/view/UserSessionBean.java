package com.team1.animalproject.view;

import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.model.dto.KullaniciPrincipal;
import com.team1.animalproject.service.UserService;
import com.team1.animalproject.view.utils.DateUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings ("ALL")
@Component
@Scope ("view")
@EqualsAndHashCode ()
@Data
public class UserSessionBean extends BaseViewController implements Serializable {

	private static final long serialVersionUID = 3603365473964743370L;

	private static final Logger logger = LoggerFactory.getLogger(UserSessionBean.class);

	List<KullaniciPrincipal> principals = Lists.newArrayList();

	List<String> userNamesLists = new ArrayList<>();
	private List<Kullanici> kullanici;
	private Kullanici selectedKullanici;

	@Autowired
	private SessionRegistry sessionRegistry;

	@Autowired
	private UserService userService;

	@Override
	@PostConstruct
	public void viewOlustur() {
		super.altVerileriVeIlkEkraniHazirla();
	}

	@Override
	public void ilkEkraniHazirla() {

		for(Object principal : sessionRegistry.getAllPrincipals()){
			if(principal instanceof KullaniciPrincipal){
				principals.add((KullaniciPrincipal) principal);
				System.out.println(((KullaniciPrincipal) principal).getId());
				userNamesLists.add(((KullaniciPrincipal) principal).getId());
			}
		}

		Optional<List<Kullanici>> kullanicis = userService.findByIdIn(userNamesLists);
		kullanici = kullanicis.get();
		kullanici.forEach(kullanici1 -> {
			List<SessionInformation> allSessions = sessionRegistry.getAllSessions(principals.stream().filter(o -> o.getId().equals(kullanici1.getId())).findFirst().get(), true);
			kullanici1.setExpired(allSessions.stream().allMatch(SessionInformation::isExpired));
			kullanici1.setEmail(DateUtil.dateAsString(allSessions.get(allSessions.size()-1).getLastRequest()));
		});
	}

	public void kill() {
		List<SessionInformation> allSessions = sessionRegistry.getAllSessions(principals.stream().filter(o -> o.getId().equals(selectedKullanici.getId())).findFirst().get(), true);
		allSessions.forEach(sessionInformation -> {
			sessionInformation.expireNow();
			System.out.println(sessionInformation.getLastRequest());
		});
	}

}
