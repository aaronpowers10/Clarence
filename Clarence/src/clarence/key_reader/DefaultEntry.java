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

public class DefaultEntry {
	
	private ArrayList<TypeDefaultEntry> defaultEntries;
	private String commandName;
	private int numTypes;
	
	public DefaultEntry(ByteBuffer buffer, CommandEntry commandEntry){
		commandName = commandEntry.name();
		defaultEntries = new ArrayList<TypeDefaultEntry>();
		numTypes = commandEntry.numTypes();
		for(int i=0;i<numTypes;i++){
			defaultEntries.add(new TypeDefaultEntry(buffer,commandEntry.valueLength()));
		}
	}
	
	public void write(ByteBuffer buffer) {
		for(TypeDefaultEntry entry: defaultEntries) {
			entry.write(buffer);
		}
	}
	
	public int byteSize() {
		int byteSize = 0;
		for(TypeDefaultEntry entry: defaultEntries) {
			byteSize += entry.byteSize();
		}
		return byteSize;
	}
	
	public TypeDefaultEntry defaultEntry(int type){
		return defaultEntries.get(type);
	}
	
	public String commandName(){
		return commandName;
	}
	
	public int numEntries() {
		return defaultEntries.size();
	}
	
	public int size() {
		int size = 0;
		for(TypeDefaultEntry entry: defaultEntries) {
			size += entry.size();
		}
		return size;
	}
	
	public void add(DefaultCreator creator) {
		for(TypeDefaultEntry entry: defaultEntries) {
			entry.add(creator);
		}
		
		
//		for(int i=0;i<numTypes;i++) {
//			for(TypeDefaultEntry entry: defaultEntries) {
//				entry.add(creator);
//			}
//		}
	}
	
	public void offsetExpressions(int offset) {
		for(TypeDefaultEntry entry: defaultEntries) {
			entry.offsetExpressions(offset);
		}
	}

}
