package com.team1.animalproject.service;

import com.team1.animalproject.exception.BaseExceptionType;
import com.team1.animalproject.exception.BusinessRuleException;
import com.team1.animalproject.exception.ViewException;
import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IBaseService<Kullanici> {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Kullanici> getAll() {
        return null;
    }

    @Override
    public void save(Kullanici kullanici) {
        Optional<Kullanici> byUserNameOrEmailOrPhoneNumber = userRepository.findByUserNameOrEmailOrPhoneNumber(kullanici.name, kullanici.email, kullanici.phoneNumber);
        if (byUserNameOrEmailOrPhoneNumber.isPresent()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, BaseExceptionType.KULLANICI_ADI_MAIL_PHONE_KULLANILIYOR.getValidationMessage(), null));
        } else {
            kullanici.setId(UUID.randomUUID().toString());
            userRepository.save(kullanici);
        }
    }

    @Override
    public void update(Kullanici kullanici) {

    }

    @Override
    public void delete(List<Kullanici> t) {

    }

    public Optional<Kullanici> findByUserNameAndPassword(String username, String sifre){
        return userRepository.findByUserNameAndPassword(username, sifre);
    }

    public Optional<Kullanici> findById(String id){
        return userRepository.findById(id);
    }

}
