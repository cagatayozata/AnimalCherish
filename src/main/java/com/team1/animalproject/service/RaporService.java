package com.team1.animalproject.service;

import com.google.common.collect.Lists;
import com.team1.animalproject.auth.Constants;
import com.team1.animalproject.model.Animal;
import com.team1.animalproject.model.Cins;
import com.team1.animalproject.model.MedicalReport;
import com.team1.animalproject.model.MedicalReportMedicine;
import com.team1.animalproject.model.Tur;
import com.team1.animalproject.model.rapor.IlacRapor;
import com.team1.animalproject.model.rapor.IlacRecetesi;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRProperties;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RaporService {

	@Autowired
	private VetService vetService;
	@Autowired
	private AnimalService animalService;
	@Autowired
	private BlockchainService blockchainService;
	@Autowired
	private IlacService ilacService;
	@Autowired
	private UserService userService;
	@Autowired
	private TurService turService;
	@Autowired
	private CinsService cinsService;

	String fileName = Constants.FILE_PATH + "rapor\\recete.jasper";
	String fileNameXML = Constants.FILE_PATH + "rapor\\recete.jrxml";

	String outFileNamePDF = Constants.FILE_PATH + "rapor\\s.pdf";
	Map hm = new HashMap();

	public void raporuOlustur(String userId, String medicalReportId) {
		try{

			File newFile = new File(fileNameXML);

			FileInputStream fileInputStream = new FileInputStream(newFile);
			String content = new String(IOUtils.toByteArray(fileInputStream), StandardCharsets.UTF_8);
			content = content.replace("\"+$P{SUBREPORT_EXT}", ".jasper\"");
			Files.write(newFile.toPath(), content.getBytes(StandardCharsets.UTF_8));
			fileInputStream.close();

			System.out.println("Filling report...");
			hm.put("", "Hello Report");
			//JasperReport manager=JasperManager.compileReport(fileName);
			MedicalReport medicalReport = blockchainService.getAllByReportId(userId, medicalReportId).stream().findFirst().get();
			List<MedicalReportMedicine> medicalReportMedicines = blockchainService.ilaclariGetir(medicalReportId);

			Animal animal = animalService.findByIdIn(Lists.newArrayList(medicalReport.getAnimalId())).get().stream().findFirst().get();

			List<IlacRapor> ilacRapolar = medicalReportMedicines.stream()
					.map(medicalReportMedicine -> IlacRapor.builder().urunAdi(medicalReportMedicine.getIlacId()).kullanimSekli(medicalReportMedicine.getKullanimSekli()).urunAdet(medicalReportMedicine.getAdet()).build())
					.collect(Collectors.toList());

			ilacRapolar.stream().forEach(ilacRapor -> {
				ilacRapor.setUrunAdi(ilacService.findById(ilacRapor.getUrunAdi()).getName());
				ilacRapor.setSistemNo(animal.getId());
			});

			Tur tur = turService.findById(animal.getTurId());
			Cins cins = cinsService.findById(animal.getCinsId());

			List<Object> reportDataSource = Lists.newArrayList(IlacRecetesi.builder().adres("").cinsiyet(animal.isCinsiyet() ? "Erkek" : "Dişi").diplomaNo("").esgal(medicalReport.getEsgal()).irk(cins.getName()).isletmeNo("").kupeNumarasi(animal.getId()).sahipAd("").sahipAdres("").seriNo("").sicilNo("").sifresi(
					RandomStringUtils.randomAlphabetic(5)).sinifi(RandomStringUtils.randomAlphabetic(7)).tedaviBaslangicTarihi(medicalReport.getDate()).teshis(medicalReport.getDescription()).tur(tur.getName()).veterinerAdi("").yas(5+"").ilacRaporList(ilacRapolar).build());

			JRProperties.setProperty("net.sf.jasperreports.default.pdf.encoding", "Cp1254");

			JasperReport jasperReport = JasperCompileManager.compileReport(fileNameXML);

			JRDataSource dataSource = new JRBeanCollectionDataSource(reportDataSource);

			hm.put("subReport", Constants.FILE_PATH + "rapor\\ilacbilgileri.jasper");
			hm.put("style", Constants.FILE_PATH + "rapor\\animal_style.jrtx");

			JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, dataSource);

			JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
			//parameter used for the destined file.
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileNamePDF);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			//export to .pdf
			exporter.exportReport();
			System.out.println("Created file: " + outFileNamePDF);
			System.out.println("Done!");

		} catch (JRException | IOException e){
			e.printStackTrace();
		}
	}

}
