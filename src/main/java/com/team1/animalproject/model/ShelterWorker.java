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
public class ShelterWorker implements Serializable {

	private static final long serialVersionUID = 8791730275724223567L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public String id;

	@Column(name = "shelterId", nullable = false)
	public String shelterId;

	@Column(name = "workerId", nullable = false)
	public String workerId;
}
