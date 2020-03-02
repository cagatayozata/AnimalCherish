package com.team1.animalproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.team1.animalproject.model.dto.Auditable;
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
public class ZooWorker extends Auditable<String, String> implements Serializable {

	private static final long serialVersionUID = 2988665675240704740L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public String id;

	@Column(name = "zooId", nullable = false)
	public String zooId;

	@Column(name = "workerId", nullable = false)
	public String workerId;
}
