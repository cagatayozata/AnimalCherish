package com.team1.animalproject.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.model.dto.KullaniciPrincipal;
import com.team1.animalproject.service.UserService;
import com.team1.animalproject.view.utils.JSFUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Optional;

@Slf4j
@Data
@Component
@Scope("session")
public class KullaniciSessionVerisi implements Serializable {

    private static final long serialVersionUID = -3594945290441411747L;

    @Autowired
    private UserService userService;

    private Kullanici kullanici;

    ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        log.info("user session is initialized for user id : " + getKullaniciBilgisi().getId());
        kullaniciBilgileriniGetir();
    }

    public boolean yetkiVarmi(String yetki) {
        return getKullaniciBilgisi().getYetkiler().contains(yetki);
    }

    public KullaniciPrincipal getKullaniciBilgisi() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if("anonymousUser".equalsIgnoreCase(authentication.getPrincipal().toString())){
            return KullaniciPrincipal.builder().build();
        }
        return (KullaniciPrincipal) authentication.getPrincipal();
    }

    public void reloadPage() {
        JSFUtil.reload();
    }

    private void kullaniciBilgileriniGetir() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if("anonymousUser".equalsIgnoreCase(authentication.getPrincipal().toString())){
            kullanici = Kullanici.builder().name("Giriş Yapılmadı").build();
        }else {
            KullaniciPrincipal kullaniciPrincipal = (KullaniciPrincipal) authentication.getPrincipal();
            Optional<Kullanici> kullaniciResponse = userService.findById(kullaniciPrincipal.getId());

            if (kullaniciResponse.isPresent()) {
                kullanici = kullaniciResponse.get();
            }
        }
    }

}