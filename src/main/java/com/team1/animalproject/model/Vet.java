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
public class Vet implements Serializable {

	private static final long serialVersionUID = -2551638311989131114L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public String id;

	@Column(name = "name", nullable = false)
	public String name;

	@Column(name = "education", nullable = false)
	public String education;

	@Column(name = "phone", nullable = false)
	public String phone;

	@Column(name = "email", nullable = false)
	public String email;

	@Column(name = "workplace")
	public String workplace;

	@Column(name = "clinic", nullable = false)
	public String clinic;

	@Column(name = "details")
	public String details;

	@Column(name = "birthdate")
	public Date birthdate;

	@Column(name="city")
	private String city;

	@Column(name="ilce")
	private String ilce;

}
