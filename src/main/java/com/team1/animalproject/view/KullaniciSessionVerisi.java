package com.team1.animalproject.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team1.animalproject.auth.Constants;
import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.model.dto.KullaniciPrincipal;
import com.team1.animalproject.service.UserService;
import com.team1.animalproject.view.utils.JSFUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.component.graphicimage.GraphicImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if ("anonymousUser".equalsIgnoreCase(authentication.getPrincipal().toString())) {
            return KullaniciPrincipal.builder().yetkiler(new ArrayList<>()).build();
        }
        KullaniciPrincipal principal = (KullaniciPrincipal) authentication.getPrincipal();
        if (principal.getYetkiler() == null) {
            principal.setYetkiler(new ArrayList<>());
        }
        return principal;
    }

    public void reloadPage() {
        JSFUtil.reload();
    }

    private void kullaniciBilgileriniGetir() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ("anonymousUser".equalsIgnoreCase(authentication.getPrincipal().toString())) {
            kullanici = Kullanici.builder().name("Giriş Yapılmadı").build();
        } else {
            KullaniciPrincipal kullaniciPrincipal = (KullaniciPrincipal) authentication.getPrincipal();
            Optional<Kullanici> kullaniciResponse = userService.findById(kullaniciPrincipal.getId());

            if (kullaniciResponse.isPresent()) {
                kullanici = kullaniciResponse.get();
            }
        }
    }

    public String resimUrl() {
        kullaniciBilgileriniGetir();
        if (getKullaniciBilgisi().getId() != null) {
            File f = new File(Constants.AVATAR_PATH + kullanici.getId() + ".jpg");
            if (f.exists()) {
                return Constants.AVATAR_PATH + getKullaniciBilgisi().getId() + ".jpg";
            }
        }

        return Constants.AVATAR_PATH + "avatar-male.jpg";
    }

    public String getAdSoyad() {
        if (getKullaniciBilgisi().getId() != null) {
            kullaniciBilgileriniGetir();
            return kullanici.getName() + " " + kullanici.getSurname();
        }
        return "Giriş Yapılmadı";
    }

    public List<String> yetkileriGetir(){
        return getKullaniciBilgisi().getYetkiler().stream().distinct().sorted().collect(Collectors.toList());
    }

    public String getKullaniciId(){
        if(getKullaniciBilgisi().getId() != null)
        return getKullaniciBilgisi().getId();
        return "anonim";
    }

}
