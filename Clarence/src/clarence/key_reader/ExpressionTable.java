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
	private int i1,i2,i3,i4;
	
	public ExpressionTable(ByteBuffer buffer, int size, int startIndex){
		i1 = buffer.getInt();
		i2 = buffer.getInt();
		
		expressionEntries = new ArrayList<ExpressionEntry>();
		int index = startIndex+1;
		while(index < size + startIndex){
			ExpressionEntry entry = new ExpressionEntry(buffer,index);
			expressionEntries.add(entry);
			index = index + entry.entryLength();
		}
		i3 = buffer.getInt();
		i4 = buffer.getInt();
//		System.out.println("EXPR TBL I1: " + i1);
//		System.out.println("EXPR TBL I2: " + i2);
//		System.out.println("EXPR TBL I3: " + i3);
//		System.out.println("EXPR TBL I4: " + i4);
	}
	
	public void write(ByteBuffer buffer) {
		//buffer.putInt(i1);
		//buffer.putInt(i2);
		for(ExpressionEntry entry: expressionEntries) {
			entry.write(buffer);
		}
		buffer.putInt(i3);
		//buffer.putInt(i4);
	}
	
	public int byteSize() {
		int byteSize = 16;
		for(ExpressionEntry entry: expressionEntries) {
			byteSize += entry.byteSize();
		}
		return byteSize;
	}
	
	public ExpressionEntry getByPointer(int index){
		for(int i=0;i<expressionEntries.size();i++){			
			if(expressionEntries.get(i).index() == index){
				return expressionEntries.get(i);
			}
		}
		return null;
	}
	
	public String summary(int index) {
		return expressionEntries.get(index).summary();
	}
	
	public String summaryByPointer(int pointer) {
		return this.getByPointer(pointer).summary();
	}
	
	public int size(){
		return expressionEntries.size();
	}
	
	public String summary(){
		String summary = "EXPRESSION TABLE SUMMARY" + System.lineSeparator();
		summary = summary + "NUMBER OF ENTRIES: " + size() + System.lineSeparator();
		summary = summary + "SIZE ON DISK: " + byteSize() + System.lineSeparator();
		return summary;
	}
	
	public String processedSummary(int index) {
		return expressionEntries.get(index).summaryProcessed();
	}
	
	public String processedSummaryByPointer(int pointer) {
		return getByPointer(pointer).summaryProcessed();
	}
	
	public void offsetExpressions(int offset) {
		for(ExpressionEntry entry: expressionEntries) {
			entry.offsetIndex(offset);
		}
	}
	
	public void keywordAdded(int keyInd) {
		for(ExpressionEntry entry: expressionEntries) {
			entry.keywordAdded(keyInd);
		}
	}
	
	public ExpressionEntry getByIndex(int index) {
		return expressionEntries.get(index);
	}

}
