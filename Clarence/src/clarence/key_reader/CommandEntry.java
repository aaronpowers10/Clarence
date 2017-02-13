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

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CommandEntry {
	
	private String name;
	private String abbreviation;
	private int iop;
	private int level;
	private int startKeys;
	private int numKeys;
	private int typeSym;
	private int maxDef;
	private int referenceTableStart;
	private int defaultTableStart;
	private int valueLength;
	private int childClass;
	private int parentClass;
	private int defaultLength;
	private int numTypes;
	private int keyOffset;
	
	public CommandEntry(ByteBuffer buffer, int keyOffset){
		this.keyOffset = keyOffset;
		read(buffer);
	}
	
	private void read(ByteBuffer buffer){
		ByteBuffer nameBuffer = ByteBuffer.allocate(4*4);
		nameBuffer.order(ByteOrder.LITTLE_ENDIAN);
		for (int i = 0; i < 4; i++) {
			nameBuffer.putInt(buffer.getInt());
		}
		
		ByteBuffer abbreviationBuffer = ByteBuffer.allocate(4*2);
		abbreviationBuffer.order(ByteOrder.LITTLE_ENDIAN);
		for (int i = 0; i < 2; i++) {
			abbreviationBuffer.putInt(buffer.getInt());
		}
		
		try {
			name = new String(nameBuffer.array(), "ASCII");
			abbreviation = new String(abbreviationBuffer.array(), "ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		iop = buffer.getInt();
		level = buffer.getInt();
		startKeys = buffer.getInt();
		numKeys = buffer.getInt();
		typeSym = buffer.getInt();
		maxDef = buffer.getInt();
		buffer.getInt();
		referenceTableStart = buffer.getInt();
		defaultTableStart = buffer.getInt();
		valueLength = buffer.getInt();
		buffer.getInt();
		buffer.getInt();
		childClass = buffer.getInt();
		parentClass = buffer.getInt();
		buffer.getInt();
		buffer.getInt();
		defaultLength = buffer.getInt();
		numTypes = Math.abs(buffer.getInt());
		
	}
	
	public String name(){
		return name;
	}
	
	public String abbreviation(){
		return abbreviation;
	}
	
	public int iop(){
		return iop;
	}
	
	public int level(){
		return level;
	}
	
	public int startKeys(){
		return startKeys - keyOffset;
	}
	
	public int numKeys(){
		return numKeys;
	}
	
	public int typeSym(){
		return typeSym;
	}
	
	public int maxDef(){
		return maxDef;
	}
	
	public int referenceTableStart(){
		return referenceTableStart;
	}
	
	public int defaultTableStart(){
		return defaultTableStart;
	}
	
	public int valueLength(){
		return valueLength;
	}
	
	public int childClass(){
		return childClass;
	}
	
	public int parentClass(){
		return parentClass;
	}
	
	public int defaultLength(){
		return defaultLength;
	}
	
	public int numTypes(){
		return numTypes;
	}

}
