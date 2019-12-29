package com.team1.animalproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
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
public class Animal implements Serializable {

	private static final long serialVersionUID = 1086930970865820419L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public String id;

	@Column(name = "name", nullable = false)
	public String name;

	@Column(name = "address", nullable = false)
	public String address;

	@Column(name = "birthdate")
	public Date birthdate;

	@Column(name = "tur_id", nullable = false)
	public String turId;

	@Column(name = "cins_id", nullable = false)
	public String cinsId;

	@Transient
	public String turAd;

	@Transient
	public String cinsAd;
}


