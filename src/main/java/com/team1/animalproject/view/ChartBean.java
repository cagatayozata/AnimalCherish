package com.team1.animalproject.view;

import com.team1.animalproject.blockchain.ipfs.IpfsService;
import com.team1.animalproject.helpers.model.Chart;
import com.team1.animalproject.service.AnimalService;
import com.team1.animalproject.service.BlockchainService;
import com.team1.animalproject.service.ShelterService;
import com.team1.animalproject.service.UserService;
import com.team1.animalproject.service.VetService;
import com.team1.animalproject.view.utils.DayEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings ("ALL")
@Component
@Scope ("view")
@EqualsAndHashCode ()
@Data
public class ChartBean extends BaseViewController<Chart> implements Serializable {

	private static final long serialVersionUID = -260254904661408724L;

	@Autowired
	private AnimalService animalService;
	@Autowired
	private VetService vetService;
	@Autowired
	private ShelterService shelterService;
	@Autowired
	private UserService userService;
	@Autowired
	private BlockchainService blockchainService;

	private BarChartModel barModel;
	private Chart enCokYazilanlarChart;
	private Chart kullanicilarChart;

	@Override
	@PostConstruct
	public void viewOlustur() {
		super.altVerileriVeIlkEkraniHazirla();
		createBarModel();
	}

	@Override
	public void ilkEkraniHazirla() {

	}

	public void createBarModel() {
		barModel = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet barDataSet = new BarChartDataSet();
		barDataSet.setLabel("Son 7 Gün Eklenmiş Hayvan Sayısı");

		Map<Integer, Long> veri = animalService.sonYediGunIcinEklenenHayvanVerileriniGetir();

		List<Number> values = new ArrayList<>();
		List<String> labels = new ArrayList<>();

		for(Integer day : veri.keySet()){
			labels.add(DayEnum.getById(day).get().getTextMessageKey());
			values.add(veri.get(day));
		}

		barDataSet.setData(values);
		data.setLabels(labels);

		List<String> bgColor = new ArrayList<>();
		bgColor.add("rgba(255, 99, 132, 0.2)");
		bgColor.add("rgba(255, 159, 64, 0.2)");
		bgColor.add("rgba(255, 205, 86, 0.2)");
		bgColor.add("rgba(75, 192, 192, 0.2)");
		bgColor.add("rgba(54, 162, 235, 0.2)");
		bgColor.add("rgba(153, 102, 255, 0.2)");
		bgColor.add("rgba(201, 203, 207, 0.2)");
		barDataSet.setBackgroundColor(bgColor);

		List<String> borderColor = new ArrayList<>();
		borderColor.add("rgb(255, 99, 132)");
		borderColor.add("rgb(255, 159, 64)");
		borderColor.add("rgb(255, 205, 86)");
		borderColor.add("rgb(75, 192, 192)");
		borderColor.add("rgb(54, 162, 235)");
		borderColor.add("rgb(153, 102, 255)");
		borderColor.add("rgb(201, 203, 207)");
		barDataSet.setBorderColor(borderColor);
		barDataSet.setBorderWidth(1);

		data.addChartDataSet(barDataSet);

		barModel.setData(data);

		//Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		ticks.setBeginAtZero(true);
		linearAxes.setTicks(ticks);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);

		Title title = new Title();
		title.setDisplay(true);
		title.setText("Hayvan Bilgi Sistemi");
		options.setTitle(title);

		Legend legend = new Legend();
		legend.setDisplay(true);
		legend.setPosition("top");
		LegendLabel legendLabels = new LegendLabel();
		legendLabels.setFontStyle("bold");
		legendLabels.setFontColor("#2980B9");
		legendLabels.setFontSize(24);
		legend.setLabels(legendLabels);
		options.setLegend(legend);

		barModel.setOptions(options);
	}

	public void itemSelect(ItemSelectEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected", "Item Index: " + event.getItemIndex() + ", DataSet Index:" + event.getDataSetIndex());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public long toplamVeterinerSayisi() {
		return vetService.toplamSayi();
	}

	public long toplamHayvanSayisi() {
		return animalService.toplamSayi();
	}

	public long toplamBarinakSayisi() {
		return shelterService.toplamSayi();
	}

	public long toplamKullaniciSayisi() {
		return userService.toplamSayi();
	}

	public String saglikRaporuIstatistikleri() throws IOException {
		enCokYazilanlarChart = new Chart.Builder().add(blockchainService.enCokYazilanIlaclar(kullaniciPrincipal.getId())).build();
		kullanicilarChart = new Chart.Builder().add(userService.kullanicilarNerede()).build();
		return "#";
	}

	public int blockchainDurumu() {
		return blockchainService.ipfsIds.size();
	}

	public int ipfsDurumu() {
		return blockchainService.ipfsIds.size();
	}

	public boolean blockchainCheck() {
		return blockchainService.servisAktifMi();
	}

	public boolean ipfsCheck() throws IOException {
		return IpfsService.aktifMi();
	}
}