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

public class TypeDefaultEntry {

	private ArrayList<Integer> type;
	private ArrayList<Integer> value;
	private ArrayList<Integer> i1,i2;
	private int numVals;
	
	public TypeDefaultEntry() {	
		type = new ArrayList<Integer>();
		value = new ArrayList<Integer>();
		i1 = new ArrayList<Integer>();
		i2 = new ArrayList<Integer>();
		numVals = 0;

	}

	public TypeDefaultEntry(ByteBuffer buffer, int numVals) {	
		type = new ArrayList<Integer>();
		value = new ArrayList<Integer>();
		i1 = new ArrayList<Integer>();
		i2 = new ArrayList<Integer>();
		this.numVals = numVals;

		for (int i = 0; i < numVals; i++) {
			type.add(buffer.getInt());
		}

		for (int i = 0; i < numVals; i++) {
			value.add(buffer.getInt());
		}

		for (int i = 0; i < numVals; i++) {
			i1.add(buffer.getInt());
		}

		for (int i = 0; i < numVals; i++) {
			i2.add(buffer.getInt());
		}

	}
	
	public void write(ByteBuffer buffer) {
		for (int i = 0; i < numVals; i++) {
			buffer.putInt(type.get(i));
		}

		for (int i = 0; i < numVals; i++) {
			buffer.putInt(value.get(i));
		}

		for (int i = 0; i < numVals; i++) {
			buffer.putInt(type.get(i));
		}

		for (int i = 0; i < numVals; i++) {
			buffer.putInt(value.get(i));
		}
	}
	
	public int byteSize() {
		return numVals*4*4;
	}

	public int type(int index) {
		return type.get(index);
	}
	
	public int value(int index){
		return value.get(index);
	}
	
	public int i1(int index) {
		return i1.get(index);
	}
	
	public int i2(int index) {
		return i2.get(index);
	}
	
	public int size() {
		return numVals;
	}
	
	public void add(DefaultCreator creator) {
		numVals++;
		i1.add(0);
		i2.add(0);
		creator.addDefault(type,value);
	}
	
	public void setType(int type, int index) {
		this.type.set(index,type);
	}
	
	public void setValue(double valD, int index) {
		int valI = DoubleToInt.convert(valD);
		value.set(index,valI);
	}
	
	public void offsetExpressions(int offset) {
		for(int i=0;i<numVals;i++) {
			if(type.get(i) == 2) {
				int val = value.get(i);
				val += offset;
				value.set(i,val);
				
			}
		}
	}

}
