/*
 * MIT License
 *
 * Copyright (c) [2018] [Mark Tripoli (Triippz)]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.team1.animalproject.blockchain.queries;

import com.team1.animalproject.blockchain.models.Assets;
import com.team1.animalproject.blockchain.utils.Connections;
import org.stellar.sdk.Asset;
import org.stellar.sdk.ChangeTrustOperation;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Server;
import org.stellar.sdk.responses.AssetResponse;
import org.stellar.sdk.responses.Page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class ChangeTrust {

	private KeyPair pair;

	public ChangeTrust(KeyPair pair) {
		this.pair = pair;
	}

	public void changeTrust(boolean isMainNet, Asset asset, String limit) throws IOException {
		Server server = Connections.getServer(isMainNet);
		Page<AssetResponse> page = server.assets().execute();

		for(AssetResponse response : page.getRecords()){
			//response.getAsset().
		}

		ChangeTrustOperation operation = new ChangeTrustOperation.Builder(asset, limit).setSourceAccount(pair).build();
	}

	/**
	 * Used to get all assets and their issuer
	 *
	 * @param isMainNet to determine which horizon server to hit
	 * @return Map k=issuer v=assetcode of all assets
	 */
	public TreeMap<String, String> getAllAssets(boolean isMainNet) {
		return AssetQuery.getAllAssets(isMainNet);
	}

	/**
	 * Returns an object of type Asset given the assetcode
	 *
	 * @param isMainNet to determine which horizon server to hit
	 * @param assetCode short-hand name of the asset
	 * @return Asset Object
	 * @throws Exception thrown if there is more than one asset with the given asset code
	 */
	private Assets getAsset(boolean isMainNet, String assetCode) throws Exception {
		return AssetQuery.getAssetInfo(isMainNet, assetCode);
	}

	/**
	 * Used when there are multiple assets of the same name but with different issuers
	 *
	 * @param isMainNet to determine which horizon server to hit
	 * @param assetCode short-hand name of the asset
	 * @return an ArrayList of all applicable assets
	 */
	private ArrayList<Assets> getAssets(boolean isMainNet, String assetCode) {
		return AssetQuery.getAssetInfoArr(isMainNet, assetCode);
	}

	/**
	 * Get all assets of the given asset code,
	 *
	 * @param isMainNet to determine which horizon server to hit
	 * @param assetCode short-hand name of the asset
	 * @param issuer    the issuer's key
	 * @return Asset Object
	 */
	private Assets getAssetByIssuer(boolean isMainNet, String assetCode, String issuer) throws Exception {
		return AssetQuery.getAssetInfo(isMainNet, assetCode, issuer);
	}

	/**
	 * Used to get all assets which are issued by the given issuer
	 *
	 * @param isMainNet to determine which horizon server to hit
	 * @param issuer    the issuer's key
	 * @return ArrayList of Asset Objects
	 */
	private ArrayList<Assets> getIssuersAssets(boolean isMainNet, String issuer) {
		return AssetQuery.getIssuerAssets(isMainNet, issuer);
	}

	/**
	 * Returns various responses of assets and their issuers
	 * depending on what the user enters
	 *
	 * @param isMainNet to determine which horizon server to hit
	 * @param asset     short-hand name of the asset
	 * @param issuer    the issuer's key
	 * @return a String response (parsed) from Horizon
	 */

}