package com.team1.animalproject.helpers.model;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PieChartModelBuilder {

	private ChartData chartData;
	private PieChartDataSet dataSet;

	public PieChartModelBuilder() {
		dataSet = new PieChartDataSet();
		dataSet.setData(new ArrayList<>());
		dataSet.setBackgroundColor(new ArrayList<>());

		chartData = new ChartData();
		chartData.setLabels(new ArrayList<String>());
		chartData.getDataSet().add(dataSet);
	}

	public PieChartModelBuilder add(String label, Number value) {
		return add(label, value, generateColor());
	}

	public static String generateColor() {
		final char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		char[] s = new char[7];
		int n = new Random().nextInt(0x1000000);
		s[0] = '#';
		for(int i = 1; i < 7; i++){
			s[i] = hex[n & 0xf];
			n >>= 4;
		}
		return new String(s);
	}

	public PieChartModelBuilder add(String label, Number value, String color) {
		dataSet.getBackgroundColor().add(color);
		dataSet.getData().add(value);
		((List) chartData.getLabels()).add(label);
		return this;
	}

	public PieChartModel build() {
		PieChartModel pieChartModel = new PieChartModel();
		pieChartModel.setData(chartData);
		return pieChartModel;
	}
}
