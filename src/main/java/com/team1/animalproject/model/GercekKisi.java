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
import java.util.Date;

@Entity
@ToString (callSuper = true)
@Data
@EqualsAndHashCode (of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude (content = JsonInclude.Include.NON_NULL)
public class GercekKisi extends Auditable<String, String> implements Serializable {

	private static final long serialVersionUID = -5853130388615981032L;

	@Id
	@Column (name = "id", unique = true, nullable = false)
	public String id;

	@Column (name = "ad", nullable = false)
	public String ad;

	@Column (name = "adres")
	public String adresi;

	@Column (name = "dogum_tarihi")
	public Date dogumTarihi;

	@Column (name = "kimlik_no", unique = true)
	public String kimlikNo;

	@Column (name = "telefon")
	public String telefon;
}
