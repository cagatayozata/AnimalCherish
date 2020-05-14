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
import java.io.Serializable;

@Entity
@ToString (callSuper = true)
@Data
@EqualsAndHashCode (of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude (content = JsonInclude.Include.NON_NULL)
public class PetShopWorker extends Auditable<String, String> implements Serializable {

	private static final long serialVersionUID = 8791730275724223567L;

	@Id
	@Column (name = "id", unique = true, nullable = false)
	public String id;

	@Column (name = "pet_shop_id", nullable = false)
	public String petShopId;

	@Column (name = "worker_id", nullable = false)
	public String workerId;
}
