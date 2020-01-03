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
	private int i1,i2;
	
	public KeywordEntry(String name, String abbreviation, int type, int length, int min, int max, int valPos) {
		this.name = name;
		this.abbreviation = abbreviation;
		this.type = type;
		this.length = length;
		this.min = min;
		this.max = max;
		this.valPos = valPos;
	}
//	
//	public KeywordEntry(String name, String abbreviation, int valPos) {
//		this.name = name;
//		this.abbreviation = abbreviation;
//		units = 0;
//		type = 4;  // String
//		length = 1;
//		this.valPos = valPos;
//	}
//	
//	public KeywordEntry(String name, String abbreviation, double min, double max, int valPos) {
//		this.name = name;
//		this.abbreviation = abbreviation;
//		units = 0;
//		type = 1;  // Real Value
//		length = 1;
//		this.min = DoubleToInt.convert(min);
//		this.max = DoubleToInt.convert(max);
//		this.valPos = valPos;
//	}
//	
//	public KeywordEntry(String name, String abbreviation, int min, int max, int valPos) {
//		this.name = name;
//		this.abbreviation = abbreviation;
//		units = 0;
//		type = 6;  // Int Value
//		length = 1;
//		this.min = min;
//		this.max = max;
//		this.valPos = valPos;
//	}
	
	public KeywordEntry(String name, String abbreviation, int valPos, int symIndex) {
		this.name = name;
		this.abbreviation = abbreviation;
		units = 0;
		type = 2;
		length = 1;
		this.valPos = valPos;
		min = symIndex;
	}
	
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
			name = new String(nameBuffer.array(), "ASCII").replaceAll("\\s+$","");
			abbreviation = new String(abbreviationBuffer.array(), "ASCII").replaceAll("\\s+$","");
			//System.out.println("READING:" + buffer.position() + "  " + name + ":" + abbreviation);
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
		i1 = buffer.getInt();
		typeCode = buffer.getInt();
		i2 = buffer.getInt();
//		if(i1 !=0) {
//			System.out.println("I1 FOR " + name + " " + i1);
//		}
//		if(i2 !=0) {
//			System.out.println("I2 FOR " + name + " " + i2);
//		}
//		if(typeCode !=0) {
//			System.out.println("TYPE CODE FOR " + name + " " + typeCode);
//		}
	}
	
	public void write(ByteBuffer buffer) {
		buffer.put(BinaryTools.packString(name,16));
		buffer.put(BinaryTools.packString(abbreviation,8));
		buffer.putInt(units);
		buffer.putInt(type);
		buffer.putInt(length);
		buffer.putInt(min);
		buffer.putInt(max);
		buffer.putInt(valPos);
		buffer.putInt(def);	
		buffer.putInt(i1);
		buffer.putInt(typeCode);
		buffer.putInt(i2);
	}
	
	public int byteSize() {
		return 16 + 8 + 10*4;
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
	
	public KeywordType keywordType() {
		System.out.println("GETTING TYPE " + type);
		if (type == 1) {
			return KeywordType.REAL;
		} else if (type == 2) {
			System.out.println("RETURNING CODEWORD");
			return KeywordType.CODEWORD;
		} else if (type == 3) {
			System.out.println("RETURNING OBJ");
			return KeywordType.OBJECT;
		} else if (type == 4) {
			return KeywordType.STRING;
		} else if (type == 5) {
			return KeywordType.REAL;
		} else if (type == 6) {
			return KeywordType.INTEGER;
		} else if (type == 10) {
			return KeywordType.OBJECT;
		} else if (type == 13) {
			return KeywordType.OBJECT;
		} else if (type == 16) {
			return KeywordType.INTEGER;
		} else if (type == 20) {
			return KeywordType.OBJECT;
		} else {
			return KeywordType.UNKNOWN;
		}
	}
	
	public int minAsInt() {
		return (int)IntToFloat.convert(min);
	}
	
	public int maxAsInt() {
		return (int)IntToFloat.convert(max);
	}
	
	public float minAsFloat() {
		return IntToFloat.convert(min);
	}
	
	public float maxAsFloat() {
		return IntToFloat.convert(max);
	}
	
	public String getRawInfo() {
		String info = "RAW DATA FOR KEYWORD ENTRY: " + name + "\n";
		info = info + "NAME: " + name + "\n";
		info = info + "ABBREVIATION: " + "\n";
		info = info + "UNITS: " + units + "\n";
		info = info + "TYPE: " + type + "\n";
		info = info + "LENGTH: " + length + "\n";
		info = info + "MIN: " + min + "\n";
		info = info + "MAX: " + max + "\n";
		info = info + "VAL-POS: " + valPos + "\n";
		info = info + "DEF: " + def + "\n";
		info = info + "I1: " + i1 + "\n";
		info = info + "TYPE-CODE: " + typeCode + "\n";
		info = info + "I2: " + i2 + "\n";
		return info;
	}
}
