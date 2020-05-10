package com.team1.animalproject.service;

import com.google.gson.Gson;
import com.team1.animalproject.blockchain.ipfs.IpfsService;
import com.team1.animalproject.blockchain.models.Transactions;
import com.team1.animalproject.blockchain.queries.AccountDetails;
import com.team1.animalproject.blockchain.queries.Payment;
import com.team1.animalproject.helpers.model.ChartDTO;
import com.team1.animalproject.model.AnimalTarihce;
import com.team1.animalproject.model.BlockchainExplorer;
import com.team1.animalproject.model.Ilac;
import com.team1.animalproject.model.IpfsID;
import com.team1.animalproject.model.Kullanici;
import com.team1.animalproject.model.MedicalReport;
import com.team1.animalproject.model.MedicalReportMedicine;
import com.team1.animalproject.repository.IpfsIDRepository;
import com.team1.animalproject.view.utils.DateUtil;
import io.ipfs.multihash.Multihash;
import org.apache.commons.compress.utils.Lists;
import org.primefaces.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.MemoText;
import org.stellar.sdk.Network;
import org.stellar.sdk.Server;
import org.stellar.sdk.requests.RequestBuilder;
import org.stellar.sdk.responses.Page;
import org.stellar.sdk.responses.TransactionResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@SuppressWarnings ("ALL")
@Service
public class BlockchainService {

	@Autowired
	@Qualifier ("ipfsIDRepository")
	private IpfsIDRepository ipfsIDRepository;

	@Autowired
	private UserService userService;
	@Autowired
	private IlacService ilacService;

	private final Map<String, String> ipfsHashes = new HashMap<>();
	private final Map<String, IpfsID> ipfsIds = new HashMap<>();
	private final Map<String, Kullanici> user = new HashMap<>();
	private final Map<String, List<MedicalReportMedicine>> medicalReportMedicineByReport = new HashMap<>();
	private final Map<String, List<MedicalReport>> medicalReportByAnimal = new HashMap<>();

	public List<BlockchainExplorer> explorer() throws IOException {
		Network.useTestNetwork();
		Server server = new Server("https://horizon-testnet.stellar.org");
		KeyPair destination = KeyPair.fromAccountId("GBOOWLO3IC7TOQFPIAA3ERSYGLG4EK2JYLMWMTCOUGJ7IQMC6EY6HFNU");

		Page<TransactionResponse> execute = server.transactions().order(RequestBuilder.Order.DESC).forAccount(destination).limit(200).execute();

		List<BlockchainExplorer> blockchainExplorers = Lists.newArrayList();

		execute.getRecords().forEach(transactionResponse -> {
			BlockchainExplorer blockchainExplorer = BlockchainExplorer.builder()
					.from(transactionResponse.getSourceAccount().getAccountId())
					.hash(transactionResponse.getHash())
					.zaman(transactionResponse.getCreatedAt().replaceAll("T", " ").replaceAll("Z", ""))
					.build();

			if(transactionResponse.getMemo() instanceof MemoText)
			ipfsIDRepository.findById(((MemoText) transactionResponse.getMemo()).getText()).ifPresent(ipfsID -> {
				blockchainExplorer.setMemo(ipfsID.getIpfsHash());
				blockchainExplorer.setZaman(DateUtil.dateAsString(ipfsID.getOlusmaTarihi()));
			});

			blockchainExplorers.add(blockchainExplorer);
		});

		while(execute.getRecords().size() != 0){
			TransactionResponse response = execute.getRecords().get(execute.getRecords().size() - 1);
			String pagingToken = response.getPagingToken();
			execute = server.transactions().order(RequestBuilder.Order.DESC).forAccount(destination).cursor(pagingToken).limit(200).execute();
			execute.getRecords().forEach(transactionResponse -> {
				BlockchainExplorer blockchainExplorer = BlockchainExplorer.builder()
						.from(transactionResponse.getSourceAccount().getAccountId())
						.hash(transactionResponse.getHash())
						.zaman(transactionResponse.getCreatedAt().replaceAll("T", " ").replaceAll("Z", ""))
						.build();

				if(transactionResponse.getMemo() instanceof MemoText)
					ipfsIDRepository.findById(((MemoText) transactionResponse.getMemo()).getText()).ifPresent(ipfsID -> {
					blockchainExplorer.setMemo(ipfsID.getIpfsHash());
					blockchainExplorer.setZaman(DateUtil.dateAsString(ipfsID.getOlusmaTarihi()));
				});

				blockchainExplorers.add(blockchainExplorer);
			});
		}

		return blockchainExplorers;
	}

