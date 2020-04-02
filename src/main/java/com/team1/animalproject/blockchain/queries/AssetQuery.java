package com.team1.animalproject.blockchain.queries;

import com.team1.animalproject.blockchain.models.Assets;
import com.team1.animalproject.blockchain.utils.Format;
import com.team1.animalproject.blockchain.utils.HorizonErrors;
import com.team1.animalproject.blockchain.utils.Props;
import com.team1.animalproject.blockchain.utils.Resolve;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeMap;

public class AssetQuery {

	private final static String EMBEDED = "_embedded";
	private final static String RECORDS = "records";
	private final static String LINKS = "_links";
	private final static String FLAGS = "flags";
	private final static String ASSET = "asset_code";
	private final static String NEXT = "next";
	private final static String HREF = "href";
	private final static String ISSUER = "asset_issuer";
	private final static String TOTAL_SUPP = "amount";
	private final static String ASSET_TYPE = "asset_type";
	private final static String PAGING_TOK = "paging_token";
	private final static String NUM_ACCOUNTS = "num_accounts";
	private final static String AUTH_REQ = "auth_required";
	private final static String AUTH_REVOC = "auth_revocable";

	public static TreeMap<String, String> getAllAssets(boolean isMainNet) {
		String json = null;
		TreeMap<String, String> assets = new TreeMap<>();
		boolean firstPass = true;
		String cursor = "";

		while(true){
			try{
				if(firstPass){
					if(isMainNet)
						json = queryHorizon(Props.getProperty(Props.LUNAR_HELPER_PROPS, Props.MAIN_NET) + Format.parseCursorUrl(Props.getProperty(Props.LUNAR_HELPER_PROPS, Props.ALL_ASSETS), cursor));
					else
						json = queryHorizon(Props.getProperty(Props.LUNAR_HELPER_PROPS, Props.TEST_NET) + Format.parseCursorUrl(Props.getProperty(Props.LUNAR_HELPER_PROPS, Props.ALL_ASSETS), cursor));

					firstPass = false;
					//json = queryHorizon ( cursor );
				} else {
					json = queryHorizon(cursor);
				}

			} catch (IOException e){
				e.printStackTrace();
			}

			JSONObject jsonObject = new JSONObject(Objects.requireNonNull(json));
			JSONObject embeded = jsonObject.getJSONObject(EMBEDED);
			JSONArray records = embeded.getJSONArray(RECORDS);

			/* make sure the record is not empty, if empty, we are done */
			if(records.length() == 0) break;

			for(int i = 0; i < records.length(); i++){
				JSONObject newRecord = records.getJSONObject(i);

                /* looks like horizon does not automatically delete assets from their db
               because of this, it changes the asset code to "REMOVED"
               So once we hit "REMOVED" we will break out of this loop to kill the clutter
               and save some time.
                */
				if(!newRecord.getString(ASSET).equalsIgnoreCase("REMOVE")){
					assets.put(newRecord.getString(ISSUER), newRecord.getString(ASSET));
				}
			}
			/* get the cursor for the next iteration */
			cursor = jsonObject.getJSONObject(LINKS).getJSONObject(NEXT).getString(HREF);

		}
		return assets;
	}

	@SuppressWarnings ("Duplicates")
	public static Assets getAssetInfo(boolean isMainNet, String assetCode) throws Exception {
		Assets asset = null;
		String jsonString;
		String URL;

		if(isMainNet){
			URL = Props.getProperty(Props.LUNAR_HELPER_PROPS, Props.ASSET_C);
			if(URL != null) URL = Props.getProperty(Props.LUNAR_HELPER_PROPS, Props.MAIN_NET) + String.format(URL, assetCode, "");
		} else {
			URL = Props.getProperty(Props.LUNAR_HELPER_PROPS, Props.ASSET_C);
			if(URL != null) URL = Props.getProperty(Props.LUNAR_HELPER_PROPS, Props.TEST_NET) + String.format(URL, assetCode, "");
		}

		try{
			jsonString = queryHorizon(URL);

			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject embeded = jsonObject.getJSONObject(EMBEDED);
			JSONArray records = embeded.getJSONArray(RECORDS);

			try{
				records.getJSONObject(1);
				throw new Exception("1");
			} catch (Exception e){
				System.out.println(records);
				//throw new Exception("1");
			}

			JSONObject newRecord = records.getJSONObject(0);

			asset = new Assets(newRecord.getString(ASSET), newRecord.getString(TOTAL_SUPP), newRecord.getString(ASSET_TYPE), newRecord.getString(PAGING_TOK), newRecord.getString(ISSUER),
					newRecord.getInt(NUM_ACCOUNTS), newRecord.getJSONObject(FLAGS).getBoolean(AUTH_REQ), newRecord.getJSONObject(FLAGS).getBoolean(AUTH_REVOC));
		} catch (IOException e){
			e.printStackTrace();
		}
		return asset;
	}

