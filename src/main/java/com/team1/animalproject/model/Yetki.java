package com.team1.animalproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Converter;
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
public class Yetki implements Serializable {

	private static final long serialVersionUID = -4951959149422559771L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public String id;

	@Column(name = "name", nullable = false)
	public String name;

	@Column(name = "kod", nullable = false)
	public String kod;
}
