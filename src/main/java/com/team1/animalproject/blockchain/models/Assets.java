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

package com.team1.animalproject.blockchain.models;

public class Assets {

    /*
    Example flow for an account with AUTHORIZATION REQUIRED and AUTHORIZATION REVOCABLE enabled:

    1.User decides he/she wants to accept an asset
    2.User opens a trust line with this asset’s issuing account
    3.Issuer authorizes the user’s trustline
    4.User can accept and send the asset to whomever else has a trustline open with the issuer
    5.Issuer wants to freeze user’s access to asset
    6.Issuer deauthorizes user’s trustline
    7.User cannot send or accept this asset
     */

	private final String assetCode;
	private final String totalSupply;
	private final String assetType;
	private final String pagingToken;
	private final String issuer;
	private final int numAccounts;
	/* this parameter means the issuer must give authorization to the
	   account ( the person who wants to hold it ) which wants to hold the asset.
	   This allows the issuer to control who its "customers" are
	 */
	private final boolean authRequired;
	/* when set to TRUE, the issuer can freeze the asset on any account
		which holds it.
	 */
	private final boolean authRevocable;

	public Assets(String assetCode, String totalSupply, String assetType, String pagingToken, String issuer, int numAccounts, boolean authRequired, boolean authRevocable) {
		this.assetCode = assetCode;
		this.totalSupply = totalSupply;
		this.assetType = assetType;
		this.pagingToken = pagingToken;
		this.issuer = issuer;
		this.numAccounts = numAccounts;
		this.authRequired = authRequired;
		this.authRevocable = authRevocable;
	}

	public String getAssetCode() {
		return assetCode;
	}

	public String getTotalSupply() {
		return totalSupply;
	}

	public String getAssetType() {
		return assetType;
	}

	public String getPagingToken() {
		return pagingToken;
	}

	public String getIssuer() {
		return issuer;
	}

	public int getNumAccounts() {
		return numAccounts;
	}

	public boolean isAuthRequired() {
		return authRequired;
	}

	public boolean isAuthRevocable() {
		return authRevocable;
	}

	@Override
	public String toString() {
		return "Asset Code= " + assetCode + '\n' + "Total Supply= " + totalSupply + '\n' + "Asset Type= " + assetType + '\n' + "Paging Token= " + pagingToken + '\n' + "Issuer= " + issuer + '\n'
				+ "Number of Accounts= " + numAccounts + "\n" + "Authorization Required= " + authRequired + "\n" + "Authorization Revocable= " + authRevocable + "\n";
	}
}
