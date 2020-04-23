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
package clarence.key_reader;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class KeywordTable {

	private ArrayList<KeywordEntry> keywords;

	public KeywordTable(ByteBuffer buffer, int size) {
		System.out.println("KEY TABLE SIZE " + size);
		keywords = new ArrayList<KeywordEntry>();
		read(buffer, size);
	}

	private void read(ByteBuffer buffer, int size) {
		for (int i = 0; i < size; i++) {
			keywords.add(new KeywordEntry(buffer));
		}
	}
	
	public void write(ByteBuffer buffer) {
		for(int i=0;i<keywords.size();i++) {
			keywords.get(i).write(buffer);
		}
	}

	public int size() {
		return keywords.size();
	}
	
	public KeywordEntry get(int index){
		return keywords.get(index);
	}
	
	public int byteSize() {
		int byteSize = 0;
		for(KeywordEntry keyword: keywords) {
			byteSize += keyword.byteSize();
		}
		return byteSize;
	}
	
	public String summary() {
		StringBuilder summary = new StringBuilder();
		summary.append("KEYWORD TABLE SUMMARY"+ System.lineSeparator());
		summary.append("NUMBER OF ENTRIES: " + size()+ System.lineSeparator());
		summary.append("SIZE ON DISK: " + byteSize() + " BYTES" + System.lineSeparator());
		summary.append("KEYWORDS: "+ System.lineSeparator());
		for(int i=0;i<size();i++) {
			summary.append((i+1) + ": " + keywords.get(i).name() + System.lineSeparator());
		}
		return summary.toString();
	}
	
	public void add(KeywordEntry keyword, int index) {
		keywords.add(index, keyword);
	}

}