	public List<String> readFile() throws IOException {

		AccountDetails accountDetails = new AccountDetails(KeyPair.fromAccountId("GBOOWLO3IC7TOQFPIAA3ERSYGLG4EK2JYLMWMTCOUGJ7IQMC6EY6HFNU"));
		List<Transactions> transactions = Lists.newArrayList();
		List<Transactions> toAdd = accountDetails.getTransactionsFull(false, null);

		while(!CollectionUtils.isEmpty(toAdd)){
			transactions.addAll(toAdd);
			toAdd = accountDetails.getTransactionsFull(false, toAdd.get(toAdd.size()-1).getPaging());
		}

		List<String> datas = Lists.newArrayList();

		if(org.apache.commons.collections.CollectionUtils.isNotEmpty(transactions)){
			List<String> ipfsUrls = transactions.stream().map(Transactions::getMemo).collect(Collectors.toList());

			ipfsUrls.forEach(ipfsId -> {
				IpfsID ipfsID;
				if(ipfsIds.containsKey(ipfsId)){
					ipfsID = ipfsIds.get(ipfsId);
					try{
						if(ipfsHashes.containsKey(ipfsID.getIpfsHash())){
							datas.add(ipfsHashes.get(ipfsID.getIpfsHash()));
						} else {
							String file = IpfsService.getFile(ipfsID.getIpfsHash());
							ipfsHashes.put(ipfsID.getIpfsHash(), file);
							datas.add(file);
						}
					} catch (IOException ignored){
					}
				} else {
					ipfsIDRepository.findById(ipfsId).ifPresent(ipfsidd -> {
						ipfsIds.put(ipfsId, ipfsidd);
						try{
							if(ipfsHashes.containsKey(ipfsidd.getIpfsHash())){
								datas.add(ipfsHashes.get(ipfsidd.getIpfsHash()));
							} else {
								String file = IpfsService.getFile(ipfsidd.getIpfsHash());
								ipfsHashes.put(ipfsidd.getIpfsHash(), file);
								datas.add(file);
							}
						} catch (IOException ignored){
						}
					});
				}
			});
		}

		return datas;
	}

