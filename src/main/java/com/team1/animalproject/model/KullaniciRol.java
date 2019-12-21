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
public class KullaniciRol implements Serializable {

    private static final long serialVersionUID = 2759414932277204060L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    public String id;

    @Column(name = "rol_id", nullable = false)
    public String rolId;

    @Column(name = "kullanici_id", nullable = false)
    public String kullaniciId;
}
