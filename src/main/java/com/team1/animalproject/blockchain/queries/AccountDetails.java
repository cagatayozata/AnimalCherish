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

import com.team1.animalproject.blockchain.models.Transactions;
import com.team1.animalproject.blockchain.utils.Connections;
import com.team1.animalproject.blockchain.utils.Format;
import org.apache.commons.compress.utils.Lists;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.MemoText;
import org.stellar.sdk.Server;
import org.stellar.sdk.requests.RequestBuilder;
import org.stellar.sdk.responses.TransactionResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class AccountDetails {

	private final KeyPair pair;

	public AccountDetails(KeyPair pair) {
		this.pair = pair;
	}

	@SuppressWarnings ("Duplicates")
	public List<Transactions> getTransactionsFull(boolean isMainNet, String paging) throws IOException {
		Server server = Connections.getServer(isMainNet);
		List<Transactions> transactions = Lists.newArrayList();
		ArrayList<TransactionResponse> transactionResponses;
		if(paging == null){
			transactionResponses = server.transactions().forAccount(pair).order(RequestBuilder.Order.DESC).limit(200).execute().getRecords();
		} else {
			transactionResponses = server.transactions().forAccount(pair).order(RequestBuilder.Order.DESC).cursor(paging).limit(200).execute().getRecords();
		}

		for(TransactionResponse response : transactionResponses){
			byte[] bytes = Base64.getDecoder().decode(response.getEnvelopeXdr());

			String memo = null;
			if(response.getMemo() instanceof MemoText){
				memo = ((MemoText) response.getMemo()).getText();
			}

			String cursor = response.getPagingToken();

			transactions.add(new Transactions("coin", "amount", Format.time(response.getCreatedAt()), memo, "addr", true, cursor));

		}
		return transactions;
	}

}
