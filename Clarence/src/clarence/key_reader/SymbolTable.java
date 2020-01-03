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

public class SymbolTable {
	
	private ArrayList<SymbolEntry> symbols;
	private int i1,i2;
	
	public SymbolTable(ByteBuffer buffer, int size){
		symbols = new ArrayList<SymbolEntry>();
		read(buffer,size);
	}
	
	private void read(ByteBuffer buffer, int size) {
		i1 = buffer.getInt();
		i2 = buffer.getInt();
//		System.out.println("SYM TBL I1: " + i1);
//		System.out.println("SYM TBL I2: " + i2);
		for (int i = 0; i < size; i++) {
			symbols.add(new SymbolEntry(buffer));
		}
	}
	
	public void write(ByteBuffer buffer) {
		//buffer.putInt(i1);
		//buffer.putInt(i2);
		for(int i=0;i<symbols.size();i++) {
			symbols.get(i).write(buffer);
		}
	}
	
	public int size(){
		return symbols.size();
	}
	
	public SymbolEntry get(int index){
		return symbols.get(index-1);
	}
	
	public ArrayList<SymbolEntry> getListOfType(int type){
		ArrayList<SymbolEntry> matches = new ArrayList<SymbolEntry>();
		for(int i=0;i<size();i++){
			if(symbols.get(i).type() == type){
				matches.add(symbols.get(i));
			} 
		}
		return matches;
	}
	
	public int byteSize() {
		int byteSize = 8;
		for(SymbolEntry symbol: symbols) {
			byteSize += symbol.byteSize();
		}
		return byteSize;
	}
	
	public String summaryString() {
		StringBuilder summary = new StringBuilder();
		summary.append( "SYMBOL TABLE SUMMARY:" + "\n");
		summary.append("NUMBER OF ENTRIES: " + size() + "\n");
		summary.append( "SIZE ON DISK: " + byteSize() + " BYTES" + "\n");
		summary.append( "SYMBOLS: " + "\n");
		for(int i=0;i<size();i++) {
			summary.append( (i+1) + ": " + symbols.get(i).symbol() + " TYPE: " + symbols.get(i).type() + 
					" VALUE: " + symbols.get(i).value() + "\n");
		}
		return summary.toString();
	}
	
	public int getUniqueType() {
		int uniqueType = 1000;
		boolean continueSearch = true;
		while(continueSearch) {
			if(containsType(uniqueType)) {
				uniqueType++;
			} else {
				continueSearch = false;
				
			}
		}
		
		return uniqueType;
	}
	
	public boolean containsType(int type) {
		for(SymbolEntry symbol: symbols) {
			if(symbol.type() == type) {
				return true;
			}
		}
		return false;
	}
	
	public void addCodewordSymbols(ArrayList<String> newSymbols, int type) {
		for(int i=0;i<newSymbols.size();i++) {
			SymbolEntry newEntry = new SymbolEntry(newSymbols.get(i),type,i);
			symbols.add(newEntry);
		}
	}

}
