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

		private List<ChartData> gbsPieChartModelList;
		private List<ChartDrilldownData> gbsPieChartDrilldownList;

		public Builder() {
			gbsPieChartModelList = new ArrayList<>();
			gbsPieChartDrilldownList = new ArrayList<>();
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

		public Builder add(List<ChartDTO> gbsChartDTOs) {
			(gbsChartDTOs).stream().forEach(gbsChartDTO -> {
				if(isNotEmpty(gbsChartDTO.getDrillDownList())){
					add(gbsChartDTO.getName(), gbsChartDTO.getValue(), gbsChartDTO.getBirim(), gbsChartDTO.getColor(), gbsChartDTO.getDrillDownList());
				} else {
					add(gbsChartDTO.getName(), gbsChartDTO.getValue(), gbsChartDTO.getBirim(), gbsChartDTO.getColor());
				}
			});
			return this;
		}

		public Builder add(ChartDTO gbsChartDTO) {
			return add(gbsChartDTO.getName(), gbsChartDTO.getValue(), gbsChartDTO.getColor(), gbsChartDTO.getDrillDownList());
		}

		public Builder add(ChartData gbsPieChartModel, List<ChartDTO> gbsChartList) {
			ChartDrilldownData gbsPieChartDrilldown = ChartDrilldownData.builder().name(gbsPieChartModel.getName()).build();

			(gbsChartList).stream().forEach(gbsChartDTO -> {
				gbsPieChartDrilldown.getData().add(ChartData.builder().color(gbsChartDTO.getColor()).name(gbsChartDTO.getName()).y(gbsChartDTO.getValue()).birim(gbsChartDTO.getBirim()).build());
			});

			return add(gbsPieChartModel, gbsPieChartDrilldown);
		}

		public Builder add(ChartData gbsPieChartModel) {
			gbsPieChartModelList.add(gbsPieChartModel);
			return this;
		}

		public Builder add(ChartData gbsPieChartModel, ChartDrilldownData gbsPieChartDrilldown) {
			gbsPieChartModelList.add(gbsPieChartModel);
			gbsPieChartDrilldown.setId(gbsPieChartModel.getDrilldown());
			gbsPieChartDrilldownList.add(gbsPieChartDrilldown);
			return this;
		}

		public Chart build() {
			return new Chart(JsonUtil.getJsonStringForChart(gbsPieChartModelList), JsonUtil.getJsonStringForChart(gbsPieChartDrilldownList));
		}
	}
}
