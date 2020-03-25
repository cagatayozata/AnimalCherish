package com.team1.animalproject.helpers.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@JsonInclude (content = JsonInclude.Include.NON_NULL)
public class ChartDrilldownData implements Serializable {

	private static final long serialVersionUID = -3363745714510736434L;

	private String id;
	private String name;
	@Builder.Default
	private List<ChartData> data = new ArrayList<>();
}
