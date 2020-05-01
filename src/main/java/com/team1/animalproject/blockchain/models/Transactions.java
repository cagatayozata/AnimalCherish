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

import lombok.Data;

@Data
public class Transactions
{
    private String assetName;
    private String amount;
    private String date;
    private String memo;
    private String toFrom;
    private Boolean wasSent;
    private String paging;

    public Transactions(String assetName, String amount, String date, String memo) {
        this.assetName = assetName;
        this.amount = amount;
        this.date = date;
        this.memo = memo;
    }

    public Transactions(String assetName, String amount, String date,
                        String memo, String toFrom, Boolean wasSent, String paging) {
        this.assetName = assetName;
        this.amount = amount;
        this.date = date;
        this.memo = memo;
        this.toFrom = toFrom;
        this.wasSent = wasSent;
        this.paging = paging;
    }

    public Transactions(String assetName, String amount, String date, Boolean wasSent) {
        this.assetName = assetName;
        this.amount = amount;
        this.date = date;
        this.wasSent = wasSent;
    }

    public Transactions(String assetName, String amount, String date, String toFrom, Boolean wasSent) {
        this.assetName = assetName;
        this.amount = amount;
        this.date = date;
        this.toFrom = toFrom;
        this.wasSent = wasSent;
    }
}
