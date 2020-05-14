package com.team1.animalproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString (callSuper = true)
@Data
@EqualsAndHashCode (of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude (content = JsonInclude.Include.NON_NULL)
public class MedicalReport implements Serializable {

	private static final long serialVersionUID = -3182719131780857136L;

	public String reportNum = "";
	public String id = "";
	public String tension = "";
	public String bodySugarLevel = "";
	public String pulse = "";
	public String weight = "";
	public String date = "";
	public String description = "";
	public String surgeryDescription = "";
	public String esgal = "";
	private String animalId = "";
	private String olusturan = "";
}


