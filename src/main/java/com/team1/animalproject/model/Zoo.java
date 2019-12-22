package com.team1.animalproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
@ToString(callSuper = true)
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(content = JsonInclude.Include.NON_NULL)
public class Zoo implements Serializable {

    private static final long serialVersionUID = -7142769166453097269L ;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    public String id;

    @Column(name = "establishDate", nullable = false)
    public Date establishDate;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "address", nullable = false)
    public String address;

    @Column(name = "workers", nullable = false)
    public String workers;

    @Column(name = "description", nullable = false)
    public String description;

    @Column(name = "phone", nullable = false)
    public String phone;

    @Column(name = "email", nullable = false)
    public String email;

}
