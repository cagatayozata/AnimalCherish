package com.team1.animalproject.service;

import com.team1.animalproject.exception.BaseExceptionType;
import com.team1.animalproject.exception.BusinessRuleException;
import com.team1.animalproject.exception.ViewException;
import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.model.KullaniciRol;
import com.team1.animalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return userRepository.findAll();
    }

    @Override
    public void save(Kullanici kullanici) {

    }

    public boolean kayitOl(Kullanici kullanici){
        Optional<Kullanici> byUserNameOrEmailOrPhoneNumber = userRepository.findByUserNameOrEmailOrPhoneNumber(kullanici.userName, kullanici.email, kullanici.phoneNumber);
        if (byUserNameOrEmailOrPhoneNumber.isPresent()) {
            return false;
        } else {
            kullanici.setId(UUID.randomUUID().toString());
            userRepository.save(kullanici);
            return true;
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

    public Optional<List<Kullanici>> findByIdIn(List<String> ids){
        return userRepository.findByIdIn(ids);
    }

    public List<Kullanici> findByIdNotIn(List<String> ids){
        return userRepository.findByIdNotIn(ids);
    }

}
