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

@ToString(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(content = JsonInclude.Include.NON_NULL)
public class AnimalTarihce {

	private static final long serialVersionUID = 4687592548276958651L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public String id;

	@Column
	private String animalId;

	@Column
	private String yapilanIslem;

	@Column
	private String deger;

	@Column
	private String kimTarafindan;

	@Column
	private String neZaman;
}