	@SuppressWarnings ("Duplicates")
	public static ArrayList<Assets> getAssetInfoArr(boolean isMainNet, String assetCode) {
		ArrayList<Assets> asset = new ArrayList<>();
		String json = null;
		String URL;
		String cursor = "";
		boolean firstPass = true;

		while(true){
			try{
				if(firstPass){
					if(isMainNet){
						URL = Props.getProperty(Props.LUNAR_HELPER_PROPS, Props.ASSET_C);
						if(URL != null) json = queryHorizon(Props.getProperty(Props.LUNAR_HELPER_PROPS, Props.MAIN_NET) + String.format(URL, assetCode, cursor));
					} else {
						URL = Props.getProperty(Props.LUNAR_HELPER_PROPS, Props.ASSET_C);
						if(URL != null) json = queryHorizon(Props.getProperty(Props.LUNAR_HELPER_PROPS, Props.TEST_NET) + String.format(URL, assetCode, cursor));
					}
					firstPass = false;
				} else {
					json = queryHorizon(cursor);
				}
			} catch (IOException e){
				e.printStackTrace();
			}

			JSONObject jsonObject = new JSONObject(Objects.requireNonNull(json));
			JSONObject embeded = jsonObject.getJSONObject(EMBEDED);
			JSONArray records = embeded.getJSONArray(RECORDS);

			/* make sure the record is not empty, if empty, we are done */
			if(records.length() == 0) break;

			for(int i = 0; i < records.length(); i++){
				JSONObject newRecord = records.getJSONObject(i);

				asset.add(new Assets(newRecord.getString(ASSET), newRecord.getString(TOTAL_SUPP), newRecord.getString(ASSET_TYPE), newRecord.getString(PAGING_TOK), newRecord.getString(ISSUER),
						newRecord.getInt(NUM_ACCOUNTS), newRecord.getJSONObject(FLAGS).getBoolean(AUTH_REQ), newRecord.getJSONObject(FLAGS).getBoolean(AUTH_REVOC)));
			}
			/* get the cursor for the next iteration */
			cursor = jsonObject.getJSONObject(LINKS).getJSONObject(NEXT).getString(HREF);
		}
		return asset;
	}

	@SuppressWarnings ("Duplicates")
	public static Assets getAssetInfo(boolean isMainNet, String assetCode, String issuer) throws Exception {
		Assets assets;
		String jsonString;
		String URL;

		if(isMainNet){
			URL = Props.getProperty(Props.LUNAR_HELPER_PROPS, Props.ASSET_CI);
			if(URL != null) URL = Props.getProperty(Props.LUNAR_HELPER_PROPS, Props.MAIN_NET) + String.format(URL, assetCode, issuer);
		} else {
			URL = Props.getProperty(Props.LUNAR_HELPER_PROPS, Props.ASSET_CI);
			if(URL != null) URL = Props.getProperty(Props.LUNAR_HELPER_PROPS, Props.TEST_NET) + String.format(URL, assetCode, issuer);
		}

		try{
			jsonString = queryHorizon(URL);

			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject embeded = jsonObject.getJSONObject(EMBEDED);
			JSONArray records = embeded.getJSONArray(RECORDS);

			JSONObject newRecord = records.getJSONObject(0);

			assets = new Assets(newRecord.getString(ASSET), newRecord.getString(TOTAL_SUPP), newRecord.getString(ASSET_TYPE), newRecord.getString(PAGING_TOK), newRecord.getString(ISSUER),
					newRecord.getInt(NUM_ACCOUNTS), newRecord.getJSONObject(FLAGS).getBoolean(AUTH_REQ), newRecord.getJSONObject(FLAGS).getBoolean(AUTH_REVOC));

		} catch (IOException e){
			//            lunHelpLogger.error( e.getMessage() );
			throw new Exception(HorizonErrors.severReturnError(e.getMessage()));
		}
		return assets;
	}

	@SuppressWarnings ("Duplicates")
	public static ArrayList<Assets> getIssuerAssets(boolean isMainNet, String issuer) {
		ArrayList<Assets> asset = new ArrayList<>();
		String jsonString;
		String URL;

		if(isMainNet){
			URL = Props.getProperty(Props.LUNAR_HELPER_PROPS, Props.ASSET_I);
			if(URL != null) URL = Props.getProperty(Props.LUNAR_HELPER_PROPS, Props.MAIN_NET) + String.format(URL, issuer);
		} else {
			URL = Props.getProperty(Props.LUNAR_HELPER_PROPS, Props.ASSET_I);
			if(URL != null) URL = Props.getProperty(Props.LUNAR_HELPER_PROPS, Props.TEST_NET) + String.format(URL, issuer);
		}

		try{
			jsonString = queryHorizon(URL);

			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject embeded = jsonObject.getJSONObject(EMBEDED);
			JSONArray records = embeded.getJSONArray(RECORDS);

			for(int i = 0; i < records.length(); i++){
				JSONObject newRecord = records.getJSONObject(i);

				asset.add(new Assets(newRecord.getString(ASSET), newRecord.getString(TOTAL_SUPP), newRecord.getString(ASSET_TYPE), newRecord.getString(PAGING_TOK), newRecord.getString(ISSUER),
						newRecord.getInt(NUM_ACCOUNTS), newRecord.getJSONObject(FLAGS).getBoolean(AUTH_REQ), newRecord.getJSONObject(FLAGS).getBoolean(AUTH_REVOC)));
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		return asset;
	}

	private static String queryHorizon(String URL) throws IOException {
		return Resolve.getJSON(URL);
	}
}
