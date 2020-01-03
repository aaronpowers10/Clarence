/*
 *
 *  Copyright (C) 2017 Aaron Powers
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package clarence.test_driver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import clarence.key_reader.DOE2DataProperties;
import clarence.key_reader.KeyReader;

public class ClarenceTestDriver {

	public static void main(String[] args) throws IOException {
		DOE2DataProperties dataProperties = KeyReader.read("C:\\doe22\\EXE48y\\BDLKEY.BIN");

		try {
			FileWriter output = new FileWriter(new File("BDLKEY.TXT"));
			for (int i = 0; i < dataProperties.size(); i++) {
				output.write(dataProperties.get(i).write());
			}
			output.close();
			output = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("DONE");

	}

}
