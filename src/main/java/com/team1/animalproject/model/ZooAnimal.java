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
public class ZooAnimal implements Serializable {

	private static final long serialVersionUID = -6369307033826282965L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public String id;

	@Column(name = "animalId", nullable = false)
	public String animalId;

	@Column(name = "zooId", nullable = false)
	public String zooId;
}
