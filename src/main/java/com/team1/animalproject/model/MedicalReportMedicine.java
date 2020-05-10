package com.team1.animalproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString(callSuper = true)
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(content = JsonInclude.Include.NON_NULL)
public class MedicalReportMedicine implements Serializable {

    private static final long serialVersionUID = -3182719131780857136L;

    private String ilacId = "";
    private String kullanimSekli = "";
    private String adet = "";
    private String ilacAd = "";
    private String medicalReportId;
}


