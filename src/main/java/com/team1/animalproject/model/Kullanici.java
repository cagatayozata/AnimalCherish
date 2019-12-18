package com.team1.animalproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@ToString(callSuper = true)
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(content = JsonInclude.Include.NON_NULL)
public class Kullanici implements Serializable {

    private static final long serialVersionUID = 8237988717501947043L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    public String id;

    @Column(name = "user_name", nullable = false)
    public String userName;

    @Column(name = "password", nullable = false)
    public String password;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "surname", nullable = false)
    public String surname;

    @Column(name = "phone_number")
    public String phoneNumber;

    @Column(name = "email")
    public String email;

}