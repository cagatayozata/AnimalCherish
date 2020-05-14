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

import org.stellar.sdk.AssetTypeNative;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Memo;
import org.stellar.sdk.Network;
import org.stellar.sdk.PaymentOperation;
import org.stellar.sdk.Server;
import org.stellar.sdk.Transaction;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.SubmitTransactionResponse;

import java.io.IOException;

public class Payment {

	public static String myToken = null;
	private final KeyPair pair;

	public Payment(KeyPair pair) {
		this.pair = pair;
	}

	public void send(String memo, KeyPair source) throws IOException {
		Network.useTestNetwork();
		Server server = new Server("https://horizon-testnet.stellar.org");

		KeyPair destination = KeyPair.fromAccountId("GBOOWLO3IC7TOQFPIAA3ERSYGLG4EK2JYLMWMTCOUGJ7IQMC6EY6HFNU");

		server.accounts().account(destination);

		AccountResponse sourceAccount = server.accounts().account(source);
		Transaction transaction = new Transaction.Builder(sourceAccount).addOperation(new PaymentOperation.Builder(destination, new AssetTypeNative(), "10").build())
				.addMemo(Memo.text(memo))
				.setTimeout(180)
				.build();
		transaction.sign(source);

		try{
			SubmitTransactionResponse response = server.submitTransaction(transaction);
			System.out.println("Success!");
			System.out.println(response);
		} catch (Exception e){
			System.out.println("Something went wrong!");
			System.out.println(e.getMessage());
		}
	}
}
