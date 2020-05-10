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

package com.team1.animalproject.blockchain.utils;

public class Format
{
    public static String parseByteArray (byte[] array)
    {
        StringBuilder parsedVal = new StringBuilder();

        for (Byte anArray : array)
        {
            parsedVal.append (anArray);
        }
        return parsedVal.toString();
    }

    public static String parseAmountString (String amount )
    {
        StringBuilder builder = new StringBuilder(amount);
        if ( amount.length() > 7 )
            builder.insert( amount.length() - 7, ".");
        else {

            while ( builder.toString().length() != 7 )
                builder.insert(0, "0");

            builder.insert(0, "0.");
        }
        return builder.toString();
    }

    public static String time (String time )
    {
        return new StringBuilder( time ).replace( 10, time.length(), "" ).toString();
    }

    public static String parseDollarAmount (String amount )
    {
        char[] amountArr = amount.toCharArray();
        StringBuilder builder = new StringBuilder();

        for ( int i = 0; i < amountArr.length; i++ )
        {
            if ( amountArr[i] == '.')
            {
                for (int j = 0; j <= i ; j++)
                    builder.append(amountArr[j]);

                builder.append( amountArr[i+1]).append( amountArr[i+2]);
                break;
            }
        }
        return builder.toString();
    }

    public static String sentPayment (String amount )
    {
        return new StringBuilder( amount ).insert(0, "-").toString();
    }

    public static String parseCursorUrl (String url, String cursor ) { return String.format ( url, cursor ); }

    public static String cleanAssetName (String assetName ) { return assetName.substring(0, assetName.indexOf(":")); }
}
