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

public class KeywordEntry {
	
	private String name;
	private String abbreviation;
	private int units;
	private int type;
	private int length;
	private int min;
	private int max;
	private int valPos;
	private int def;
	private int typeCode;
	
	public KeywordEntry(ByteBuffer buffer){
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
			name = new String(nameBuffer.array(), "ASCII").trim();
			abbreviation = new String(abbreviationBuffer.array(), "ASCII").trim();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		units = buffer.getInt();
		type = buffer.getInt();
		length = buffer.getInt();
		min = buffer.getInt();
		max = buffer.getInt();
		valPos = buffer.getInt();
		def = buffer.getInt();		
		buffer.getInt();
		typeCode = buffer.getInt();
		//System.out.println(typeCode);
		buffer.getInt();
	}
	
	public String name(){
		return name;
	}
	
	public String abbreviation(){
		return abbreviation;
	}
	
	public int units(){
		return units;
	}
	
	public int type(){
		return type;
	}
	
	public int length(){
		return length;
	}
	
	public int min(){
		return min;
	}
	
	public int max(){
		return max;
	}
	
	public int valPos(){
		return valPos;
	}
	
	public int def(){
		return def;
	}
	
	public int typeCode(){
		return typeCode;
	}
}
