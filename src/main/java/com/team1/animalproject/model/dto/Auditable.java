package com.team1.animalproject.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team1.animalproject.view.KullaniciSessionVerisi;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@EqualsAndHashCode (of = {"id"})
@MappedSuperclass
@EntityListeners (AuditingEntityListener.class)
@Data
public class Auditable<I extends Serializable, U> {

	private static final long serialVersionUID = -2066755288726974124L;

	@SuppressWarnings ("SpringJavaAutowiredMembersInspection")
	@Autowired
	@Transient
	@JsonIgnore
	private KullaniciSessionVerisi kullaniciSessionVerisi;

	@Id
	@Column (nullable = false, unique = true, updatable = false)
	private I id;

	@CreatedDate
	@Temporal (TIMESTAMP)
	protected Date olusmaTarihi;

	@CreatedBy
	protected String olusturanKullanici;

	@LastModifiedDate
	@Temporal (TIMESTAMP)
	protected Date sonGuncellenmeTarihi;


	@PrePersist
	protected void prePersist() {
		if (this.olusmaTarihi == null) olusmaTarihi = new Date();
		if (this.sonGuncellenmeTarihi == null) sonGuncellenmeTarihi = new Date();
		try {
			if(this.olusturanKullanici == null) olusturanKullanici = ((KullaniciPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
		}catch (ClassCastException e){
			System.out.println("Mobil Tarafindan Olusturuldu");
		}
	}

	@PreUpdate
	protected void preUpdate() {
		this.sonGuncellenmeTarihi = new Date();
	}

}