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

public class DefaultTable {
	
	private ArrayList<DefaultEntry> defaultEntries;
	
	public DefaultTable(ByteBuffer buffer, CommandTable commandTable){
		defaultEntries = new ArrayList<DefaultEntry>();		
		for(int i=0;i<commandTable.size();i++){
			if( i > 0){
				if((commandTable.get(i).defaultTableStart() == commandTable.get(i-1).defaultTableStart() + commandTable.get(i-1).valueLength() *
						commandTable.get(i-1).numTypes()*4)){
					defaultEntries.add(new DefaultEntry(buffer,commandTable.get(i).valueLength(),commandTable.get(i).numTypes()));
				}
			} else {
				defaultEntries.add(new DefaultEntry(buffer,commandTable.get(i).valueLength(),commandTable.get(i).numTypes()));
			}
			
		}
	}
	
	public DefaultEntry get(int index){
		return defaultEntries.get(index);
	}
	
	public TypeDefaultEntry get(int index, int type){
		return defaultEntries.get(index).defaultEntry(type);
	}

}
