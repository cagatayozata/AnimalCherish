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

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class Props {

	public static final String LUNAR_HELPER_PROPS = "../config/endpoints.properties";
	public static final String MAIN_NET = "stellar.org.mainnet";

	public static String getProperty(String propertiesFile, String propertyKey) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties prop = new Properties();
		InputStream in = loader.getResourceAsStream(propertiesFile);
		String value;

		try{
			prop.load(Objects.requireNonNull(in));
			value = prop.getProperty(propertyKey);
			in.close();
			return value;
		} catch (IOException e){
			try{
				Objects.requireNonNull(in).close();
			} catch (IOException e1){
				return null;
			}
			return null;
		}

	}
}
