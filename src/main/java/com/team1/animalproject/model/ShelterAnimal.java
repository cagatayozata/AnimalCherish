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
public class ShelterAnimal implements Serializable {

	private static final long serialVersionUID = 7546302874816294993L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public String id;

	@Column(name = "animalId", nullable = false)
	public String animalId;

	@Column(name = "shelterId", nullable = false)
	public String shelterId;
}
