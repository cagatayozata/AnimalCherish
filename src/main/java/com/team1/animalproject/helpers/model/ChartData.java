package com.team1.animalproject.helpers.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@JsonInclude (content = JsonInclude.Include.NON_NULL)
public class ChartData implements Serializable {

	private static final long serialVersionUID = 2772977604562313448L;

	private String name;
	private String color;
	private String drilldown;
	private Number y;
	private String birim;
}
