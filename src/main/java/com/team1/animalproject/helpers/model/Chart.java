package com.team1.animalproject.helpers.model;

import com.team1.animalproject.helpers.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Data
@AllArgsConstructor
public class Chart implements Serializable {

	private static final long serialVersionUID = 6870709511041182708L;

	private String dataJson;
	private String drilldownDataJson;

	public static class Builder {

		private final List<ChartData> pieChartModelList;
		private final List<ChartDrilldownData> pieChartDrilldownList;

		public Builder() {
			pieChartModelList = new ArrayList<>();
			pieChartDrilldownList = new ArrayList<>();
		}

		public Builder add(String name, Number value) {
			return add(ChartData.builder().name(name).y(value).build());
		}

		public Builder add(String name, Number value, String color) {
			return add(ChartData.builder().name(name).y(value).color(color).build());
		}

		public Builder add(String name, Number value, String birim, String color) {
			return add(ChartData.builder().name(name).y(value).birim(birim).color(color).build());
		}

		public Builder add(String name, Number value, String color, List<ChartDTO> getDrillDownList) {
			return add(ChartData.builder().name(name).y(value).color(color).drilldown(UUID.randomUUID().toString()).build(), getDrillDownList);
		}

		public Builder add(String name, Number value, String birim, String color, List<ChartDTO> getDrillDownList) {
			return add(ChartData.builder().name(name).y(value).birim(birim).color(color).drilldown(UUID.randomUUID().toString()).build(), getDrillDownList);
		}

		public Builder add(List<ChartDTO> chartDTOs) {
			(chartDTOs).stream().forEach(chartDTO -> {
				if(isNotEmpty(chartDTO.getDrillDownList())){
					add(chartDTO.getName(), chartDTO.getValue(), chartDTO.getBirim(), chartDTO.getColor(), chartDTO.getDrillDownList());
				} else {
					add(chartDTO.getName(), chartDTO.getValue(), chartDTO.getBirim(), chartDTO.getColor());
				}
			});
			return this;
		}

		public Builder add(ChartDTO chartDTO) {
			return add(chartDTO.getName(), chartDTO.getValue(), chartDTO.getColor(), chartDTO.getDrillDownList());
		}

		public Builder add(ChartData pieChartModel, List<ChartDTO> chartList) {
			ChartDrilldownData pieChartDrilldown = ChartDrilldownData.builder().name(pieChartModel.getName()).build();

			(chartList).stream().forEach(chartDTO -> {
				pieChartDrilldown.getData().add(ChartData.builder().color(chartDTO.getColor()).name(chartDTO.getName()).y(chartDTO.getValue()).birim(chartDTO.getBirim()).build());
			});

			return add(pieChartModel, pieChartDrilldown);
		}

		public Builder add(ChartData pieChartModel) {
			pieChartModelList.add(pieChartModel);
			return this;
		}

		public Builder add(ChartData pieChartModel, ChartDrilldownData pieChartDrilldown) {
			pieChartModelList.add(pieChartModel);
			pieChartDrilldown.setId(pieChartModel.getDrilldown());
			pieChartDrilldownList.add(pieChartDrilldown);
			return this;
		}

		public Chart build() {
			return new Chart(JsonUtil.getJsonStringForChart(pieChartModelList), JsonUtil.getJsonStringForChart(pieChartDrilldownList));
		}
	}
}
