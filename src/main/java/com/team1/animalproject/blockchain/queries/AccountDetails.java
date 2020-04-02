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

import com.team1.animalproject.blockchain.models.StellarAsset;
import com.team1.animalproject.blockchain.models.Transactions;
import com.team1.animalproject.blockchain.utils.Connections;
import com.team1.animalproject.blockchain.utils.Format;
import com.team1.animalproject.blockchain.utils.Resolve;
import org.apache.commons.compress.utils.Lists;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Server;
import org.stellar.sdk.requests.ErrorResponse;
import org.stellar.sdk.requests.PaymentsRequestBuilder;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.Page;
import org.stellar.sdk.responses.TransactionResponse;
import org.stellar.sdk.responses.operations.OperationResponse;
import org.stellar.sdk.responses.operations.PaymentOperationResponse;
import org.stellar.sdk.xdr.OperationType;
import org.stellar.sdk.xdr.Transaction;
import org.stellar.sdk.xdr.TransactionEnvelope;
import org.stellar.sdk.xdr.XdrDataInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class AccountDetails {

	private static String myToken = null;
	private KeyPair pair;

	public AccountDetails(KeyPair pair) {
		this.pair = pair;
	}

	@SuppressWarnings ("Duplicates")
	public String getNativeBalance(boolean isMainNet) {
		Server server = Connections.getServer(isMainNet);
		String balanceAmount = null;

		try{
			AccountResponse.Balance[] balances = server.accounts().account(pair).getBalances();
			for(AccountResponse.Balance balance : balances){
				if(balance.getAssetType().equalsIgnoreCase("native")){
					balanceAmount = balance.getBalance();

				}
			}
		} catch (IOException | ErrorResponse e){
			balanceAmount = "0";
		}

		return balanceAmount;
	}

	@SuppressWarnings("Duplicates")
	public List<Transactions> getTransactionsFull ( boolean isMainNet ) throws IOException {
		Server server = Connections.getServer(isMainNet);
		List<Transactions> transactions = Lists.newArrayList();
		ArrayList<TransactionResponse> transactionResponses = server.transactions().forAccount(pair).limit(100).execute().getRecords();

		for (TransactionResponse response : transactionResponses) {
			byte[] bytes = Base64.getDecoder().decode(response.getEnvelopeXdr());
			XdrDataInputStream in = new XdrDataInputStream(new ByteArrayInputStream(bytes));
			Transaction tx = TransactionEnvelope.decode(in).getTx();

			/* a tx can have multiple operations, so iterate through (potentially) all of them */
			for (int i = 0; i < tx.getOperations().length; i++) {
				if (tx.getOperations()[i].getBody().getDiscriminant() == OperationType.PAYMENT) {
					org.stellar.sdk.xdr.Asset asset = tx.getOperations()[i].getBody().getPaymentOp().getAsset();

					String amount = Format.parseAmountString(tx.getOperations()[i].getBody().getPaymentOp().getAmount().getInt64().toString());
					String coin = Resolve.assetName(asset);
					String memo = tx.getMemo().getText();
					KeyPair srcKey = Resolve.getKeyPairFromAccountIdStr(transactionResponses.get(0).getSourceAccount().getAccountId()); // the user's account
					KeyPair destKey = Resolve.getKeyPairFromAccountId(tx.getOperations()[i].getBody().getPaymentOp().getDestination());
					String addr;
					boolean isPayment;

					/* determine if we sent or recieved this payment */
					if (!pair.getAccountId().equalsIgnoreCase(srcKey.getAccountId()) && pair.getAccountId().equalsIgnoreCase(destKey.getAccountId())) {
						if (!srcKey.getAccountId().equalsIgnoreCase(pair.getAccountId())) {
							//this means the current account is RECEIVING the payment
							isPayment = false;
							addr = srcKey.getAccountId();

						} else {
							isPayment = true;
							addr = destKey.getAccountId();
							amount = Format.sentPayment(amount);

						}
					} else {
						isPayment = false;
						addr = "Sent to self";

					}

					transactions.add(new Transactions(
							coin
							, amount
							, Format.time(response.getCreatedAt())
							, memo
							, addr
							, isPayment));
				}
			}
		}
		return transactions;
	}

	@SuppressWarnings ("Duplicates")
	public StellarAsset[] getAllAssetBalancesArr(boolean isMainNet) throws IOException {
		Server server = Connections.getServer(isMainNet);

		AccountResponse.Balance[] balances = server.accounts().account(pair).getBalances();
		StellarAsset[] assetBalances = new StellarAsset[balances.length];

		int i = 0;
		for(AccountResponse.Balance balance : balances){
			String assetName;
			if(balance.getAssetType().equalsIgnoreCase("native")) assetName = "XLM";
			else assetName = balance.getAssetCode();

			assetBalances[i] = new StellarAsset(assetName, balance.getBalance());
			i++;
		}
		return assetBalances;
	}

	private static void savePagingToken(String pagingToken) {
		// TODO Auto-generated method stub
		myToken = pagingToken;
	}

	private static String loadLastPagingToken() {
		// TODO Auto-generated method stub
		return myToken;
	}

	public KeyPair getPair() {
		return pair;
	}

	public void setPair(KeyPair pair) {
		this.pair = pair;
	}
}
