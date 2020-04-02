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

import com.team1.animalproject.blockchain.exception.SubmitTransactionException;
import com.team1.animalproject.blockchain.utils.Connections;
import org.stellar.sdk.AccountFlag;
import org.stellar.sdk.Asset;
import org.stellar.sdk.AssetTypeCreditAlphaNum;
import org.stellar.sdk.AssetTypeCreditAlphaNum12;
import org.stellar.sdk.AssetTypeCreditAlphaNum4;
import org.stellar.sdk.ChangeTrustOperation;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.PaymentOperation;
import org.stellar.sdk.Server;
import org.stellar.sdk.SetOptionsOperation;
import org.stellar.sdk.Transaction;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.SubmitTransactionResponse;

import java.io.IOException;

public class CreateAsset {

	private KeyPair srcPair;

	public CreateAsset(KeyPair srcPair) {
		this.srcPair = srcPair;
	}

	public KeyPair getSrcPair() {
		return srcPair;
	}

	private Transaction setMetaData(AccountResponse sourceAccount, String domain) {
		return new Transaction.Builder(sourceAccount).addOperation(new SetOptionsOperation.Builder().setHomeDomain(domain).build()).build();
	}

	private Transaction setAuthorizationFlags(boolean authReq, boolean authRevoc, AccountResponse sourceAccount) {

		if(authReq && authRevoc) return new Transaction.Builder(sourceAccount).addOperation(
				new SetOptionsOperation.Builder().setSetFlags(AccountFlag.AUTH_REQUIRED_FLAG.getValue() | AccountFlag.AUTH_REVOCABLE_FLAG.getValue()).build()).build();
		else if(authReq && !authRevoc)
			return new Transaction.Builder(sourceAccount).addOperation(new SetOptionsOperation.Builder().setSetFlags(AccountFlag.AUTH_REQUIRED_FLAG.getValue()).build()).build();
		else if(!authReq && authRevoc)
			return new Transaction.Builder(sourceAccount).addOperation(new SetOptionsOperation.Builder().setSetFlags(AccountFlag.AUTH_REVOCABLE_FLAG.getValue()).build()).build();
		else return new Transaction.Builder(sourceAccount).addOperation(new SetOptionsOperation.Builder().build()).build();
	}

	private Asset createAlphaNum4(String assetCode, KeyPair issuer) {
		return AssetTypeCreditAlphaNum4.createNonNativeAsset(assetCode, issuer);
	}

	private Asset createAlphaNum(String assetCode, KeyPair issuer) {
		return AssetTypeCreditAlphaNum.createNonNativeAsset(assetCode, issuer);
	}

	private Asset createAlphaNum12(String assetCode, KeyPair issuer) {
		return AssetTypeCreditAlphaNum12.createNonNativeAsset(assetCode, issuer);
	}
}
