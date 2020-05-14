package com.team1.animalproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.team1.animalproject.model.dto.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Entity
@ToString (callSuper = true)
@Data
@EqualsAndHashCode (of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude (content = JsonInclude.Include.NON_NULL)
public class Vet extends Auditable<String, String> implements Serializable {

	private static final long serialVersionUID = -2551638311989131114L;

	@Id
	@Column (name = "id", unique = true, nullable = false)
	public String id;

	@Column (name = "name", nullable = false)
	public String name;

	@Column (name = "education", nullable = false)
	public String education;

	@Column (name = "phone", nullable = false)
	public String phone;

	@Column (name = "email", nullable = false)
	public String email;

	@Column (name = "workplace")
	public String workplace;

	@Column (name = "clinic", nullable = false)
	public String clinic;

	@Column (name = "details")
	public String details;

	@Column (name = "birthdate")
	public Date birthdate;

	@Column (name = "city")
	private String city;

	@Column (name = "ilce")
	private String ilce;

	@Column (name = "diplomaNo")
	private String diplomaNo;

	@Column (name = "userId")
	private String userId;

	@Column (name = "sicilNo")
	private String sicilNo;

	@Column (name = "kullanici_id")
	private String kullaniciId;

	@Transient
	private String kullaniciAdi;

}
