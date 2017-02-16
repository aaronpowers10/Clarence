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
	
	public SymbolTable(ByteBuffer buffer, int initialSize){
		symbols = new ArrayList<SymbolEntry>();
		read(buffer,initialSize);
	}
	
	private void read(ByteBuffer buffer, int initialSize) {
		for (int i = 0; i < initialSize; i++) {
			symbols.add(new SymbolEntry(buffer));
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

}
