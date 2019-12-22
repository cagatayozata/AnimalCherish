package com.team1.animalproject.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.model.Yetki;
import com.team1.animalproject.model.dto.KullaniciPrincipal;
import com.team1.animalproject.service.RolService;
import com.team1.animalproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Autowired
    private UserService userService;

    @Autowired
    private transient RolService rolService;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (authentication.getCredentials() == null) {
            log.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }

        String kullaniciAdi = authentication.getName();
        String sifre = authentication.getCredentials().toString();

        UsernamePasswordAuthenticationToken result = null;


        // Rolleri yetkileri koy

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(RoleConstants.ROLE_USER));
        String sifreHashed = "";
        try {
            sifreHashed = UserService.md5Java(sifre);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Optional<Kullanici> byUsername = userService.findByUserNameAndPassword(kullaniciAdi, sifreHashed);

        if (byUsername.isPresent()) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Başarılı", "Başarıyla giriş yapıldı."));
            context.getExternalContext().getFlash().setKeepMessages(true);
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Başarısız", "Girdiğiniz bilgilerden birisi yalnış!"));
            context.getExternalContext().getFlash().setKeepMessages(true);
        }
        if (byUsername.isPresent()) {
            KullaniciPrincipal kullaniciPrincipal = KullaniciPrincipal.builder().id(byUsername.get().id).build();
            List<String> yetkis = new ArrayList<>();
            List<String> strings = rolService.herkesRoluYetkileriGetir();
            if(strings != null)
                yetkis.addAll(strings);

            kullaniciPrincipal.setYetkiler(yetkis);

            kullaniciPrincipal.getYetkiler().addAll(rolService.findByKullaniciId(kullaniciPrincipal.getId()));

            String serializedPrincipal;
            try {
                serializedPrincipal = jacksonObjectMapper.writeValueAsString(kullaniciPrincipal);
            } catch (JsonProcessingException e) {
                log.error("ERROR : coulndnt convert user principal to json string", e);
                throw new InternalAuthenticationServiceException("couldnt prepare principal object!");
            }
            result = new UsernamePasswordAuthenticationToken(kullaniciPrincipal, authentication.getCredentials(), authorities);
            result.setDetails(authentication.getDetails());
        }

        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