	public List<MedicalReport> getAll() throws IOException {
		List<String> authorityHashes = readFile();
		List<MedicalReport> medicalReports = new ArrayList<>();
		authorityHashes.forEach(s -> {
			JSONObject jsonObject = new JSONObject(s.substring(s.indexOf("{")));
			if(jsonObject.has("olusturan")){
				String id = jsonObject.getString("olusturan");
				Kullanici kullanici;
				if(user.containsKey(id)){
					kullanici = user.get(id);
				} else {
					kullanici = userService.findById(id).get();
					user.put(id, kullanici);
				}
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

	public List<MedicalReport> getAllByReportId(String reportId) throws IOException {
		List<String> authorityHashes = readFile();
		List<MedicalReport> medicalReports = new ArrayList<>();
		authorityHashes.forEach(s -> {
			JSONObject jsonObject = new JSONObject(s.substring(s.indexOf("{")));
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

	public List<MedicalReport> getAllByAnimalId(String animalId) throws IOException {
		List<String> authorityHashes = readFile();
		List<MedicalReport> medicalReports = new ArrayList<>();
		authorityHashes.forEach(s -> {
			JSONObject jsonObject = new JSONObject(s.substring(s.indexOf("{")));
			if(!jsonObject.has("kimTarafindan") && jsonObject.has("animalId") && jsonObject.getString("animalId").equals(animalId)){
				String id = jsonObject.getString("olusturan");
				Kullanici kullanici;
				if(user.containsKey(id)){
					kullanici = user.get(id);
				} else {
					kullanici = userService.findById(id).get();
					user.put(id, kullanici);
				}
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
		List<MedicalReport> all = getAll();
		medicalReport.setId(UUID.randomUUID().toString());
		medicalReport.setReportNum(all.size() + 1 + "");
		Gson gson = new Gson();
		String jsonStr = gson.toJson(medicalReport);
		String id = UUID.randomUUID().toString();
		PrintWriter out = new PrintWriter(id);
		out.println(jsonStr);
		out.close();
		String olusturan = medicalReport.getOlusturan();
		Kullanici kullanici;
		if(user.containsKey(olusturan)){
			kullanici = user.get(olusturan);
		} else {
			kullanici = userService.findById(olusturan).get();
			user.put(id, kullanici);
		}
		String keyPair = kullanici.getKeyPair();

		Multihash saved = IpfsService.save(id);
		String ipfsId = UUID.randomUUID().toString().substring(0, 20);

		ipfsIDRepository.save(IpfsID.builder().id(ipfsId).ipfsHash(saved.toBase58()).build());

		if(isNotBlank(keyPair)){
			Payment payment = new Payment(KeyPair.fromSecretSeed(keyPair));
			payment.send(ipfsId, KeyPair.fromSecretSeed(keyPair));
		}
	}

	public void transactionOlustur(AnimalTarihce animalTarihce) throws IOException {
		Gson gson = new Gson();
		String jsonStr = gson.toJson(animalTarihce);
		String id = UUID.randomUUID().toString();
		PrintWriter out = new PrintWriter(id);
		out.println(jsonStr);
		out.close();
		String olusturan = animalTarihce.getKimTarafindan();
		Kullanici kullanici;
		if(user.containsKey(olusturan)){
			kullanici = user.get(olusturan);
		} else {
			kullanici = userService.findById(olusturan).get();
			user.put(olusturan, kullanici);
		}
		String keyPair = kullanici.getKeyPair();

		Multihash saved = IpfsService.save(id);
		String ipfsId = UUID.randomUUID().toString().substring(0, 20);

		ipfsIDRepository.save(IpfsID.builder().id(ipfsId).ipfsHash(saved.toBase58()).build());

		if(isNotBlank(keyPair)){
			Payment payment = new Payment(KeyPair.fromSecretSeed(keyPair));
			payment.send(ipfsId, KeyPair.fromSecretSeed(keyPair));
		}
	}

	public void transactionOlustur(MedicalReportMedicine medicalReportMedicine, String kullaniciId) throws IOException {
		Gson gson = new Gson();
		String jsonStr = gson.toJson(medicalReportMedicine);
		String id = UUID.randomUUID().toString();
		PrintWriter out = new PrintWriter(id);
		out.println(jsonStr);
		out.close();
		Kullanici kullanici;
		if(user.containsKey(kullaniciId)){
			kullanici = user.get(kullaniciId);
		} else {
			kullanici = userService.findById(kullaniciId).get();
			user.put(kullaniciId, kullanici);
		}
		String keyPair = kullanici.getKeyPair();

		Multihash saved = IpfsService.save(id);

		String ipfsId = UUID.randomUUID().toString().substring(0, 20);

		ipfsIDRepository.save(IpfsID.builder().id(ipfsId).ipfsHash(saved.toBase58()).build());

		if(isNotBlank(keyPair)){
			Payment payment = new Payment(KeyPair.fromSecretSeed(keyPair));
			payment.send(ipfsId, KeyPair.fromSecretSeed(keyPair));
		}
	}

	public List<MedicalReportMedicine> ilaclariGetir(String medicalReportId) throws IOException {
		List<String> authorityHashes = readFile();
		List<MedicalReportMedicine> medicalReportMedicines = new ArrayList<>();
		authorityHashes.forEach(s -> {
			String jsonObjects = s;
			JSONObject jsonObject = new JSONObject(s.substring(s.indexOf("{")));
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

	public List<AnimalTarihce> tarihceGetir(String animalId) throws IOException {
		List<String> authorityHashes = readFile();
		List<AnimalTarihce> animalTarihces = new ArrayList<>();
		authorityHashes.forEach(s -> {
			String jsonObjects = s;
			JSONObject jsonObject = new JSONObject(s.substring(s.indexOf("{")));
			if(jsonObject.has("kimTarafindan") && jsonObject.getString("animalId").equals(animalId)){
				animalTarihces.add(AnimalTarihce.builder()
						.animalId(jsonObject.getString("animalId"))
						.deger(jsonObject.getString("deger"))
						.yapilanIslem(jsonObject.getString("yapilanIslem"))
						.neZaman(jsonObject.getString("neZaman"))
						.kimTarafindan(jsonObject.getString("kimTarafindan"))
						.build());
			}
		});
		return animalTarihces;
	}

	public List<ChartDTO> enCokYazilanIlaclar(String userId) throws IOException {
		long start = System.currentTimeMillis();
		List<MedicalReport> medicalReports = getAll();
		List<MedicalReportMedicine> medicalReportMedicines = Lists.newArrayList();
		if(!CollectionUtils.isEmpty(medicalReports)){
			medicalReports.forEach(medicalReport -> {
				List<MedicalReportMedicine> medicalReportMedicines1;
				try{
					if(medicalReportMedicineByReport.containsKey(medicalReport.getId())){
						medicalReportMedicines1 = medicalReportMedicineByReport.get(medicalReport.getId());
					}else {
						medicalReportMedicines1 = ilaclariGetir(medicalReport.getId());
						medicalReportMedicineByReport.put(medicalReport.getId(), medicalReportMedicines1);
					}
					medicalReportMedicines.addAll(medicalReportMedicines1);
				} catch (IOException e){
					e.printStackTrace();
				}
			});
		}
		System.out.println("Ilaclari Doldurma Suresi: " + ((System.currentTimeMillis() - start) / 1000F));

		Map<String, Integer> ilacMiktar = new HashMap<>();
		Map<String, List<String>> ilacRapor = new HashMap<>();

		List<ChartDTO> chartDTOs = Lists.newArrayList();

		start = System.currentTimeMillis();

		if(!CollectionUtils.isEmpty(medicalReportMedicines)){
			medicalReportMedicines.forEach(medicalReportMedicine -> {
				if(ilacMiktar.containsKey(medicalReportMedicine.getIlacAd())){
					Integer miktar = ilacMiktar.get(medicalReportMedicine.getIlacAd());
					miktar = miktar + Integer.parseInt(medicalReportMedicine.getAdet());
					ilacMiktar.put(medicalReportMedicine.getIlacAd(), miktar);
				} else {
					ilacMiktar.put(medicalReportMedicine.getIlacAd(), Integer.valueOf(medicalReportMedicine.getAdet()));
				}

				if(ilacRapor.containsKey(medicalReportMedicine.getIlacAd())){
					List<String> bilgiler = ilacRapor.get(medicalReportMedicine.getIlacAd());
					List<MedicalReport> raporlar = null;
					try{
						if(medicalReportByAnimal.containsKey(medicalReportMedicine.getMedicalReportId())){
							raporlar = medicalReportByAnimal.get(medicalReportMedicine.getMedicalReportId());
						}else {
							raporlar = getAllByReportId(medicalReportMedicine.getMedicalReportId());
							medicalReportByAnimal.put(medicalReportMedicine.getMedicalReportId(), raporlar);
						}
					} catch (IOException e){
						e.printStackTrace();
					}
					raporlar.forEach(medicalReport -> {
						Kullanici kullanici;
						if(user.containsKey(medicalReport.getOlusturan())){
							kullanici = user.get(medicalReport.getOlusturan());
						} else {
							kullanici = userService.findById(medicalReport.getOlusturan()).get();
							user.put(medicalReport.getOlusturan(), kullanici);
						}
						bilgiler.add("Veteriner: " + kullanici.getName() + " " + kullanici.getSurname() + "- Rapor Tarihi: " + medicalReport.getDate());
					});
					ilacRapor.put(medicalReportMedicine.getIlacAd(), bilgiler);
				} else {
					List<MedicalReport> raporlar = null;
					try{
						if(medicalReportByAnimal.containsKey(medicalReportMedicine.getMedicalReportId())){
							raporlar = medicalReportByAnimal.get(medicalReportMedicine.getMedicalReportId());
						}else {
							raporlar = getAllByReportId(medicalReportMedicine.getMedicalReportId());
							medicalReportByAnimal.put(medicalReportMedicine.getMedicalReportId(), raporlar);
						}
					} catch (IOException e){
						e.printStackTrace();
					}

					ilacRapor.put(medicalReportMedicine.getIlacAd(), Objects.requireNonNull(raporlar).stream()
							.map(medicalReport -> "Veteriner: " + (user.containsKey(medicalReport.getOlusturan()) ? Optional.of(user.get(medicalReport.getOlusturan())) : userService.findById(medicalReport.getOlusturan()))
									.get()
									.getName()
									.concat(" ".concat((user.containsKey(medicalReport.getOlusturan()) ? Optional.of(user.get(medicalReport.getOlusturan())) : userService.findById(medicalReport.getOlusturan())).get().getSurname())) + "- Rapor Tarihi: " + medicalReport.getDate())
							.collect(Collectors.toList()));
				}
			});
		}

		ilacMiktar.forEach((s, integer) -> {
			List<ChartDTO> raporlar = Lists.newArrayList();
			ilacRapor.get(s).forEach(s1 -> raporlar.add(ChartDTO.builder().name(s1).value(integer).build()));
			chartDTOs.add(ChartDTO.builder().name(s).value(integer).drillDownList(raporlar).build());
		});

		System.out.println("Ilac Chart Doldurma Suresi: " + ((System.currentTimeMillis() - start) / 1000F));

		return chartDTOs;
	}
}
