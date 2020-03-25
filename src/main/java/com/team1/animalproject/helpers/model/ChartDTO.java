package com.team1.animalproject.helpers.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude (content = JsonInclude.Include.NON_NULL)
public class ChartDTO implements Serializable {

	private static final long serialVersionUID = 2772977604562313448L;

	private String name;
	private Number value;
	private String birim;
	private String color;
	private List<ChartDTO> drillDownList;
}
