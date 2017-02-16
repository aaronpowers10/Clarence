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

public class ExpressionTable {
	
	private ArrayList<ExpressionEntry> expressionEntries;
	
	public ExpressionTable(ByteBuffer buffer, int size, int startIndex){
		expressionEntries = new ArrayList<ExpressionEntry>();
		int index = startIndex+1;
		while(index < size + startIndex){
			ExpressionEntry entry = new ExpressionEntry(buffer,index);
			expressionEntries.add(entry);
			index = index + entry.entryLength();
		}
	}
	
	public ExpressionEntry get(int index){
		for(int i=0;i<expressionEntries.size();i++){			
			if(expressionEntries.get(i).index() == index){
				return expressionEntries.get(i);
			}
		}
		return null;
	}
	
	public int size(){
		return expressionEntries.size();
	}

}
