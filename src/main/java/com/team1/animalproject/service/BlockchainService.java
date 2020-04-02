package com.team1.animalproject.service;

import com.google.gson.Gson;
import com.team1.animalproject.blockchain.ipfs.IpfsService;
import com.team1.animalproject.blockchain.models.Transactions;
import com.team1.animalproject.blockchain.queries.AccountDetails;
import com.team1.animalproject.blockchain.queries.Payment;
import com.team1.animalproject.helpers.model.ChartDTO;
import com.team1.animalproject.model.Ilac;
import com.team1.animalproject.model.IpfsID;
import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.model.MedicalReport;
import com.team1.animalproject.model.MedicalReportMedicine;
import com.team1.animalproject.repository.IpfsIDRepository;
import io.ipfs.multihash.Multihash;
import org.apache.commons.compress.utils.Lists;
import org.primefaces.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.stellar.sdk.KeyPair;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class BlockchainService {

	@Autowired
	@Qualifier("ipfsIDRepository")
	private IpfsIDRepository ipfsIDRepository;

	@Autowired
	private UserService userService;
	@Autowired
	private IlacService ilacService;

	public List<String> readFile(String userId) throws IOException {

		Kullanici kullanici = userService.findById(userId).get();

		AccountDetails accountDetails = new AccountDetails(KeyPair.fromAccountId(kullanici.getKeyPair()));
		List<Transactions> transactions = accountDetails.getTransactionsFull(false);

		List<String> datas = Lists.newArrayList();

		if(org.apache.commons.collections.CollectionUtils.isNotEmpty(transactions)){
			List<String> ipfsUrls = transactions.stream().map(Transactions::getMemo).collect(Collectors.toList());

			List<IpfsID> ipfsIds = ipfsIDRepository.findByIdIn(ipfsUrls);
			if(!CollectionUtils.isEmpty(ipfsIds)){
				List<String> collect = ipfsIds.stream().map(IpfsID::getIpfsHash).collect(Collectors.toList());
				collect.stream().forEach(s -> {
					try{
						datas.add(IpfsService.getFile(s));
					} catch (IOException e){
					}
				});
			}
		}

		return datas;
	}

	public List<MedicalReport> getAll(String userId) throws IOException {
		List<String> authorityHashes = readFile(userId);
		List<MedicalReport> medicalReports = new ArrayList<>();
		authorityHashes.stream().forEach(s -> {
			JSONObject jsonObject = new JSONObject(s.substring(s.indexOf("{"), s.length()));
			if(jsonObject.has("olusturan")){
				String id = jsonObject.getString("olusturan");
				Kullanici kullanici = userService.findById(id).get();
				String olusturan = kullanici.getName() + " " + kullanici.getSurname();
				medicalReports.add(MedicalReport.builder()
						.id(jsonObject.getString("id"))
						.date(jsonObject.getString("date"))
						.tension(jsonObject.getString("tension"))
						.animalId(jsonObject.getString("animalId"))
						.olusturan(olusturan)
						.surgeryDescription(jsonObject.getString("surgeryDescription"))
						.esgal(jsonObject.getString("esgal"))
						.description(jsonObject.getString("description"))
						.reportNum(jsonObject.getString("reportNum"))
						.weight(jsonObject.getString("weight"))
						.pulse(jsonObject.getString("pulse"))
						.bodySugarLevel(jsonObject.getString("bodySugarLevel"))
						.build());
			}
		});
		return medicalReports;
	}

	public List<MedicalReport> getAllByReportId(String userId, String reportId) throws IOException {
		List<String> authorityHashes = readFile(userId);
		List<MedicalReport> medicalReports = new ArrayList<>();
		authorityHashes.stream().forEach(s -> {
			JSONObject jsonObject = new JSONObject(s.substring(s.indexOf("{"), s.length()));
			if(jsonObject.has("olusturan") && jsonObject.getString("id").equals(reportId)){
				medicalReports.add(MedicalReport.builder()
						.id(jsonObject.getString("id"))
						.date(jsonObject.getString("date"))
						.tension(jsonObject.getString("tension"))
						.animalId(jsonObject.getString("animalId"))
						.olusturan(jsonObject.getString("olusturan"))
						.surgeryDescription(jsonObject.getString("surgeryDescription"))
						.esgal(jsonObject.getString("esgal"))
						.description(jsonObject.getString("description"))
						.reportNum(jsonObject.getString("reportNum"))
						.weight(jsonObject.getString("weight"))
						.pulse(jsonObject.getString("pulse"))
						.bodySugarLevel(jsonObject.getString("bodySugarLevel"))
						.build());
			}
		});
		return medicalReports;
	}

	public List<MedicalReport> getAllByAnimalId(String animalId, String userId) throws IOException {
		List<String> authorityHashes = readFile(userId);
		List<MedicalReport> medicalReports = new ArrayList<>();
		authorityHashes.stream().forEach(s -> {
			JSONObject jsonObject = new JSONObject(s.substring(s.indexOf("{"), s.length()));
			if(jsonObject.has("animalId") && jsonObject.getString("animalId").equals(animalId)){
				String id = jsonObject.getString("olusturan");
				Kullanici kullanici = userService.findById(id).get();
				String olusturan = kullanici.getName() + " " + kullanici.getSurname();
				medicalReports.add(MedicalReport.builder()
						.id(jsonObject.getString("id"))
						.date(jsonObject.getString("date"))
						.tension(jsonObject.getString("tension"))
						.animalId(jsonObject.getString("animalId"))
						.olusturan(olusturan)
						.surgeryDescription(jsonObject.getString("surgeryDescription"))
						.esgal(jsonObject.getString("esgal"))
						.description(jsonObject.getString("description"))
						.reportNum(jsonObject.getString("reportNum"))
						.weight(jsonObject.getString("weight"))
						.pulse(jsonObject.getString("pulse"))
						.bodySugarLevel(jsonObject.getString("bodySugarLevel"))
						.build());
			}
		});
		return medicalReports;
	}

	public void transactionOlustur(MedicalReport medicalReport) throws IOException {
		List<MedicalReport> all = getAll(medicalReport.getOlusturan());
		medicalReport.setId(UUID.randomUUID().toString());
		medicalReport.setReportNum(all.size() + 1 + "");
		Gson gson = new Gson();
		String jsonStr = gson.toJson(medicalReport);
		String id = UUID.randomUUID().toString();
		PrintWriter out = new PrintWriter(id);
		out.println(jsonStr);
		out.close();
		String olusturan = medicalReport.getOlusturan();
		Kullanici kullanici = userService.findById(olusturan).get();
		String keyPair = kullanici.getKeyPair();

		Multihash saved = IpfsService.save(id);
		String ipfsId = UUID.randomUUID().toString().substring(0, 20);

		ipfsIDRepository.save(IpfsID.builder().id(ipfsId).ipfsHash(saved.toBase58()).build());

		if(isNotBlank(keyPair)){
			Payment payment = new Payment(KeyPair.fromAccountId(keyPair));
			payment.send(ipfsId, KeyPair.fromAccountId(keyPair));
		}
	}

	public void transactionOlustur(MedicalReportMedicine medicalReportMedicine, String kullaniciId) throws IOException {
		Gson gson = new Gson();
		String jsonStr = gson.toJson(medicalReportMedicine);
		String id = UUID.randomUUID().toString();
		PrintWriter out = new PrintWriter(id);
		out.println(jsonStr);
		out.close();
		Kullanici kullanici = userService.findById(kullaniciId).get();
		String keyPair = kullanici.getKeyPair();

		Multihash saved = IpfsService.save(id);

		String ipfsId = UUID.randomUUID().toString().substring(0, 20);

		ipfsIDRepository.save(IpfsID.builder().id(ipfsId).ipfsHash(saved.toBase58()).build());

		if(isNotBlank(keyPair)){
			Payment payment = new Payment(KeyPair.fromAccountId(keyPair));
			payment.send(ipfsId, KeyPair.fromAccountId(keyPair));
		}
	}

	public List<MedicalReportMedicine> ilaclariGetir(String medicalReportId, String userId) throws IOException {
		List<String> authorityHashes = readFile(userId);
		List<MedicalReportMedicine> medicalReportMedicines = new ArrayList<>();
		authorityHashes.stream().forEach(s -> {
			String jsonObjects = new String(s);
			JSONObject jsonObject = new JSONObject(s.substring(s.indexOf("{"), s.length()));
			if(jsonObject.has("medicalReportId") && jsonObject.getString("medicalReportId").equals(medicalReportId)){
				Ilac ilac = ilacService.findById(jsonObject.getString("ilacId"));
				medicalReportMedicines.add(MedicalReportMedicine.builder()
						.ilacId(jsonObject.getString("ilacId"))
						.ilacAd(ilac.getName())
						.adet(jsonObject.getString("adet"))
						.kullanimSekli(jsonObject.getString("kullanimSekli"))
						.medicalReportId(jsonObject.getString("medicalReportId"))
						.build());
			}
		});
		return medicalReportMedicines;
	}

	public List<ChartDTO> enCokYazilanIlaclar(String userId) throws IOException {

		List<MedicalReport> medicalReports = getAll(userId);
		List<MedicalReportMedicine> medicalReportMedicines = Lists.newArrayList();
		if(!CollectionUtils.isEmpty(medicalReports)){
			medicalReports.stream().forEach(medicalReport -> {
				try{
					medicalReportMedicines.addAll(ilaclariGetir(medicalReport.getId(), userId));
				} catch (IOException e){
					e.printStackTrace();
				}
			});
		}

		Map<String, Integer> ilacMiktar = new HashMap<>();
		Map<String, List<String>> ilacRapor = new HashMap<>();

		List<ChartDTO> chartDTOs = Lists.newArrayList();

		if(!CollectionUtils.isEmpty(medicalReportMedicines)){
			medicalReportMedicines.stream().forEach(medicalReportMedicine -> {
				if(ilacMiktar.containsKey(medicalReportMedicine.getIlacAd())){
					Integer miktar = ilacMiktar.get(medicalReportMedicine.getIlacAd());
					miktar = miktar + Integer.valueOf(medicalReportMedicine.getAdet());
					ilacMiktar.put(medicalReportMedicine.getIlacAd(), miktar);
				} else {
					ilacMiktar.put(medicalReportMedicine.getIlacAd(), Integer.valueOf(medicalReportMedicine.getAdet()));
				}

				if(ilacRapor.containsKey(medicalReportMedicine.getIlacAd())){
					List<String> bilgiler = ilacRapor.get(medicalReportMedicine.getIlacAd());
					List<MedicalReport> raporlar = null;
					try{
						raporlar = getAllByReportId(userId, medicalReportMedicine.getMedicalReportId());
					} catch (IOException e){
						e.printStackTrace();
					}
					raporlar.stream().forEach(medicalReport -> {
						Kullanici kullanici = userService.findById(medicalReport.getOlusturan()).get();
						bilgiler.add("Veteriner: " + kullanici.getName() + " " + kullanici.getSurname() + "- Rapor Tarihi: " + medicalReport.getDate());
					});
					ilacRapor.put(medicalReportMedicine.getIlacAd(), bilgiler);
				} else {
					List<MedicalReport> raporlar = null;
					try{
						raporlar = getAllByReportId(userId, medicalReportMedicine.getMedicalReportId());
					} catch (IOException e){
						e.printStackTrace();
					}
					ilacRapor.put(medicalReportMedicine.getIlacAd(), raporlar.stream()
							.map(medicalReport -> "Veteriner: " + userService.findById(medicalReport.getOlusturan())
									.get()
									.getName()
									.concat(" ".concat(userService.findById(medicalReport.getOlusturan()).get().getSurname())) + "- Rapor Tarihi: " + medicalReport.getDate())
							.collect(Collectors.toList()));
				}
			});
		}

		ilacMiktar.forEach((s, integer) -> {
			List<ChartDTO> raporlar = Lists.newArrayList();
			ilacRapor.get(s).stream().forEach(s1 -> {
				raporlar.add(ChartDTO.builder().name(s1).value(integer).build());
			});
			chartDTOs.add(ChartDTO.builder().name(s).value(Integer.valueOf(integer)).drillDownList(raporlar).build());
		});

		return chartDTOs;
	}
}
