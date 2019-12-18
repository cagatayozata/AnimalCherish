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
public class Shelter implements Serializable {

	private static final long serialVersionUID = -785304844585574390L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public String id;

	@Column(name = "name", nullable = false)
	public String name;

	@Column(name = "address", nullable = false)
	public String address;

	@Column(name = "capacity", nullable = false)
	public String capacity;

	@Column(name = "email", nullable = false)
	public String email;

	@Column(name = "phone")
	public String phone;

	@Column(name = "details")
	public String details;

	@Column(name = "birthdate")
	public Date birthdate;

}