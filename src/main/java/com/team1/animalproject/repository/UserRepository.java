package com.team1.animalproject.repository;

import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.repository.custom.CustomUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<Kullanici, String>, CustomUserRepository {

    Optional<List<Kullanici>> findByUserNameOrEmailOrPhoneNumber(String username, String email, String phonenumber);

    Optional<Kullanici> findByUserNameAndPassword(String userName, String password);

    Optional<Kullanici> findByUserNameAndEmail(String userName, String email);

    Optional<Kullanici> findByUserName(String userName);

    Optional<Kullanici> findById(String id);

    Optional<List<Kullanici>> findByIdIn(List<String> ids);
}
