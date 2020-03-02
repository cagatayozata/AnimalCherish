package com.team1.animalproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.team1.animalproject.model.dto.Auditable;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@ToString(callSuper = true)
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(content = JsonInclude.Include.NON_NULL)
public class MedicalReport extends Auditable<String, String> implements Serializable {

    private static final long serialVersionUID = -3182719131780857136L;

    public String reportNum;
    public String id;
    public String tension;
    public String bodySugarLevel;
    public String pulse;
    public String weight;
    private String animalId;
    private String olusturan;
    public String date;
    public String description;
    public String surgeryDescription;
    public String medicines;
}


